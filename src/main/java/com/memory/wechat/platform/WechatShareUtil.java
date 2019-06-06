package com.memory.wechat.platform;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 微信(公众号)分享获取签名
 * @author INS6+
 * @date 2019/6/4 17:15
 */

public class WechatShareUtil {

    public static final String WX_APPID = "wxbf553fc8992a2e86";
    public static final String WX_APPSECRET = "47e23b55b9e2e91be90bc23bd5c3aa5e";

    private Logger logger = LoggerFactory.getLogger(getClass());

    //1.获取微信token

    public static String Get_Token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&appid&secret=%s&secret";
    //替换字符串
    public static String getToken(String APPID, String SECRET){
        return String.format(Get_Token,APPID,SECRET);
    }

    //2.获取ticket
    public static String Get_Ticket="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&token&type=jsapi";
    //替换字符串
    public static String getTicket(String TOKEN){
        return String.format(Get_Ticket,TOKEN);
    }

    //3.生成签名
    public static Map<String,String> createSign(String jsapi_ticket, String url){
        return Sign.sign(jsapi_ticket,url,WX_APPID);
    }


    public static void main(String[] args) {
        System.out.println(getToken(WX_APPID,WX_APPSECRET));
        System.out.println(getTicket("22_lLuACp1KiKMY7FpCKg12i7IPc1WCh4GCRlWUIBLSr0UqcISPQ4wN81-zoLHmW-ve_T200IXxuErEsaq03Sp8TCT-ngTkStiEQCP7vPqJExirUxt7OycS4LjpArxT0o6ff5I6qPze3TJwIbysUNKiAGABUR"));
        String ticket="LIKLckvwlJT9cWIhEQTwfLtOAQ7GWMt1neglkDZwM5xbhrNKu3P9pbZpEy-r_WyMju11Ml9jtH15VcB6dNSLDw";
        String url="http://www.baidu.com";
        System.out.println(JSON.toJSONString(createSign(ticket,url)));


    }







}
