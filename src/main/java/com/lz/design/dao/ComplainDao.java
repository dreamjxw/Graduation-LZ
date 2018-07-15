package com.lz.design.dao;

import com.lz.design.model.Complain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:02
 */
@Repository
public interface ComplainDao {
    /**
     * 根据留言Id删除留言
     *
     * @param complainId
     * @return
     */
    int deleteByComplainId(@Param("complainId") Integer complainId);

    /**
     * 添加留言
     *
     * @param complain
     * @return
     */
    int insertComplain(@Param("complain") Complain complain);

    /**
     * 检测重复
     *
     * @param complain
     * @return
     */
    Complain checkRepeat(@Param("complain") Complain complain);

    /**
     * 根据商品ID查询留言
     *
     * @param goodsId
     * @return
     */
    List<Complain> selectByGoodsId(@Param("goodsId") Integer goodsId);

    /**
     * 根据用户ID查询留言
     *
     * @param userId
     * @return
     */
    List<Complain> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据留言ID查询留言
     *
     * @param complainId
     * @return
     */
    Complain selectByComplainId(@Param("complainId") Integer complainId);


    /**
     * 根据留言ID回复留言
     *
     * @param complain
     * @return
     */
    int updateByComplainId(@Param("complain") Complain complain);
}