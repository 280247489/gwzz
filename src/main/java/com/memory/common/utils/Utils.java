package com.memory.common.utils;

import org.springframework.core.io.UrlResource;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;
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




    /**
     * 判断是否为基本类型：包括String/Integer/Double/Boolean
     * @param clazz clazz
     * @return  true：是;     false：不是
     */
    public static boolean isPrimite(Class<?> clazz){
        if (clazz.isPrimitive() || clazz == String.class || clazz == Integer.class || clazz == Double.class || clazz == Boolean.class){
            return true;
        }else {
            return false;
        }
    }

    public static Boolean isNull(String str){
        return  str!=null && str.length()>0;
    }

    public static Boolean isNull(Integer str){
        return  str!=null ;
    }

    public static Boolean isNull(Boolean str){
        return  str!=null ;
    }

    public static Boolean isNull(MultipartFile file){
        return file!=null && !file.isEmpty();
    }

    /**
     * 转表情符
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!notisEmojiCharacter(codePoint)) {
                //判断确认有表情字符
                return true;
            }
        }
        return false;
    }

    /**
     * 非emoji表情字符判断
     * @param codePoint
     * @return
     */
    private static boolean notisEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source  需要过滤的字符串
     * @return
     */
    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }

        StringBuilder buf = null;//该buf保存非emoji的字符
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (notisEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }

        if (buf == null) {
            return "";//如果没有找到非emoji的字符，则返回无内容的字符串
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    public static Boolean isNotNull(String str){
        return  str!=null && str.length()>0;
    }

    public static Boolean isNotNull(Integer str){
        return  str!=null ;
    }

    public static Boolean isNotNull(Boolean str){
        return  str!=null ;
    }

    public static Boolean isNotNull(MultipartFile file){
        return file!=null && !file.isEmpty();
    }

    public static Boolean isNotNull(Object file){
        return file!=null;
    }

    public static Boolean isNotNull(List<?> list){ return list!=null && list.size()>0; }

    public static Integer getIntVal(Object obj){
        return (Utils.isNotNull(obj)&&!("null").equals(obj))?(Integer.valueOf(obj.toString())):0;
    }


}


