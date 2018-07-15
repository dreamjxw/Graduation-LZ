package com.lz.design.service;

import com.lz.design.model.User;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:19
 */
public interface UserService {
    /**
     * 根据UserID删除用户
     *
     * @param userId
     * @return
     */
    int deleteByUserId(Long userId);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    int userRegister(User user);

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    int userLoggin(User user);

    /**
     * 根据UserID查询用户
     *
     * @param userId
     * @return
     */
    User selectByUserId(Long userId);

    /**
     * 根据UserName查询用户(用于check重复)
     *
     * @param userName
     * @return
     */
    User selectByUserName(String userName);

    /**
     * 修改用户信息（包括权限）
     *
     * @param user
     * @return
     */
    int updateUserInfo(User user);
    /**
     * 根据用户权限查询
     *
     * @param userPower
     * @return
     */
    List<User> selectByUserPower(Integer userPower);
    /**
     * 查询所有用户信息
     *
     * @return
     */
    List<User> selectAllUser();
}
