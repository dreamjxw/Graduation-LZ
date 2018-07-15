package com.lz.design.service.impl;

import com.google.gson.Gson;
import com.lz.design.dao.ShopCartDao;
import com.lz.design.model.ShopCart;
import com.lz.design.service.ShopCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author Xingwu.Jia [xingwuj@tujia.com]
 * @Date 2018/5/22 11:47
 * @Description
 */
@Service
public class ShopCartServiceImpl implements ShopCartService {
    private static Logger logger = LoggerFactory.getLogger(ShopCartServiceImpl.class);
    @Autowired
    private ShopCartDao shopCartDao;

    @Override
    public int insertShopCart(ShopCart ShopCart) {
        logger.info("【购物车系统】商品请求添加购物车，请求数据:{}", new Gson().toJson(ShopCart));
        ShopCart shopCart = shopCartDao.checkRepeat(ShopCart.getUserId(), ShopCart.getGoodsId());
        if (shopCart == null) {
            logger.info("【购物车系统】购物车中无该商品，新添加");
            return shopCartDao.insertShopCart(ShopCart);
        }
        logger.info("【购物车系统】购物车中有该商品，修改数量");
        int newWineNum = ShopCart.getGoodsNum() + shopCart.getGoodsNum();
        logger.info("【购物车系统】购物车中已有该商品" + shopCart.getGoodsNum() + "件，新添加" + ShopCart.getGoodsNum() + "件，更新后购物车中商品" +
                newWineNum + "件");
        ShopCart.setGoodsNum(newWineNum);
        return updateShopCart(ShopCart);
    }

    @Override
    public List<ShopCart> selectByUserId(Long userId) {
        logger.info("【购物车系统】请求查询购物车，请求用户ID:{}", new Gson().toJson(userId));
        List<ShopCart> shopCarts = shopCartDao.selectByUserId(userId);
        if (CollectionUtils.isEmpty(shopCarts)) {
            logger.info("【购物车系统】该用户购物车为空");
            return null;
        }
        logger.info("查询到购物车信息,:{}", new Gson().toJson(shopCarts));
        return shopCarts;
    }

    @Override
    public int updateShopCart(ShopCart ShopCart) {
        logger.info("【购物车系统】请求更改购物车信息，请求数据:{}", new Gson().toJson(ShopCart));
        return shopCartDao.updateShopCart(ShopCart);
    }

    @Override
    public int deleteShopCart(ShopCart ShopCart) {
        logger.info("【购物车系统】请求删除购物车商品，请求数据:{}", new Gson().toJson(ShopCart));
        return shopCartDao.deleteShopCart(ShopCart);
    }

    @Override
    public int deleteAllShopCart(Long userId) {
        logger.info("【购物车系统】用户请求清空购物车，请求数据:{}", new Gson().toJson(userId));
        return shopCartDao.deleteAllShopCart(userId);
    }
}

