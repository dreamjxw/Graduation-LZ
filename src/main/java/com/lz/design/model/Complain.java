package com.lz.design.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:11
 * 投诉留言
 */
public class Complain {
    /**
     * 投诉ID
     */
    private Integer complainId;
    /**
     * 投诉人ID
     */
    private Long complainUserId;
    /**
     * 投诉商品ID
     */
    private Integer complainGoodsId;
    /**
     * 投诉信息
     */
    private String complainDescribe;
    /**
     * 投诉时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date complainStartTime;
    /**
     * 投诉更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date complainUpdateTime;
    /**
     * 投诉信息状态
     */
    private Integer complainState;

    public Date getComplainUpdateTime() {
        return complainUpdateTime;
    }

    public void setComplainUpdateTime(Date complainUpdateTime) {
        this.complainUpdateTime = complainUpdateTime;
    }

    public Integer getComplainState() {
        return complainState;
    }

    public void setComplainState(Integer complainState) {
        this.complainState = complainState;
    }

    public Integer getComplainId() {
        return complainId;
    }

    public void setComplainId(Integer complainId) {
        this.complainId = complainId;
    }

    public Long getComplainUserId() {
        return complainUserId;
    }

    public void setComplainUserId(Long complainUserId) {
        this.complainUserId = complainUserId;
    }

    public Integer getComplainGoodsId() {
        return complainGoodsId;
    }

    public void setComplainGoodsId(Integer complainGoodsId) {
        this.complainGoodsId = complainGoodsId;
    }

    public String getComplainDescribe() {
        return complainDescribe;
    }

    public void setComplainDescribe(String complainDescribe) {
        this.complainDescribe = complainDescribe == null ? null : complainDescribe.trim();
    }

    public Date getComplainStartTime() {
        return complainStartTime;
    }

    public void setComplainStartTime(Date complainStartTime) {
        this.complainStartTime = complainStartTime;
    }
}