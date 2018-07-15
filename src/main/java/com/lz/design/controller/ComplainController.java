package com.lz.design.controller;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.lz.design.common.APIResult;
import com.lz.design.model.Complain;
import com.lz.design.model.Loggers;
import com.lz.design.model.User;
import com.lz.design.service.ComplainService;
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
import java.util.Objects;

/**
 * @author Xingwu.Jia
 * @date 2018/5/22  10:34
 */
@CrossOrigin
@Controller
@RequestMapping("/lz/design/complain")
public class ComplainController {
    private static Logger logger = LoggerFactory.getLogger(ComplainController.class);
    @Autowired
    private ComplainService complainService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoggersService loggersService;

    @RequestMapping(value = "insertComplain.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult insertComplain(@RequestBody Complain complain) {
        try {
            Preconditions.checkArgument(complain.getComplainGoodsId() != null, "留言商品Id不能为空");
            Preconditions.checkArgument(complain.getComplainUserId() != null, "留言用户Id不能为空");
            Preconditions.checkArgument(complain.getComplainDescribe() != null, "留言信息不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(complain));
            complain.setComplainStartTime(DateTime.now().toDate());
            User user = userService.selectByUserId(complain.getComplainUserId());
            complain.setComplainDescribe(complain.getComplainDescribe());
            int i = complainService.insertComplain(complain);
            if (i > 0) {
                logger.info("留言添加成功,信息：{}", new Gson().toJson(complain));
                return APIResult.buildSuccessResult("留言添加成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【留言模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【留言模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "留言添加失败");
    }

    @RequestMapping(value = "deleteComplain.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult deleteComplain(@RequestBody Complain complain) {
        try {
            Preconditions.checkArgument(complain.getComplainId() != null, "留言Id不能为空");
            Preconditions.checkArgument(complain.getComplainUserId() != null, "留言用户Id不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(complain));
            Complain complain1 = complainService.selectByComplainId(complain.getComplainId());
            if (!Objects.equals(complain1.getComplainUserId(), complain.getComplainUserId())) {
                logger.info("留言人与操作人身份不一致，无法进行删除操作");
                return APIResult.buildFailedResult(-1, "留言人与操作人身份不一致，无法进行删除操作");
            }
            int i = complainService.deleteByComplainId(complain.getComplainId());
            if (i > 0) {
                logger.info("留言删除成功,信息：{}", new Gson().toJson(complain));
                return APIResult.buildSuccessResult("留言删除成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【留言模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【留言模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "留言删除失败");
    }

    @RequestMapping(value = "updateComplain.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult updateComplain(@RequestBody Complain complain, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            Preconditions.checkArgument(complain.getComplainId() != null, "留言Id不能为空");
            Preconditions.checkArgument(complain.getComplainDescribe() != null, "留言信息不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() <= 2) {
                logger.warn("该用户无权限处理留言，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            if (user2.getUserPower() == 4) {
                Loggers loggers = new Loggers();
                loggers.setUserId(user2.getUserId());
                loggers.setLoggerTime(DateTime.now().toDate());
                loggers.setLoggerMessage("系统管理员" + user2.getUserName() + "处理了留言" + complain.getComplainId());
                loggersService.insertLoggers(loggers);
            }
            logger.info("传入数据,:{}", new Gson().toJson(complain));
            complain.setComplainState(1);
            int i = complainService.updateByComplainId(complain,userId1);
            if (i > 0) {
                logger.info("留言更新成功,信息：{}", new Gson().toJson(complain));
                return APIResult.buildSuccessResult("留言更新成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【留言模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【留言模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "留言更新失败");
    }

    @RequestMapping(value = "selectByUserId.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectByUserId(@RequestBody Complain complain) {
        try {
//            Preconditions.checkArgument(complain.getComplainUserId() != null, "用户Id不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(complain.getComplainUserId()));
            List<Complain> complainList = complainService.selectByUserId(complain.getComplainUserId());
            if (complainList != null) {
                logger.info("查询留言成功,信息：{}", new Gson().toJson(complainList));
                return APIResult.buildSuccessResult(complainList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【留言模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【留言模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询留言失败");
    }

    @RequestMapping(value = "selectByGoodsId.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectByGoodsId(@RequestBody Complain complain) {
        try {
            Preconditions.checkArgument(complain.getComplainGoodsId() != null, "商品Id不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(complain.getComplainGoodsId()));
            List<Complain> complainList = complainService.selectByGoodsId(complain.getComplainGoodsId());
            if (complainList != null) {
                logger.info("查询留言成功,信息：{}", new Gson().toJson(complainList));
                return APIResult.buildSuccessResult(complainList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【留言模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【留言模块】活动模块出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询留言失败");
    }
}
