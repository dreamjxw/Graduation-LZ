package com.lz.design.dao;

import com.lz.design.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:05
 */
@Repository
public interface UserDao {
    /**
     * 根据UserID删除用户
     *
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") Long userId);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    int insertUser(@Param("user") User user);

    /**
     * 根据UserID查询用户
     *
     * @param userId
     * @return
     */
    User selectByUserId(@Param("userId") Long userId);

    /**
     * 登录校验
     *
     * @param userName
     * @param userPassword
     * @return
     */
    User selectByLoggin(@Param("userName") String userName, @Param("userPassword") String userPassword);

    /**
     * 根据UserName查询用户(用于check重复)
     *
     * @param userName
     * @return
     */
    User selectByUserName(@Param("userName") String userName);

    /**
     * 根据用户权限查询
     *
     * @param userPower
     * @return
     */
    List<User> selectByUserPower(@Param("userPower") Integer userPower);


    /**
     * 修改用户信息（包括权限）
     *
     * @param user
     * @return
     */
    int updateUserInfo(@Param("user") User user);

    /**
     * 查询所有用户信息
     *
     * @return
     */
    List<User> selectAllUser();
}