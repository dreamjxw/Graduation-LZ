package com.lz.design.common.base64;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author Xingwu.Jia
 * @date 2018/6/1  18:38
 */
public class ImgBase64 {
    //base64字符串转化成图片
    public boolean GenerateImage(String imgStr, String userName) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "E:/IDEA Project/Graduation-LZ/src/main/webapp/shop/head/" + userName + ".png";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            String imgFilePath1 = "E:/IDEA Project/Graduation-LZ/src/main/webapp/head/" + userName + ".png";
            OutputStream out1 = new FileOutputStream(imgFilePath1);
            out1.write(b);
            out1.flush();
            out1.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
