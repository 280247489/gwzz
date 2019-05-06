package com.memory.common.utils;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 13:58
 * @Description: 密码加密工具类
 */
public class EncryptPwdUtil {

    /**
     * 加密
     */
    public static String encryptPwd(String password){
        String pwd = null;
        try {
            pwd =CDesUtilsHelper.getUtilsForDes("l_l > O_O > Q_Q").encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pwd;
    }

    /**
     * 解密
     */
    public static String decryptPwd(String password) {
        String pwd = null;
        try {
            pwd =CDesUtilsHelper.getUtilsForDes("l_l > O_O > Q_Q").decrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pwd;
    }

    public static void main(String[] args) {
        String pwd = "加密之前";
        System.out.println(pwd);
        String enpwd = encryptPwd(pwd);
        System.out.println(enpwd);
        String depwd = decryptPwd(enpwd);
        System.out.println(depwd);

    }
}
