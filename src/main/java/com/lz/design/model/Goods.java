package com.lz.design.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:12
 * 商品信息
 */
public class Goods extends Stock {
    /**
     * 商品ID
     */
    private transient Integer goodsId;
    /**
     * 商品名称
     */
    private transient String goodsName;
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
     * 商品是否促销（1：是 0：否）
     */
    private Integer goodsSales;
    /**
     * 赠品信息
     */
    private List<GoodsGift> goodsGift;
    /**
     * 促销截止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date goodsSalesDowntime;
    /**
     * 商品添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date goodsUptime;
    /**
     * 商品过期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date goodsExpire;
    /**
     * 商品描述
     */
    private String goodsDescribe;

    @Override
    public Integer getGoodsId() {
        return goodsId;
    }

    @Override
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture == null ? null : goodsPicture.trim();
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
        this.goodsClass = goodsClass == null ? null : goodsClass.trim();
    }

    public Integer getGoodsSales() {
        return goodsSales;
    }

    public void setGoodsSales(Integer goodsSales) {
        this.goodsSales = goodsSales;
    }

    public List<GoodsGift> getGoodsGift() {
        return goodsGift;
    }

    public void setGoodsGift(List<GoodsGift> goodsGift) {
        this.goodsGift = goodsGift;
    }

    public Date getGoodsSalesDowntime() {
        return goodsSalesDowntime;
    }

    public void setGoodsSalesDowntime(Date goodsSalesDowntime) {
        this.goodsSalesDowntime = goodsSalesDowntime;
    }

    public Date getGoodsUptime() {
        return goodsUptime;
    }

    public void setGoodsUptime(Date goodsUptime) {
        this.goodsUptime = goodsUptime;
    }

    public Date getGoodsExpire() {
        return goodsExpire;
    }

    public void setGoodsExpire(Date goodsExpire) {
        this.goodsExpire = goodsExpire;
    }

    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe == null ? null : goodsDescribe.trim();
    }
}