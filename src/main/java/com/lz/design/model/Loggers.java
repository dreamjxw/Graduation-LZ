package com.lz.design.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Loggers {
    /**
     * 日志主键
     */
    private Long loggerId;
    /**
     * 操作人ID
     */
    private Long userId;
    /**
     * 日志内容
     */
    private String loggerMessage;
    /**
     * 日志时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date loggerTime;
    /**
     * 起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
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

    public Long getLoggerId() {
        return loggerId;
    }

    public void setLoggerId(Long loggerId) {
        this.loggerId = loggerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoggerMessage() {
        return loggerMessage;
    }

    public void setLoggerMessage(String loggerMessage) {
        this.loggerMessage = loggerMessage == null ? null : loggerMessage.trim();
    }

    public Date getLoggerTime() {
        return loggerTime;
    }

    public void setLoggerTime(Date loggerTime) {
        this.loggerTime = loggerTime;
    }
}