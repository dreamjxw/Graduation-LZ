package com.lz.design.service;

import com.lz.design.model.Complain;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:20
 */
public interface ComplainService {
    /**
     * 根据留言Id删除留言
     *
     * @param complainId
     * @return
     */
    int deleteByComplainId(Integer complainId);

    /**
     * 添加留言
     *
     * @param complain
     * @return
     */
    int insertComplain(Complain complain);

    /**
     * 根据留言ID查询留言
     *
     * @param complainId
     * @return
     */
    Complain selectByComplainId(Integer complainId);

    /**
     * 根据商品ID查询留言
     *
     * @param goodsId
     * @return
     */
    List<Complain> selectByGoodsId(Integer goodsId);

    /**
     * 根据用户ID查询留言
     *
     * @param userId
     * @return
     */
    List<Complain> selectByUserId(Long userId);

    /**
     * 根据留言ID回复留言
     *
     * @param complain
     * @param makeId
     * @return
     */
    int updateByComplainId(Complain complain, String makeId);
}
