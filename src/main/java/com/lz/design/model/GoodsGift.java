package com.lz.design.model;

import java.io.Serializable;

/**
 * @author Xingwu.Jia
 * @date 2018/5/27  14:23
 */
public class GoodsGift implements Serializable {
    private static final long serialVersionUID = -3572806481602672266L;
    /**
     * 商品ID
     */
    private  Integer goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品图片
     */
    private String goodsPicture;
    /**
     * 商品价格
     */
    private Double goodsPrice;
    /**
     * 商品类别
     */
    private String goodsClass;
    /**
     * 商品描述
     */
    private String goodsDescribe;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsClass() {
        return goodsClass;
    }

    public void setGoodsClass(String goodsClass) {
        this.goodsClass = goodsClass;
    }

    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
    }
}
