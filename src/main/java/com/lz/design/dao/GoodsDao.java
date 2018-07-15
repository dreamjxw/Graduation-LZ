package com.lz.design.dao;

import com.lz.design.model.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:06
 */
@Repository
public interface GoodsDao {
    /**
     * 根据商品Id删除商品
     *
     * @param goodsIds
     * @return
     */
    int deleteByGoodsId(@Param("goodsIds") Integer[] goodsIds);

    /**
     * 根据商品Id批量查询商品
     *
     * @param goodsIds
     * @return
     */
    List<Goods> selectByEach(@Param("goodsIds") Integer[] goodsIds);

    /**
     * 添加商品(入库)
     *
     * @param goods
     * @return
     */
    int insertGoods(@Param("goods") Goods goods);

    /**
     * 查询商品
     *
     * @param goodsId
     * @return
     */
    List<Goods> selectByGoodsId(@Param("goodsId") Integer goodsId);

    /**
     * 更改商品属性
     *
     * @param goods
     * @return
     */
    int updateByGoodsId(@Param("goods") Goods goods);

    /**
     * 搜索
     *
     * @param contents
     * @return
     */
    List<Goods> searchGoods(@Param("contents") String contents);

    /**
     * 按条件查看(类别、是否促销、是否赠品)
     *
     * @param className
     * @return
     */
    List<Goods> selectGoodsBySomething(@Param("className") Integer className);

    /**
     * 查询快过期商品
     *
     * @param expireTime
     * @return
     */
    List<Goods> selectGoodsByExpire(@Param("expireTime") Date expireTime);


    /**
     * 按名称查看
     *
     * @param goodsName
     * @return
     */
    Goods selectGoodsByName(@Param("goodsName") String goodsName);
}