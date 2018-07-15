package com.lz.design.service.impl;

import com.lz.design.dao.ActivityDao;
import com.lz.design.model.Activity;
import com.lz.design.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/21  15:07
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    private static Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);
    @Autowired
    private ActivityDao activityDao;

    @Override
    public int deleteByActivityId(Integer activityId) {
        return activityDao.deleteByActivityId(activityId);
    }

    @Override
    public int insertActivity(Activity activity) {
        /**
         * 判重
         */
        Activity activity1 = selectByActivityName(activity.getActivityName());
        if (activity1 == null) {
            logger.info("该活动不存在，可以新添加。");
            return activityDao.insertActivity(activity);
        }
        logger.info("该活动已存在，不可以新添加。");
        return 0;
    }

    @Override
    public Activity selectByActivityId(Integer activityId) {
        return activityDao.selectByActivityId(activityId);
    }

    @Override
    public Activity selectByActivityName(String activityName) {
        return activityDao.selectByActivityName(activityName);
    }

    @Override
    public List<Activity> selectByActivityTarget(Integer activityTarget) {
        return activityDao.selectByActivityTarget(activityTarget);
    }

    @Override
    public int updateByActivityId(Activity activity) {
        return activityDao.updateByActivityId(activity);
    }
}
