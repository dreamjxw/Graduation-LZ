package com.lz.design.service;

import com.lz.design.model.Activity;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:19
 */
public interface ActivityService {
    /**
     * 删除会员活动
     *
     * @param activityId
     * @return
     */
    int deleteByActivityId(Integer activityId);

    /**
     * 添加会员活动
     *
     * @param activity
     * @return
     */
    int insertActivity(Activity activity);

    /**
     * 查询会员活动 by activityId
     *
     * @param activityId
     * @return
     */
    Activity selectByActivityId(Integer activityId);

    /**
     * 查询会员活动 by activityName
     *
     * @param activityName
     * @return
     */
    Activity selectByActivityName(String activityName);

    /**
     * 根据活动对象查看
     *
     * @param activityTarget
     * @return
     */
    List<Activity> selectByActivityTarget(Integer activityTarget);

    /**
     * 更改会员活动
     *
     * @param activity
     * @return
     */
    int updateByActivityId(Activity activity);
}
