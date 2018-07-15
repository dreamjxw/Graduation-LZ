package com.lz.design.dao.handler;

import com.alibaba.fastjson.JSON;
import com.lz.design.common.JsonUtils;
import com.lz.design.model.GoodsGift;
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
public class GoodsHandler implements TypeHandler<List<GoodsGift>> {
    public static final String ORDER_DETAILS_COLUMN_NAME = "goods_gift";

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<GoodsGift> goodsGift, JdbcType
            jdbcType) throws SQLException {
        if (goodsGift != null) {
            preparedStatement.setString(i, JsonUtils.toJsonString(goodsGift));
        } else {
            preparedStatement.setString(i, null);
        }
    }

    @Override
    public List<GoodsGift> getResult(ResultSet resultSet, String s) throws SQLException {
        String goodsGift = resultSet.getString(ORDER_DETAILS_COLUMN_NAME);
        if (goodsGift != null) {
            try {
                return JSON.parseArray(goodsGift, GoodsGift.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<GoodsGift> getResult(ResultSet resultSet, int i) throws SQLException {
        String goodsGift = resultSet.getString(i);
        if (goodsGift != null) {
            try {

                return JSON.parseArray(goodsGift, GoodsGift.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<GoodsGift> getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
