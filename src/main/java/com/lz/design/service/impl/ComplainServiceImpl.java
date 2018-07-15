package com.lz.design.service.impl;

import com.google.gson.Gson;
import com.lz.design.dao.ComplainDao;
import com.lz.design.dao.UserDao;
import com.lz.design.model.Complain;
import com.lz.design.model.User;
import com.lz.design.service.ComplainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/21  14:51
 */
@Service
public class ComplainServiceImpl implements ComplainService {
    private static Logger logger = LoggerFactory.getLogger(ComplainServiceImpl.class);
    @Autowired
    private ComplainDao complainDao;
    @Autowired
    private UserDao userDao;

    @Override
    public int deleteByComplainId(Integer complainId) {
        return complainDao.deleteByComplainId(complainId);
    }

    @Override
    public int insertComplain(Complain complain) {
        /**
         * 判重处理
         */
        Complain checkRepeat = complainDao.checkRepeat(complain);
        if (checkRepeat != null) {
            logger.info("该用户已对该商品做出留言", new Gson().toJson(checkRepeat));
            return 0;
        }
        User user = userDao.selectByUserId(complain.getComplainUserId());
        complain.setComplainDescribe(user.getUserName()+": "+complain.getComplainDescribe());
        int i = complainDao.insertComplain(complain);
        if (i > 0) {
            logger.info("留言添加成功，请求信息:{}", new Gson().toJson(complain));
            return 1;
        }
        logger.info("留言添加失败，数据插入失败。请求信息:{}", new Gson().toJson(complain));
        return 0;
    }

    @Override
    public Complain selectByComplainId(Integer complainId) {
        return complainDao.selectByComplainId(complainId);
    }

    @Override
    public List<Complain> selectByGoodsId(Integer goodsId) {
        return complainDao.selectByGoodsId(goodsId);
    }

    @Override
    public List<Complain> selectByUserId(Long userId) {
        return complainDao.selectByUserId(userId);
    }

    /**
     * 更新留言（对留言做出回复）
     *
     * @param complain
     * @return
     */
    @Override
    public int updateByComplainId(Complain complain,String makeId) {
        if (complain.getComplainDescribe() == null) {
            logger.info("未进行回复操作，记录为已读，更改留言状态");
            return complainDao.updateByComplainId(complain);
        }
        logger.info("进行回复留言操作");
        /**
         * 回复留言，先拿出原有留言信息再与回复留言拼接
         */

        Complain complain1 = complainDao.selectByComplainId(complain.getComplainId());
        User user = userDao.selectByUserId(Long.valueOf(makeId));

        String message = complain1.getComplainDescribe() + "-"+user.getUserName()+"回复:" + complain.getComplainDescribe();
        complain.setComplainDescribe(message);
        return complainDao.updateByComplainId(complain);
    }
}
