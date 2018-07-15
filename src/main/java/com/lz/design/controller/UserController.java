package com.lz.design.controller;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.lz.design.common.APIResult;
import com.lz.design.common.base64.ImgBase64;
import com.lz.design.model.Loggers;
import com.lz.design.model.RequestModel;
import com.lz.design.model.User;
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
 * @date 2018/5/20  1:59
 */
@CrossOrigin
@Controller
@RequestMapping("/lz/design/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private LoggersService loggersService;

    @RequestMapping(value = "userRegister.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult userRegister(@RequestBody User user) {
        try {
            Preconditions.checkArgument(user.getUserName() != null, "用户名称不能为空");
            Preconditions.checkArgument(user.getUserPassword() != null, "用户密码不能为空");
            Preconditions.checkArgument(user.getUserHeadurl() != null, "用户头像地址不能为空");
            Preconditions.checkArgument(user.getUserSex() != null, "用户性别不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(user));
            String substring = user.getUserHeadurl().substring(22);
            logger.info("头像Base64,:{}", new Gson().toJson(substring));
            ImgBase64 base64 = new ImgBase64();
            String millis = String.valueOf(DateTime.now().getMillis());
            base64.GenerateImage(substring, millis);
            user.setUserHeadurl("head/" + millis + ".png");
            int i = userService.userRegister(user);
            if (i == 1) {
                return APIResult.buildSuccessResult("注册成功!");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【用户模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "请填写完整信息!");
        } catch (Exception e) {
            logger.error("【用户模块】获取用户信息时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "注册失败！");
    }

    @RequestMapping(value = "userLoggin.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult userLoggin(@RequestBody User user) {
        try {
            Preconditions.checkArgument(user.getUserName() != null, "用户名称不能为空");
            Preconditions.checkArgument(user.getUserPassword() != null, "用户密码不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(user));
            int i = userService.userLoggin(user);
            if (i == 1) {
                logger.info("用户登录成功");
                User user1 = userService.selectByUserName(user.getUserName());
                logger.info("传入前端的用户信息：{}", new Gson().toJson(user1));
                return APIResult.buildSuccessResult(user1);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【用户模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【用户模块】获取用户信息时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "登录失败！");
    }

    /**
     * 更改用户信息（包括账户充值）
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "updateUserInfo.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult updateUserInfo(@RequestBody User user, HttpServletRequest req) {
        try {
            String userId = req.getParameter("userId");
            String changePower = req.getParameter("changePower");
            Preconditions.checkArgument(user.getUserId() != null, "被操作人Id不能为空");
            Preconditions.checkArgument(userId != null, "操作人Id不能为空");
            Preconditions.checkArgument(changePower != null, "操作类型不能为空");
            int anInt = Integer.parseInt(changePower);
            User user2 = userService.selectByUserId(Long.valueOf(userId));
            if (anInt == 1 && user2.getUserPower() != 4) {
                logger.warn("该用户无权限修改用户权限，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
//            if (anInt != 1 && !Objects.equals(userId, user.getUserId().toString())) {
//                logger.info("非本人操作无法修改");
//                return APIResult.buildFailedResult(-1, "非本人操作无法修改");
//            }
            if (anInt != 1) {
                user.setUserPower(null);
            }
            logger.info("传入数据,:{}", new Gson().toJson(user));
            if (user.getUserAccount() != null) {
                Double userAccount = userService.selectByUserId(user.getUserId()).getUserAccount();
                user.setUserAccount(userAccount + user.getUserAccount());
            }
            int i = userService.updateUserInfo(user);
            if (i > 0) {
                logger.info("修改用户信息成功");
                if (user2.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员:" + user2.getUserName() + "修改了用户信息,被修改人Id:" + user.getUserId());
                    loggersService.insertLoggers(loggers);
                }
                User user1 = userService.selectByUserId(user.getUserId());
                logger.info("传入前端的用户信息：{}", new Gson().toJson(user1));
                return APIResult.buildSuccessResult("更改用户信息成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【用户模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【用户模块】获取用户信息时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "修改用户信息失败");
    }

    @RequestMapping(value = "deleteUser.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult deleteUser(@RequestBody User user, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() != 4) {
                logger.warn("该用户无权限删除用户信息，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            Preconditions.checkArgument(user.getUserId() != null, "用户Id不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(user));
            int i = userService.deleteByUserId(user.getUserId());
            if (i > 0) {
                logger.info("删除用户成功");
                if (user2.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user2.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员:" + user2.getUserName() + "删除了用户信息,被删除人Id:" + user.getUserId());
                    loggersService.insertLoggers(loggers);
                }
                return APIResult.buildSuccessResult("删除用户成功");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【用户模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【用户模块】获取用户信息时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "删除用户失败");
    }

    @RequestMapping(value = "selectUserByUserId.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectUserByUserId(@RequestBody RequestModel requestModel) {
        try {
            logger.info("请求参数,:{}", requestModel.getMessage());
            Preconditions.checkArgument(requestModel.getMessage() != null, "用户Id不能为空");
            if (requestModel.getMessage() == null) {
                User user = userService.selectByUserId(null);
                if (user != null) {
                    logger.info("查询用户成功，：{}", new Gson().toJson(user));
                    return APIResult.buildSuccessResult(user);
                }
            }
            long userId = Long.parseLong(requestModel.getMessage());
            logger.info("传入数据,:{}", new Gson().toJson(userId));
            User user = userService.selectByUserId(userId);
            if (user != null) {
                logger.info("查询用户成功，：{}", new Gson().toJson(user));
                return APIResult.buildSuccessResult(user);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【用户模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【用户模块】获取用户信息时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询用户失败");
    }

    @RequestMapping(value = "selectUserByUserPower.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectUserByUserPower(@RequestBody Integer userPower, HttpServletRequest req) {
        try {
            String userId1 = req.getParameter("userId");
            Preconditions.checkArgument(userId1 != null, "操作人Id不能为空");
            User user2 = userService.selectByUserId(Long.valueOf(userId1));
            if (user2.getUserPower() != 4) {
                logger.warn("该用户无权限查看用户信息，：{}", new Gson().toJson(user2));
                return APIResult.buildFailedResult(-1, "您无权限执行此操作");
            }
            Preconditions.checkArgument(userPower != null, "权限不能为空");
            logger.info("传入数据,:{}", new Gson().toJson(userPower));
            List<User> userList = userService.selectByUserPower(userPower);
            if (userList != null) {
                logger.info("查询用户成功，：{}", new Gson().toJson(userList));
                if (user2.getUserPower() == 4) {
                    Loggers loggers = new Loggers();
                    loggers.setUserId(user2.getUserId());
                    loggers.setLoggerTime(DateTime.now().toDate());
                    loggers.setLoggerMessage("系统管理员:" + user2.getUserName() + "查询了" + userPower + "等级的用户列表:" + userList);
                    loggersService.insertLoggers(loggers);
                }
                return APIResult.buildSuccessResult(userList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【用户模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【用户模块】获取用户信息时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询用户失败");
    }

    @RequestMapping(value = "selectAllUser.htm", method = RequestMethod.POST)
    @ResponseBody
    public APIResult selectAllUser() {
        try {
            List<User> userList = userService.selectAllUser();
            if (userList != null) {
                logger.info("查询用户成功，：{}", new Gson().toJson(userList));
                return APIResult.buildSuccessResult(userList);
            }
        } catch (IllegalArgumentException ie) {
            logger.error("【用户模块】非法参数异常", ie);
            return APIResult.buildFailedResult(-1, "非法参数");
        } catch (Exception e) {
            logger.error("【用户模块】获取用户信息时出现异常", e);
            return APIResult.buildFailedResult(-1, "服务器开小差了~~  请稍后重试");
        }
        return APIResult.buildFailedResult(-1, "查询用户失败");
    }
}
