package com.lz.design.dao.handler;

import com.alibaba.fastjson.JSON;

import com.lz.design.common.JsonUtils;
import com.lz.design.model.OrderGoods;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * @author Xingwu.Jia [xingwuj@tujia.com]
 * @date 2018/1/29 13:14
 */
public class OrderGoodsHandler implements TypeHandler<List<OrderGoods>> {
    public static final String ORDER_DETAILS_COLUMN_NAME = "order_details";

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<OrderGoods> orderGoods, JdbcType
            jdbcType) throws SQLException {
        if (orderGoods != null) {
            preparedStatement.setString(i, JsonUtils.toJsonString(orderGoods));
        } else {
            preparedStatement.setString(i, null);
        }
    }

    @Override
    public List<OrderGoods> getResult(ResultSet resultSet, String s) throws SQLException {
        String orderDetails = resultSet.getString(ORDER_DETAILS_COLUMN_NAME);
        if (orderDetails != null) {
            try {
                return JSON.parseArray(orderDetails, OrderGoods.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<OrderGoods> getResult(ResultSet resultSet, int i) throws SQLException {
        String orderDetails = resultSet.getString(i);
        if (orderDetails != null) {
            try {

                return JSON.parseArray(orderDetails, OrderGoods.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<OrderGoods> getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
