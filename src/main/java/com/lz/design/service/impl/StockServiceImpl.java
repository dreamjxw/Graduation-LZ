package com.lz.design.service.impl;

import com.google.gson.Gson;
import com.lz.design.dao.StockDao;
import com.lz.design.model.Stock;
import com.lz.design.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  1:14
 */
@Service
public class StockServiceImpl implements StockService {
    private static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
    @Autowired
    private StockDao stockDao;

    @Override
    public int insertStock(Stock stock) {
        return stockDao.insertStock(stock);
    }

    @Override
    public List<Stock> selectStock(Integer goodsId) {
        return stockDao.selectStock(goodsId);
    }

    @Override
    public int updateStockByGoodsId(Stock stock) {
        logger.info("查询该商品已有库存信息,:{}", new Gson().toJson(stock.getGoodsId()));
        List<Stock> stock1 = stockDao.selectStock(stock.getGoodsId());
        if (stock1.get(0).getGoodsGround() != null) {
            if (stock.getGoodsGround() != null) {
                stock1.get(0).setGoodsGround(stock.getGoodsGround() + stock1.get(0).getGoodsGround());
            }
        } else {
            if (stock.getGoodsGround() != null) {
                stock1.get(0).setGoodsGround(stock.getGoodsGround());
            }
        }
        if (stock.getGoodsStockIn() != null) {
            stock1.get(0).setGoodsStockIn(stock.getGoodsStockIn() + stock1.get(0).getGoodsStockIn());
        }
        if (stock1.get(0).getGoodsStockOut() != null) {
            if (stock.getGoodsStockOut() != null) {
                stock1.get(0).setGoodsStockOut(stock.getGoodsStockOut() + stock1.get(0).getGoodsStockOut());
            }
        } else {
            if (stock.getGoodsStockOut() != null) {
                stock1.get(0).setGoodsStockOut(stock.getGoodsStockOut());
            }
        }
        logger.info("要更改的库存信息，：{}", new Gson().toJson(stock1));
        return stockDao.updateStockByGoodsId(stock1.get(0));
    }

    @Override
    public int deleteStockByGoodsId(Integer[] goodsId) {
        stockDao.deleteByGoodsId(goodsId);
        return 1;
    }
}
