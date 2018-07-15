package com.lz.design.dao;

import com.lz.design.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:03
 */
@Repository
public interface OrderDao {
    /**
     * 添加订单
     * @param order
     * @return
     */
    int insertOrder(@Param("order") Order order);

    /**
     * 根据订单号查询订单
     * @param orderId
     * @return
     */
    Order selectByOrderId(@Param("orderId") Long orderId);
    /**
     * 根据用户Id查询订单
     *
     * @param userId
     * @return
     */
    List<Order> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据OrderId更改订单状态
     * @param order
     * @return
     */
    int updateOrderStateByOrderId(@Param("order") Order order);
}