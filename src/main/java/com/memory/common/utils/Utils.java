package com.memory.common.utils;

import org.springframework.core.io.UrlResource;
import org.springframework.util.DigestUtils;

import java.net.MalformedURLException;
import java.util.UUID;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 16:48
 * @Description:
 */
public class Utils {

    public static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 生成32位UUID字符串
     */
    public static String generateUUIDs() {
        return UUID.randomUUID().toString().replace("-", "");
    }


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

    public static String getShortUUTimeStamp(){return getShortUUID()+ System.currentTimeMillis();}




    /**
     *md5加密
     */
    public static String md5Password(String str){ return  DigestUtils.md5DigestAsHex(str.getBytes()); }

    public static void main(String[] args) {
        System.out.println(getShortUUTimeStamp());
    }


    /**
     * 检测网络资源是否可访问
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static Boolean isHttpAccess(String url) throws MalformedURLException {
      return   new UrlResource(url).exists();
    }

}


