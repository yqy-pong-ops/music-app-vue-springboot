package com.example.musicserver.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    /**
     * md5算法加密字符串
     * @param str
     * @return 成功返回结果，失败返回null
     */
    public static String md5(String str) {
        if (str == null || str.isEmpty())
            return null;

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes("UTF-8"));
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1)
                    temp = "0" + temp;
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("md5 exception: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("md5 exception: " + e.getMessage());
        }
        return null;
    }
}
