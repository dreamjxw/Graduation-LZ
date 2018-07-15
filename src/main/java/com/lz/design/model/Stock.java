package com.lz.design.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:14
 * 库存信息
 */
public class Stock {
    /**
     * 主键
     */
    private Integer stockId;
    /**
     * 商品上架数
     */
    private Integer goodsGround;
    /**
     * 商品ID
     */
    private Integer goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品入库（库存）数
     */
    private Integer goodsStockIn;
    /**
     * 商品出库数
     */
    private Integer goodsStockOut;
    /**
     * 入库时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date stockInTime;
    /**
     * 出库时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date stockOutTime;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsGround() {
        return goodsGround;
    }

    public void setGoodsGround(Integer goodsGround) {
        this.goodsGround = goodsGround;
    }

    public Integer getGoodsStockIn() {
        return goodsStockIn;
    }

    public void setGoodsStockIn(Integer goodsStockIn) {
        this.goodsStockIn = goodsStockIn;
    }

    public Integer getGoodsStockOut() {
        return goodsStockOut;
    }

    public void setGoodsStockOut(Integer goodsStockOut) {
        this.goodsStockOut = goodsStockOut;
    }

    public Date getStockInTime() {
        return stockInTime;
    }

    public void setStockInTime(Date stockInTime) {
        this.stockInTime = stockInTime;
    }

    public Date getStockOutTime() {
        return stockOutTime;
    }

    public void setStockOutTime(Date stockOutTime) {
        this.stockOutTime = stockOutTime;
    }
}