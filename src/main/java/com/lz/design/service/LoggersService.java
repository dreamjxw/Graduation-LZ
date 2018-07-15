package com.lz.design.service;

import com.lz.design.model.Loggers;

import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  15:16
 */
public interface LoggersService {
    /**
     * 插入日志
     *
     * @param loggers
     * @return
     */
    int insertLoggers(Loggers loggers);

    /**
     * 根据UserId查询日志
     *
     * @param loggers
     * @return
     */
    List<Loggers> selectByUserId(Loggers loggers);

    /**
     * 查询某一时间段日志
     *
     * @param loggers
     * @return
     */
    List<Loggers> selectByTime(Loggers loggers);
}
