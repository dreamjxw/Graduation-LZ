package com.lz.design.model;

import java.util.Date;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  15:52
 * 时间段查询日志请求格式
 */
public class LoggerReq {
    /**
     * 起始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
