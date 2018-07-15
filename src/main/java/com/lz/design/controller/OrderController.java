package com.lz.design.controller;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.lz.design.common.APIResult;
import com.lz.design.model.*;
import com.lz.design.service.GoodsService;
import com.lz.design.service.OrderService;
import com.lz.design.service.StockService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Xingwu.Jia [xingwuj@tujia.com]
 * @date 2018/1/25 20:22
 */
@CrossOrigin
@Controller
@RequestMapping("/lz/design/order")
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private StockService stockService;


    @RequestMapping(value = "addOrder.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult addOrder(@RequestBody Order Order) {
        try {
            Preconditions.checkArgument(Order.getUserId() != null, "用户ID不可为空");
            Preconditions.checkArgument(Order.getOrderGoods() != null, "订单信息不可为空");
            logger.info("【订单系统】请求下订单，请求参数:{}", new Gson().toJson(Order));
            List<OrderGoods> orderGoods = Order.getOrderGoods();
            for (int i = 0; i < orderGoods.size(); i++) {
                Preconditions.checkArgument(Order.getOrderGoods().get(i).getGoodsId() != null, "第" + i + "件商品ID不可为空");
                Preconditions.checkArgument(Order.getOrderGoods().get(i).getGoodsNum() != null, "第" + i +
                        "件商品数量不可为空");
            }
            double goodsTotalPrice = 0;
            for (OrderGoods orderGood : orderGoods) {
                List<Stock> stocks = stockService.selectStock(orderGood.getGoodsId());
                Integer goodsGround = stocks.get(0).getGoodsGround();
                if (goodsGround <= orderGood.getGoodsNum()) {
                    logger.error("【订单系统】添加订单失败,酒品" + orderGood.getGoodsId() + "号，上架数不足!!!");
                    return APIResult.buildFailedResult(-1, "添加订单失败,酒品" + orderGood.getGoodsId() + "号，上架数不足!!!");
                }
                Stock stock = new Stock();
                stock.setGoodsId(orderGood.getGoodsId());
                stock.setGoodsStockIn(orderGood.getGoodsNum());
                int i = stockService.updateStockByGoodsId(stock);
                if (i > 0) {
                    List<Goods> goods = goodsService.selectByGoodsId(orderGood.getGoodsId());
                    orderGood.setGoodsName(goods.get(0).getGoodsName());
                    goodsTotalPrice += orderGood.getGoodsNum() * goods.get(0).getGoodsPrice();
                } else {
                    new Exception("第" + orderGood.getGoodsId() + "个酒品库存更新失败");
                }
            }
            Order order = new Order();
            Long orderId = DateTime.now().getMillis();
            order.setOrderId(orderId);
            order.setUserId(Order.getUserId());
            String format = new DecimalFormat("#.00").format(goodsTotalPrice);
            order.setOrderTotalprice(Double.valueOf(format));
            order.setOrderGoods(orderGoods);
            order.setOrderDateStart(DateTime.now().toDate());
            order.setPayStatusId((byte) 0);
            int i = orderService.insertOrder(order);
            if (i > 0) {
                logger.info("【订单系统】添加订单成功");
                return APIResult.buildSuccessResult(order);
            }
            logger.info("【订单系统】添加订单失败");
            return APIResult.buildFailedResult(-1, "添加订单失败");
        } catch (IllegalArgumentException ie) {
            logger.error("【订单系统】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数异常");
        } catch (Exception e) {
            logger.error("【订单系统】添加订单时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
    }

    @RequestMapping(value = "paymentOrder.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult paymentOrder(@RequestBody RequestModel requestModel, HttpServletRequest req) {
        try {
            Preconditions.checkArgument(requestModel.getMessage() != null, "订单号不可为空");
            String message = requestModel.getMessage();
            long orderId = Long.parseLong(message);
            String userId = req.getParameter("userId");
            logger.info("【订单系统】请求支付订单，请求参数:{}", new Gson().toJson(orderId));
            int i = orderService.paymentOrder(orderId, Long.valueOf(userId));
            if (i > 0) {
                logger.info("【订单系统】支付成功");
                return APIResult.buildSuccessResult("支付成功");
            }
            if (i == 0) {
                logger.info("【订单系统】账户余额不足");
                return APIResult.buildFailedResult(-1, "账户余额不足");
            }
            if (i == -2) {
                logger.info("【订单系统】该订单已被支付");
                return APIResult.buildFailedResult(-1, "该订单已被支付");
            }
            logger.info("【订单系统】支付失败");
            return APIResult.buildFailedResult(-1, "支付失败");
        } catch (IllegalArgumentException ie) {
            logger.error("【订单系统】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数异常");
        } catch (Exception e) {
            logger.error("【订单系统】支付订单时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
    }

    @RequestMapping(value = "selectOrder.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectOrder(@RequestBody RequestModel requestModel) {
        try {
            Preconditions.checkArgument(requestModel.getMessage() != null, "订单号不可为空");
            long orderId = Long.parseLong(requestModel.getMessage());
            logger.info("【订单系统】请求查询订单，请求参数:{}", new Gson().toJson(orderId));
            Order order = orderService.selectOrder(orderId);
            if (order != null) {
                logger.info("【订单系统】查询订单结果:{}", new Gson().toJson(order));
                return APIResult.buildSuccessResult(order);
            }
            logger.info("【订单系统】查询订单结果为空");
            return APIResult.buildFailedResult(-1, "该订单不存在");
        } catch (IllegalArgumentException ie) {
            logger.error("【订单系统】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数异常");
        } catch (Exception e) {
            logger.error("【订单系统】查询订单时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
    }

    @RequestMapping(value = "selectOrderByUser.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectOrderByUserId(@RequestBody RequestModel requestModel) {
        try {
            Preconditions.checkArgument(requestModel.getMessage() != null, "用户ID不可为空");
            long userId = Long.parseLong(requestModel.getMessage());
            logger.info("【订单系统】请求查询订单，请求参数:{}", new Gson().toJson(userId));
            List<Order> orderList = orderService.selectOrderByUserId(userId);
            if (CollectionUtils.isEmpty(orderList)) {
                logger.info("【订单系统】查询订单结果为空");
                return APIResult.buildFailedResult(-1, "您还没有下过订单...快去购买吧~~~");
            }
            logger.info("【订单系统】查询订单结果:{}", new Gson().toJson(orderList));
            return APIResult.buildSuccessResult(orderList);
        } catch (IllegalArgumentException ie) {
            logger.error("【订单系统】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数异常");
        } catch (Exception e) {
            logger.error("【订单系统】查询订单时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
    }
}

