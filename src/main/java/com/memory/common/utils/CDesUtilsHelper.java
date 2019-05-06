package com.memory.common.utils;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 13:59
 * @Description: DES加密解密工具类
 */
public class CDesUtilsHelper {

    private static CDesUtils desUtils = null;

    /**
     * 获取与服务器通信的加密处理类的实例，多次调用该方法返回同一个实例
     * @param key 关键字
     * @return CDesUtils 类的一个实例
     */
    public static CDesUtils getUtilsForCommunication(String key) {
        if (null != key && key.equals("@_@ > *_* > -_-")) {
            if (null == desUtils) {
                try {
                    desUtils = new CDesUtils("s@4#$%*K");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return desUtils;
        }
        return null;
    }

    /**
     * 获取与服务器通信的加密处理类的实例，多次调用该方法返回同一个实例
     * @param key 关键字
     * @return CDesUtils 类的一个实例
     */
    public static CDesUtils getUtilsForPassword(String key) {
        if (null != key && key.equals("@_@ < *_* < -_-")) {
            if (null == desUtils) {
                try {
                    desUtils = new CDesUtils("2&^6_+#@@$#@#$");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return desUtils;
        }
        return null;
    }

    public static CDesUtils getUtilsForDes(String key) {
        CDesUtils cDesUtils = null;
        try {
            cDesUtils =  new CDesUtils(key);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cDesUtils;
    }
}
