package com.lz.design.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:10
 * 会员活动信息
 */
public class Activity {
    /**
     * 活动ID
     */
    private Integer activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动对象（对应用户等级）
     */
    private Integer activityTarget;
    /**
     * 活动折扣
     */
    private Double activityDiscount;
    /**
     * 活动状态（1：有效 0：无效）
     */
    private Integer activityState;
    /**
     * 活动开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date activityTime;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public Integer getActivityTarget() {
        return activityTarget;
    }

    public void setActivityTarget(Integer activityTarget) {
        this.activityTarget = activityTarget;
    }

    public Double getActivityDiscount() {
        return activityDiscount;
    }

    public void setActivityDiscount(Double activityDiscount) {
        this.activityDiscount = activityDiscount;
    }

    public Integer getActivityState() {
        return activityState;
    }

    public void setActivityState(Integer activityState) {
        this.activityState = activityState;
    }

    public Date getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(Date activityTime) {
        this.activityTime = activityTime;
    }
}