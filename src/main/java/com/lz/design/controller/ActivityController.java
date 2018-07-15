package com.lz.design.controller;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.lz.design.common.APIResult;
import com.lz.design.model.Activity;
import com.lz.design.model.Loggers;
import com.lz.design.model.RequestModel;
import com.lz.design.model.User;
import com.lz.design.service.ActivityService;
import com.lz.design.service.LoggersService;
import com.lz.design.service.UserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/21  15:13
 */
@CrossOrigin
@Controller
@RequestMapping("/lz/design/activity")
public class ActivityController {
    private static Logger logger = LoggerFactory.getLogger(ActivityController.class);
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoggersService loggersService;

    @RequestMapping(value = "insertActivity.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult insertActivity(@RequestBody Activity activity, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() != 3) {
                logger.warn("该用户无权限发布会员活动，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            Preconditions.checkArgument(activity.getActivityName() != null, "活动名称不能为空");
            Preconditions.checkArgument(activity.getActivityTarget() != null, "活动对象不能为空");
            Preconditions.checkArgument(activity.getActivityDiscount() != null, "活动描述不能为空");
//            Preconditions.checkArgument(activity.getActivityTime() != null, "活动开始时间不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(activity));
            int i = activityService.insertActivity(activity);
            if (i > 0) {
                logger.info("添加活动成功");
                if (user2.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user2.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员" + user2.getUserName() + "添加了活动,活动名称:" + activity.getActivityName());
                    loggersService.insertLoggers(loggers);
                }
                return APIResult.buildSuccessResult("添加活动成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【活动模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【活动模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "添加活动失败");
    }

    @RequestMapping(value = "deleteActivity.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult deleteActivity(@RequestBody Integer activityId, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() != 3) {
                logger.warn("该用户无权限发布会员活动，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            Preconditions.checkArgument(activityId != null, "活动Id不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(activityId));
            int i = activityService.deleteByActivityId(activityId);
            if (i > 0) {
                logger.info("删除活动成功");
                Loggers loggers = new Loggers();
                loggers.setUserId(user2.getUserId());
                loggers.setLoggerTime(DateTime.now().toDate());
                loggers.setLoggerMessage("系统管理员" + user2.getUserName() + "删除了会员活动" + activityId);
                loggersService.insertLoggers(loggers);
                return APIResult.buildSuccessResult("删除活动成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【活动模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【活动模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "删除活动成功");
    }

    @RequestMapping(value = "updateActivity.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult updateActivity(@RequestBody Activity activity, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() != 3) {
                logger.warn("该用户无权限发布会员活动，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            Preconditions.checkArgument(activity.getActivityId() != null, "活动Id不能为空");
//            Preconditions.checkArgument(activity.getActivityName() != null, "活动名称不能为空");
//            Preconditions.checkArgument(activity.getActivityTarget() != null, "活动对象不能为空");
//            Preconditions.checkArgument(activity.getActivityDiscount() != null, "活动描述不能为空");
//            Preconditions.checkArgument(activity.getActivityTime() != null, "活动开始时间不能为空");
            if (activity.getActivityName() == null && activity.getActivityTarget() == null && activity.getActivityDiscount() == null) {
                logger.error("更改信息不能都为空");
                return APIResult.buildFailedResult(-1, "更改信息不能都为空");
            }
            logger.info("传入数据,:{}", new Gson().toJson(activity));
            int i = activityService.updateByActivityId(activity);
            if (i > 0) {
                logger.info("更改活动成功");
                Loggers loggers = new Loggers();
                loggers.setUserId(user2.getUserId());
                loggers.setLoggerTime(DateTime.now().toDate());
                loggers.setLoggerMessage("系统管理员" + user2.getUserName() + "修改了会员活动" + activity.getActivityName());
                loggersService.insertLoggers(loggers);
                return APIResult.buildSuccessResult("更改活动成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【活动模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【活动模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "更改活动成功");
    }

    @RequestMapping(value = "selectActivityById.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectActivityById(@RequestBody Integer activityId) {
        try {
            Preconditions.checkArgument(activityId != null, "活动Id不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(activityId));
            Activity activity = activityService.selectByActivityId(activityId);
            if (activity != null) {
                logger.info("查询活动成功,活动为：{}", new Gson().toJson(activity));
                return APIResult.buildSuccessResult(activity);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【活动模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【活动模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询活动失败");
    }

    @RequestMapping(value = "selectActivityList.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectActivityList(@RequestBody RequestModel requestModel) {
        try {
            Preconditions.checkArgument(requestModel.getMessage() != null, "用户权限不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(requestModel.getMessage()));
            Integer parseInt = Integer.parseInt(requestModel.getMessage());
            List<Activity> activityList = activityService.selectByActivityTarget(parseInt);
            if (activityList != null) {
                logger.info("查询活动列表成功,活动为：{}", new Gson().toJson(activityList));
                return APIResult.buildSuccessResult(activityList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【活动模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【活动模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询活动列表失败");
    }
}
