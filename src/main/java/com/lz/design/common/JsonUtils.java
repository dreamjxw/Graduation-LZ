package com.lz.design.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author Xingwu.Jia
 * @date 2018/5/19 01:40
 */
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapperInstance() {
        return objectMapper;
    }

    /**
     * 使用Jackson序列化字符串,出异常后写日志但不抛异常
     *
     * @return
     */
    public static String writeObjectAsStringSilently(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 输出格式化的json
     */
    public static String toPrettyJsonString(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用Jackson序列化字符串,出异常后写日志&抛异常
     */
    public static String writeObjectAsString(Object object) throws Exception {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new Exception("Jackson转字符串时报错", e);
        }
    }

    /**
     * 将字符串转为Java对象
     */
    public static <T> T readValue(String data, Class<T> c) throws Exception {
        try {
            return objectMapper.readValue(data, c);
        } catch (Exception e) {
            throw new Exception("Jackson转对象时报错", e);
        }
    }

    /**
     * 将字符串转Json对象（微信授权登录使用到）
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static JSONObject doGetJson(String url) throws Exception {
        JSONObject jsonObject = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSON.parseObject(result);

            }
        } catch (Exception e) {
            throw new Exception("Jackson转对象时报错", e);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }
}
