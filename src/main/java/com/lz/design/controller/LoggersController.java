package com.lz.design.controller;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.lz.design.common.APIResult;
import com.lz.design.model.LoggerReq;
import com.lz.design.model.Loggers;
import com.lz.design.service.LoggersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  15:48
 */
@CrossOrigin
@Controller
@RequestMapping("/lz/design/loggers")
public class LoggersController {
    private final Logger logger = LoggerFactory.getLogger(LoggersController.class);
    @Autowired
    private LoggersService loggersService;

    /**
     * 全查日志 OR  根据用户Id查询日志
     * @param loggerr
     * @return
     */
    @RequestMapping(value = "selectByUserId.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectByUserId(@RequestBody Loggers loggerr) {
        try {
//            Preconditions.checkArgument(userId != null, "用户Id不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(loggerr));
            List<Loggers> loggers = loggersService.selectByUserId(loggerr);
            if (loggers != null) {
                logger.info("查询日志成功,:{}", new Gson().toJson(loggers));
                return APIResult.buildSuccessResult(loggers);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【日志模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【日志模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询日志失败");
    }

    /**
     * 根据时间查询日志  OR  根据用户ID和时间查询日志
     * @param loggers
     * @return
     */
    @RequestMapping(value = "selectByTime.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectByTime(@RequestBody Loggers loggers) {
        try {
            Preconditions.checkArgument(loggers.getEndTime() != null, "终止时间不能为空");
            Preconditions.checkArgument(loggers.getStartTime() != null, "开始时间不能为空");
            Date startDate = new Date(String.valueOf(loggers.getStartTime()));
            Date endDate = new Date(String.valueOf(loggers.getEndTime()));
            loggers.setStartTime(startDate);
            loggers.setEndTime(endDate);
            logger.info("传入数据,:{}", new Gson().toJson(loggers));
            List<Loggers> loggers1= loggersService.selectByTime(loggers);
            if (loggers1 != null) {
                logger.info("查询日志成功,：{}", new Gson().toJson(loggers1));
                return APIResult.buildSuccessResult(loggers);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【日志模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【日志模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询日志失败");
    }
}
