package com.lz.design.service;

import com.lz.design.model.ShopCart;

import java.util.List;

/**
 * @Author Xingwu.Jia [xingwuj@tujia.com]
 * @Date 2018/2/26 19:43
 * @Description
 */
public interface ShopCartService {
    /**
     * 添加购物车
     *
     * @param shopCart
     * @return
     */
    int insertShopCart(ShopCart shopCart);

    /**
     * 根据userId查询购物车信息
     *
     * @param userId
     * @return
     */
    List<ShopCart> selectByUserId(Long userId);

    /**
     * 更改购物车（主要是商品数量）
     *
     * @param shopCart
     * @return
     */
    int updateShopCart(ShopCart shopCart);

    /**
     * 删除购物车（删除某一件商品）
     *
     * @param shopCart
     * @return
     */
    int deleteShopCart(ShopCart shopCart);

    /**
     * 清空购物车
     *
     * @param userId
     * @return
     */
    int deleteAllShopCart(Long userId);
}
