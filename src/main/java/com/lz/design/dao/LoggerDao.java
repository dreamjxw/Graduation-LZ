package com.lz.design.dao;


import com.lz.design.model.Loggers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  14:03
 */
@Repository
public interface LoggerDao {
    /**
     * 插入日志
     *
     * @param loggers
     * @return
     */
    int insertLoggers(@Param("loggers") Loggers loggers);

    /**
     * 根据UserId查询日志
     *
     * @param loggers
     * @return
     */
    List<Loggers> selectByUserId(@Param("loggers") Loggers loggers);

    /**
     * 查询日志
     * @param userId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Loggers> selectByTime(@Param("userId") Long userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

}