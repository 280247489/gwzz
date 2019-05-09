package com.memory.common.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 16:48
 * @Description:
 */
public class Utils {
    /**
     * 生成32位UUID字符串
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     *md5加密
     */
    public static String md5Password(String str){ return  DigestUtils.md5DigestAsHex(str.getBytes()); }
}
