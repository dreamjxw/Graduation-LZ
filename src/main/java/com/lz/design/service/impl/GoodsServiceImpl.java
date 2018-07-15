package com.lz.design.service.impl;

import com.google.common.collect.Lists;
import com.lz.design.dao.GoodsDao;
import com.lz.design.dao.StockDao;
import com.lz.design.model.Goods;
import com.lz.design.model.Stock;
import com.lz.design.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  0:33
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private StockDao stockDao;

    @Override
    public int deleteByGoodsId(Integer[] goodsId) {
        return goodsDao.deleteByGoodsId(goodsId);
    }

    @Override
    public int insertGoods(Goods goods) {
        return goodsDao.insertGoods(goods);
    }

    @Override
    public List<Goods> selectByGoodsId(Integer goodsId) {
        return goodsDao.selectByGoodsId(goodsId);
    }

    @Override
    public int updateByGoodsId(Goods goods) {
        return goodsDao.updateByGoodsId(goods);
    }

    @Override
    public List<Goods> searchGoods(String contents) {
        return goodsDao.searchGoods(contents);
    }

    @Override
    public List<Goods> selectGoodsBySomething(Integer className) {
        return goodsDao.selectGoodsBySomething(className);
    }

    @Override
    public List<Goods> selectGoodsByExpire(Date expireTime) {
        return goodsDao.selectGoodsByExpire(expireTime);
    }

    @Override
    public List<Goods> selectUpGoods() {
        List<Stock> stocks = stockDao.selectUpGoodsStock();
        List<Goods> list = Lists.newArrayList();
        for (Stock stock : stocks) {
            List<Goods> goods = goodsDao.selectByGoodsId(stock.getGoodsId());
            list.add(goods.get(0));
        }
        return list;
    }

    @Override
    public List<Goods> selectByEach(Integer[] goodsIds) {
        return goodsDao.selectByEach(goodsIds);
    }

    @Override
    public Goods selectGoodsByName(String goodsName) {
        return goodsDao.selectGoodsByName(goodsName);
    }
}
