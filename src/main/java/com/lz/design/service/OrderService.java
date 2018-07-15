package com.lz.design.service;

import com.lz.design.model.Order;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/20  0:26
 */
public interface OrderService {
    /**
     * 添加订单
     *
     * @param order
     * @return
     */
    int insertOrder(Order order);

    /**
     * 支付订单
     *
     * @param orderId
     * @param userId
     * @return
     */
    int paymentOrder(Long orderId, Long userId);

    /**
     * 查询订单
     *
     * @param orderId
     * @return
     */
    Order selectOrder(Long orderId);

    /**
     * 根据用户Id查询订单
     *
     * @param userId
     * @return
     */
    List<Order> selectOrderByUserId(Long userId);
}
