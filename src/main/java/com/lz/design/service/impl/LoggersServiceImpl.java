package com.lz.design.service.impl;

import com.lz.design.dao.LoggerDao;
import com.lz.design.model.Loggers;
import com.lz.design.service.LoggersService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  15:17
 */
@Service
public class LoggersServiceImpl implements LoggersService {
    @Autowired
    private LoggerDao loggerDao;

    @Override
    public int insertLoggers(Loggers loggers) {
        loggers.setLoggerTime(DateTime.now().toDate());
        return loggerDao.insertLoggers(loggers);
    }

    @Override
    public List<Loggers> selectByUserId(Loggers loggers) {
        return loggerDao.selectByUserId(loggers);
    }

    @Override
    public List<Loggers> selectByTime(Loggers loggers) {
        return loggerDao.selectByTime(loggers.getUserId(), loggers.getStartTime(), loggers.getEndTime());
    }
}
