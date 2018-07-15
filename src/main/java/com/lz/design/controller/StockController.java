package com.lz.design.controller;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.lz.design.common.APIResult;
import com.lz.design.model.Goods;
import com.lz.design.model.Loggers;
import com.lz.design.model.Stock;
import com.lz.design.model.User;
import com.lz.design.service.GoodsService;
import com.lz.design.service.LoggersService;
import com.lz.design.service.StockService;
import com.lz.design.service.UserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  1:20
 */
@CrossOrigin
@Controller
@RequestMapping("/lz/design/stock")
public class StockController {
    private static Logger logger = LoggerFactory.getLogger(StockController.class);
    @Autowired
    private StockService stockService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoggersService loggersService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "insertStock.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult insertStock(@RequestBody Stock stock, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            Preconditions.checkArgument(stock.getGoodsId() != null, "商品Id不能为空");
            Preconditions.checkArgument(stock.getGoodsStockIn() != null, "入库数量不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() != 3) {
                logger.warn("该用户无权限管理商品库存，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            logger.info("传入数据,:{}", new Gson().toJson(stock));
            stock.setStockInTime(DateTime.now().toDate());
            int i = stockService.insertStock(stock);
            if (i > 0) {
                logger.info("添加库存成功");
                if (user2.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user2.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员" + user2.getUserName() + "添加了库存" + stock);
                    loggersService.insertLoggers(loggers);
                }
                return APIResult.buildSuccessResult("添加库存成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【库存模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【库存模块】库存模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "添加库存失败");
    }

    /**
     * 更改库存信息（上架、出库等）
     *
     * @param stock
     * @return
     */
    @RequestMapping(value = "updateStock.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult updateStock(@RequestBody Stock stock, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            Preconditions.checkArgument(stock.getGoodsId() != null, "商品Id不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() <= 2) {
                logger.warn("该用户无权限管理商品库存，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            logger.info("传入数据,:{}", new Gson().toJson(stock));
            int i = stockService.updateStockByGoodsId(stock);
            Date date = new DateTime().toDate();
            Goods goods = new Goods();
            goods.setGoodsExpire(date);
            goodsService.updateByGoodsId(goods);
            if (i > 0) {
                logger.info("更改库存成功");
                if (user2.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user2.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员" + user2.getUserName() + "更改了库存信息" + stock);
                    loggersService.insertLoggers(loggers);
                }
                return APIResult.buildSuccessResult("更改库存成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【库存模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【库存模块】库存模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "更改库存失败");
    }

    /**
     * 查询库存（goodsID为null时查询全部库存，goodsId指定时查询单一商品库存）
     *
     * @param goodsId
     * @param req
     * @return
     */
    @RequestMapping(value = "selectStock.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectStock(@RequestBody Integer goodsId, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() <= 2) {
                logger.warn("该用户无权限管理商品库存，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            logger.info("传入数据,:{}", new Gson().toJson(goodsId));
            if (goodsId == -1) {
                goodsId = null;
            }
            List<Stock> stockList = stockService.selectStock(goodsId);
            if (stockList != null) {
                logger.info("查询库存成功");
                if (user2.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user2.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员" + user2.getUserName() + "查询了商品库存,商品Id:" + goodsId);
                    loggersService.insertLoggers(loggers);
                }
                List<Goods> goods;
                String goodsName;
                for (Stock stock : stockList) {
                    goods = goodsService.selectByGoodsId(stock.getGoodsId());
                    goodsName = goods.get(0).getGoodsName();
                    stock.setGoodsName(goodsName);
                }
                logger.info("查询到库存信息，：{}", new Gson().toJson(stockList));
                return APIResult.buildSuccessResult(stockList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【库存模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【库存模块】库存模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询库存失败");
    }

    @RequestMapping(value = "deleteStock.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult deleteStock(@RequestBody Integer[] goodsId, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() <= 2) {
                logger.warn("该用户无权限管理商品库存，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            logger.info("传入数据,:{}", new Gson().toJson(goodsId));
            int i = stockService.deleteStockByGoodsId(goodsId);
            if (i > 0) {
                logger.info("删除库存成功");
                goodsService.deleteByGoodsId(goodsId);
                if (user2.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user2.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员" + user2.getUserName() + "删除了商品库存,商品Id" + goodsId);
                    loggersService.insertLoggers(loggers);
                }
                return APIResult.buildSuccessResult("删除库存成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【库存模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【库存模块】库存模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "删除库存失败");
    }
}
