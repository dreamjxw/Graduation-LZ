package com.lz.design.service;

import com.lz.design.model.Goods;
import com.lz.design.model.Stock;

import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:20
 */
public interface GoodsService {
    /**
     * 根据商品Id删除商品
     *
     * @param goodsId
     * @return
     */
    int deleteByGoodsId(Integer[] goodsId);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    int insertGoods(Goods goods);

    /**
     * 查询商品
     *
     * @param goodsId
     * @return
     */
    List<Goods> selectByGoodsId(Integer goodsId);

    /**
     * 更改商品属性
     *
     * @param goods
     * @return
     */
    int updateByGoodsId(Goods goods);

    /**
     * 搜索
     *
     * @param contents
     * @return
     */
    List<Goods> searchGoods(String contents);

    /**
     * 按条件查看(是否促销)
     *
     * @param className
     * @return
     */
    List<Goods> selectGoodsBySomething(Integer className);

    /**
     * 查询快过期商品
     *
     * @param expireTime
     * @return
     */
    List<Goods> selectGoodsByExpire(Date expireTime);

    /**
     * 查询已上架商品
     *
     * @return
     */
    List<Goods> selectUpGoods();
    /**
     * 根据商品Id批量查询商品
     *
     * @param goodsIds
     * @return
     */
    List<Goods> selectByEach(Integer[] goodsIds);
    /**
     * 按名称查看
     *
     * @param goodsName
     * @return
     */
    Goods selectGoodsByName(String goodsName);
}
