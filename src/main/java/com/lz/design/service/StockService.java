package com.lz.design.service;

import com.lz.design.model.Stock;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:19
 */
public interface StockService {
    /**
     * 添加库存(入库)
     *
     * @param stock
     * @return
     */
    int insertStock(Stock stock);

    /**
     * 查询库存
     *
     * @param goodsId
     * @return
     */
    List<Stock> selectStock(Integer goodsId);

    /**
     * 更改商品库存
     *
     * @param stock
     * @return
     */
    int updateStockByGoodsId(Stock stock);

    /**
     * 根据商品Id清除库存
     *
     * @param goodsId
     * @return
     */
    int deleteStockByGoodsId(Integer[] goodsId);
}
