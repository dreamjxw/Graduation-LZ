package com.lz.design.service.impl;

import com.google.gson.Gson;
import com.lz.design.dao.UserDao;
import com.lz.design.model.User;
import com.lz.design.service.UserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Xingwu.Jia
 * @date 2018/5/20  1:43
 */
@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public int deleteByUserId(Long userId) {
        return userDao.deleteByUserId(userId);
    }

    @Override
    public int userRegister(User user) {
        logger.info("用户注册，请求参数:{}", new Gson().toJson(user));
        User user1 = userDao.selectByUserName(user.getUserName());
        if (user1 != null) {
            logger.info("用户注册失败，该用户已存在。请求用户:{}", new Gson().toJson(user.getUserName()));
            return 0;
        }
        int i = userDao.insertUser(user);
        if (i > 0) {
            logger.info("用户注册成功，请求用户:{}", new Gson().toJson(user.getUserName()));
            return 1;
        }
        logger.info("用户注册失败，数据插入失败。请求用户:{}", new Gson().toJson(user.getUserName()));
        return 0;
    }

    @Override
    public int userLoggin(User user) {
        logger.info("用户登录，请求参数:{}", new Gson().toJson(user));
        User user1 = userDao.selectByLoggin(user.getUserName(), user.getUserPassword());
        if (user1 == null) {
            logger.info("用户登录失败，请求用户:{}", new Gson().toJson(user.getUserName()));
            return 0;
        }
        logger.info("用户登录成功，请求用户:{}", new Gson().toJson(user.getUserName()));
        return 1;
    }


    @Override
    public User selectByUserId(Long userId) {
        return userDao.selectByUserId(userId);
    }

    @Override
    public User selectByUserName(String userName) {
        return userDao.selectByUserName(userName);
    }

    @Override
    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }

    @Override
    public List<User> selectByUserPower(Integer userPower) {
        return userDao.selectByUserPower(userPower);
    }

    @Override
    public List<User> selectAllUser() {
        return userDao.selectAllUser();
    }
}
