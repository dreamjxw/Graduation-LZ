package com.lz.design.dao;

import com.lz.design.model.Stock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:04
 */
@Repository
public interface StockDao {
    /**
     * 添加库存
     *
     * @param stock
     * @return
     */
    int insertStock(@Param("stock") Stock stock);

    /**
     * 查询库存
     *
     * @param goodsId
     * @return
     */
    List<Stock> selectStock(@Param("goodsId") Integer goodsId);

    /**
     * 根据商品Id删除库存
     *
     * @param goodsIds
     * @return
     */
    void deleteByGoodsId(@Param("goodsIds") Integer[] goodsIds);

    /**
     * 根据商品Id更改库存
     *
     * @param stock
     * @return
     */
    int updateStockByGoodsId(@Param("stock") Stock stock);

    /**
     * 查询已上架商品ID
     *
     * @return
     */
    List<Stock> selectUpGoodsStock();

}