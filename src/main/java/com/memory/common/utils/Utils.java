package com.memory.common.utils;

import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
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
    public static String md5Password(String str){
        return  DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * 生成6位随机字符串
     */
    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
    public static String getShortUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
    public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat idTimer = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 获取当前时间戳
     * @return
     */
    public static  Long times (){ return System.currentTimeMillis(); }
    public static String getShortUUTimeStamp(){return getShortUUID()+ System.currentTimeMillis(); }
}
