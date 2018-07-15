package com.lz.design.controller;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.lz.design.common.APIResult;
import com.lz.design.model.Goods;
import com.lz.design.model.RequestModel;
import com.lz.design.model.ShopCart;
import com.lz.design.service.GoodsService;
import com.lz.design.service.ShopCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Xingwu.Jia [xingwuj@tujia.com]
 * @Date 2018/5/22 11:49
 * @Description
 */
@CrossOrigin
@Controller
@RequestMapping("/lz/design/shopcart")
public class ShopCartController {
    private static Logger logger = LoggerFactory.getLogger(ShopCartController.class);
    @Autowired
    private ShopCartService shopCartService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "insertShopCart.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult insertShopCart(@RequestBody ShopCart ShopCart) {
        try {
            Preconditions.checkArgument(ShopCart.getUserId() != null, "用户ID不可为空");
            Preconditions.checkArgument(ShopCart.getGoodsId() != null, "商品ID不可为空");
            Preconditions.checkArgument(ShopCart.getGoodsNum() != null, "商品数量不可为空");
            logger.info("【购物车系统】商品请求添加购物车，请求数据:{}", new Gson().toJson(ShopCart));
            int i = shopCartService.insertShopCart(ShopCart);
            if (i > 0) {
                logger.info("【购物车系统】商品添加购物车成功");
                return APIResult.buildSuccessResult("添加购物车成功!");
            }
            logger.info("【购物车系统】商品添加购物车失败");
            return APIResult.buildFailedResult(-1, "添加购物车失败");
        } catch (IllegalArgumentException ie) {
            logger.error("【购物车系统】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数异常");
        } catch (Exception e) {
            logger.error("【购物车系统】商品添加购物车时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
    }

    @RequestMapping(value = "deleteShopCart.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult deleteShopCart(@RequestBody ShopCart ShopCart) {
        try {
            Preconditions.checkArgument(ShopCart.getUserId() != null, "用户ID不可为空");
            Preconditions.checkArgument(ShopCart.getGoodsId() != null, "商品ID不可为空");
            logger.info("【购物车系统】请求删除购物车商品，请求数据:{}", new Gson().toJson(ShopCart));
            int i = shopCartService.deleteShopCart(ShopCart);
            if (i > 0) {
                logger.info("【购物车系统】删除购物车商品成功");
                return APIResult.buildSuccessResult("删除购物车商品成功!");
            }
            logger.info("【购物车系统】删除购物车商品失败");
            return APIResult.buildFailedResult(-1, "删除购物车商品失败");
        } catch (IllegalArgumentException ie) {
            logger.error("【购物车系统】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数异常");
        } catch (Exception e) {
            logger.error("【购物车系统】删除购物车时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
    }

    @RequestMapping(value = "deleteAllShopCart.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult deleteAllShopCart(@RequestBody RequestModel requestModel) {
        try {
            Preconditions.checkArgument(requestModel.getMessage() != null, "用户ID不可为空");
            long userId = Long.parseLong(requestModel.getMessage());
            logger.info("【购物车系统】请求清空购物车，请求数据:{}", new Gson().toJson(userId));
            int i = shopCartService.deleteAllShopCart(userId);
            if (i > 0) {
                logger.info("【购物车系统】清空购物车成功");
                return APIResult.buildSuccessResult("清空购物车成功!");
            }
            logger.info("【购物车系统】清空购物车失败");
            return APIResult.buildFailedResult(-1, "清空购物车失败");
        } catch (IllegalArgumentException ie) {
            logger.error("【购物车系统】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数异常");
        } catch (Exception e) {
            logger.error("【购物车系统】清空购物车时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
    }

    @RequestMapping(value = "updateShopCart.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult updateShopCart(@RequestBody ShopCart ShopCart) {
        try {
            Preconditions.checkArgument(ShopCart.getId() != null, "购物车ID不可为空");
            Preconditions.checkArgument(ShopCart.getGoodsNum() != null, "商品数量不可为空");
            logger.info("【购物车系统】请求更新购物车，请求数据:{}", new Gson().toJson(ShopCart));
            int i = shopCartService.updateShopCart(ShopCart);
            if (i > 0) {
                logger.info("【购物车系统】更新购物车成功");
                return APIResult.buildSuccessResult("更新购物车成功!");
            }
            logger.info("【购物车系统】更新购物车失败");
            return APIResult.buildFailedResult(-1, "更新购物车失败");
        } catch (IllegalArgumentException ie) {
            logger.error("【购物车系统】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数异常");
        } catch (Exception e) {
            logger.error("【购物车系统】更新购物车时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
    }

    @RequestMapping(value = "selectShopCart.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectShopCart(@RequestBody RequestModel requestModel) {
        try {
            Preconditions.checkArgument(requestModel.getMessage() != null, "用户ID不可为空");
            long userId = Long.parseLong(requestModel.getMessage());
            logger.info("【购物车系统】请求查询购物车信息，请求数据:{}", new Gson().toJson(userId));
            List<ShopCart> shopCarts = shopCartService.selectByUserId(userId);
            if (CollectionUtils.isEmpty(shopCarts)) {
                logger.info("【购物车系统】该用户购物车中无任何商品");
                return APIResult.buildFailedResult(-1, "您的购物车空空如也，快去选购吧~~~");
            }
            for (ShopCart shopCart : shopCarts) {
                List<Goods> goods = goodsService.selectByGoodsId(shopCart.getGoodsId());
                shopCart.setGoodsName(goods.get(0).getGoodsName());
                shopCart.setGoodsPrice(goods.get(0).getGoodsPrice());
                shopCart.setGoodsImg(goods.get(0).getGoodsPicture());
            }
            logger.info("购物车查询结果,:{}", new Gson().toJson(shopCarts));
            return APIResult.buildSuccessResult(shopCarts);
        } catch (IllegalArgumentException ie) {
            logger.error("【购物车系统】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数异常");
        } catch (Exception e) {
            logger.error("【购物车系统】查询购物车信息时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
    }
}

