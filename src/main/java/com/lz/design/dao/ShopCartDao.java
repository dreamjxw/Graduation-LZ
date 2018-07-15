package com.lz.design.dao;


import com.lz.design.model.ShopCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xingwu.Jia [xingwuj@tujia.com]
 * @date 2018/1/18 11:32
 */
@Repository
public interface ShopCartDao {
    /**
     * 添加购物车
     *
     * @param shopCart
     * @return
     */
    int insertShopCart(@Param("shopCart") ShopCart shopCart);

    /**
     * 更改购物车
     *
     * @param shopCart
     * @return
     */
    int updateShopCart(@Param("shopCart") ShopCart shopCart);

    /**
     * 删除购物车（删除某一件商品）
     *
     * @param shopCart
     * @return
     */
    int deleteShopCart(@Param("shopCart") ShopCart shopCart);

    /**
     * 清空购物车
     *
     * @param userId
     * @return
     */
    int deleteAllShopCart(@Param("userId") Long userId);

    /**
     * 批量删除购物车商品（下订单后购物车中商品将被删除）
     *
     * @param userId
     * @param goodsId
     * @return
     */
    int deleteShopCartByGoodsIdBatch(@Param("userId") Long userId, @Param("goodsId") int[] goodsId);

    /**
     * 添加购物车时检测商品重复性
     *
     * @param userId
     * @param goodsId
     * @return
     */
    ShopCart checkRepeat(@Param("userId") Long userId, @Param("goodsId") Integer goodsId);

    /**
     * 根据userId查询购物车信息
     *
     * @param userId
     * @return
     */
    List<ShopCart> selectByUserId(@Param("userId") Long userId);
}