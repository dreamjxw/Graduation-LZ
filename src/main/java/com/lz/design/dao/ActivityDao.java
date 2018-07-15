package com.lz.design.dao;

import com.lz.design.model.Activity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:01
 */
@Repository
public interface ActivityDao {
    /**
     * 删除会员活动
     *
     * @param activityId
     * @return
     */
    int deleteByActivityId(@Param("activityId") Integer activityId);

    /**
     * 添加会员活动
     *
     * @param activity
     * @return
     */
    int insertActivity(@Param("activity") Activity activity);

    /**
     * 查询会员活动by activityId
     *
     * @param activityId
     * @return
     */
    Activity selectByActivityId(@Param("activityId") Integer activityId);

    /**
     * 查询会员活动by activityName
     *
     * @param activityName
     * @return
     */
    Activity selectByActivityName(@Param("activity") String activityName);

    /**
     * 根据活动对象查看
     *
     * @param activityTarget
     * @return
     */
    List<Activity> selectByActivityTarget(@Param("activityTarget") Integer activityTarget);

    /**
     * 更改会员活动
     *
     * @param activity
     * @return
     */
    int updateByActivityId(@Param("activity") Activity activity);
}