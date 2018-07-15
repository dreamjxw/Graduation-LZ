package com.lz.design.service.impl;

import com.google.gson.Gson;
import com.lz.design.dao.OrderDao;
import com.lz.design.dao.ShopCartDao;
import com.lz.design.dao.UserDao;
import com.lz.design.model.Order;
import com.lz.design.model.OrderGoods;
import com.lz.design.model.User;
import com.lz.design.service.OrderService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xingwu.Jia [xingwuj@tujia.com]
 * @date 2018/1/25 20:18
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final int overTime = 30;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ShopCartDao shopCartDao;
    @Autowired
    private UserDao userDao;

    @Override
    public int insertOrder(Order order) {
        logger.info("【订单系统】请求添加订单,请求参数:{}", new Gson().toJson(order));
        int i = orderDao.insertOrder(order);
        if (i > 0) {
            List<OrderGoods> orderGoods = order.getOrderGoods();
            int[] data = new int[orderGoods.size()];
            for (int j = 0; j < orderGoods.size(); j++) {
                data[j] = orderGoods.get(j).getGoodsId();
            }
            shopCartDao.deleteShopCartByGoodsIdBatch(order.getUserId(), data);
            logger.info("【订单系统】添加订单成功");
            return 1;
        }
        logger.info("【订单系统】添加订单失败");
        return 0;
    }

    @Override
    public int paymentOrder(Long orderId, Long userId) {
        logger.info("【订单系统】请求支付订单,请求订单号:{}", new Gson().toJson(orderId));
        Order order = orderDao.selectByOrderId(orderId);
        User user = userDao.selectByUserId(userId);
        if (order.getPayStatusId() == 1) {
            logger.info("【订单系统】该订单已被支付");
            return -2;
        }
        if (order != null && user != null) {
            if (!order.getOrderDateStart().after(DateTime.now().minusMinutes(overTime).toDate())) {
                logger.info("【订单系统】订单支付超时");
                order.setOrderDateEnd(DateTime.now().toDate());
                order.setPayStatusId((byte) 2);
                return orderDao.updateOrderStateByOrderId(order);
            } else {
                logger.info("【订单系统】订单正常支付");
                if (order.getOrderTotalprice() <= user.getUserAccount()) {
                    order.setOrderDateEnd(DateTime.now().toDate());
                    order.setPayStatusId((byte) 1);
                    logger.info("【订单系统】账户余额" + user.getUserAccount() + "元，本次扣除" + order.getOrderTotalprice() + "元");
                    User user1 = new User();
                    user1.setUserId(userId);
                    user1.setUserAccount(user.getUserAccount() - order.getOrderTotalprice());
                    userDao.updateUserInfo(user1);
                    return orderDao.updateOrderStateByOrderId(order);
                }
                logger.info("【订单系统】账户余额不足");
                return 0;
            }
        }
        logger.info("【订单系统】查询不到该订单信息");
        return -1;
    }

    @Override
    public Order selectOrder(Long orderId) {
        logger.info("【订单系统】请求查询订单,请求订单号:{}", new Gson().toJson(orderId));
        return orderDao.selectByOrderId(orderId);
    }

    @Override
    public List<Order> selectOrderByUserId(Long userId) {
        return orderDao.selectByUserId(userId);
    }
}
