package com.lz.design.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:13
 * 订单信息
 */
public class Order {
    /**
     * 订单号
     */
    private Long orderId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订单详情
     */
    private List<OrderGoods> orderGoods;
    /**
     * 订单总价
     */
    private Double orderTotalprice;
    /**
     * 订单创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date orderDateStart;
    /**
     * 订单支付时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date orderDateEnd;
    /**
     * 订单状态   0：未支付    1：已支付    2.支付超时
     */
    private Byte payStatusId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderGoods> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<OrderGoods> orderGoods) {
        this.orderGoods = orderGoods;
    }

    public Double getOrderTotalprice() {
        return orderTotalprice;
    }

    public void setOrderTotalprice(Double orderTotalprice) {
        this.orderTotalprice = orderTotalprice;
    }

    public Date getOrderDateStart() {
        return orderDateStart;
    }

    public void setOrderDateStart(Date orderDateStart) {
        this.orderDateStart = orderDateStart;
    }

    public Date getOrderDateEnd() {
        return orderDateEnd;
    }

    public void setOrderDateEnd(Date orderDateEnd) {
        this.orderDateEnd = orderDateEnd;
    }

    public Byte getPayStatusId() {
        return payStatusId;
    }

    public void setPayStatusId(Byte payStatusId) {
        this.payStatusId = payStatusId;
    }
}