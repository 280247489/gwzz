package com.memory.wechat.platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

/**
 * 微信(公众号)授权登录获取用户信息
 * @author INS6+
 * @date 2019/6/4 13:51
 */

public class WechatUserInfoUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());

    // 1.获取code的请求地址
    public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    // 替换字符串
    public static String getCode(String APPID, String REDIRECT_URI,String SCOPE,String state) {
        return String.format(Get_Code,APPID, URLEncoder.encode(REDIRECT_URI),SCOPE,state);
    }

    // 2.获取Web_access_token https的请求地址
    public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    // 替换字符串
    public static String getWebAccess(String APPID, String SECRET,String CODE) {
        return String.format(Web_access_tokenhttps, APPID, SECRET,CODE);
    }

    // 3.拉取用户信息的请求地址
    public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    // 替换字符串
    public static String getUserMessage(String access_token, String openid) {
        return String.format(User_Message, access_token,openid);
    }


    public static void main(String[] args) {
        String REDIRECT_URI = "http://tmanager.houaihome.com/wechat/auth";
        String SCOPE = "snsapi_userinfo"; // snsapi_userinfo // snsapi_login

        //appId
        String appId = "wxbf553fc8992a2e86";

        String getCodeUrl = getCode(appId, REDIRECT_URI, SCOPE,1+"");
        System.out.println("getCodeUrl:"+getCodeUrl);
    }


}
