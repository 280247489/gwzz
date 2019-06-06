package com.memory.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.http.https.HttpsUtil;
import com.memory.redis.config.RedisUtil;
import com.memory.wechat.platform.WechatShareUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.memory.redis.CacheConstantConfig.WECHATSHAREACCESSTOKEN;
import static com.memory.redis.CacheConstantConfig.WECHATSHAREJSAPITICKET;

/**
 * @author INS6+
 * @date 2019/6/4 17:40
 */
@RestController
public class WechatShareController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String WX_APPID = "wxbf553fc8992a2e86";
    public static final String WX_APPSECRET = "47e23b55b9e2e91be90bc23bd5c3aa5e";

    //默认超时时间1个半小时
    private static final long timeout = 90 * 60;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取微信签名
     * @param url
     * @return
     */
    @RequestMapping(value = "wechat/sign")
    public Result getShareSign(@RequestParam("url") String url){
        Result result = new Result();
        try {
            Object redis_access_token = redisUtil.get(WECHATSHAREACCESSTOKEN);

            Object redis_jsapi_ticket = redisUtil.get(WECHATSHAREJSAPITICKET);

            //redis中存在则直接根据规则生成签名
            if(redis_jsapi_ticket != null){
                System.out.println("redis");
                //3.获取sign信息
                Map<String,String> map = WechatShareUtil.createSign(redis_jsapi_ticket.toString(),url);
                result = ResultUtil.success(map);

            }else{
                System.out.println("reload");
             //redis_jsapi_ticket不存在或失效

                //1.获取token
                String tokenUrl = WechatShareUtil.getToken(WX_APPID,WX_APPSECRET);
                String tokenReturn =  HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);
                JSONObject tokenJson = JSONObject.parseObject(tokenReturn);
                System.out.println("tokenReturn = " + tokenReturn);
                String access_token =  tokenJson.getString("access_token");
                redisUtil.set(WECHATSHAREACCESSTOKEN,access_token,timeout);

                //2.获取ticket
                String ticketUrl = WechatShareUtil.getTicket(access_token);
                String ticketReturn = HttpsUtil.httpsRequestToString(ticketUrl,"GET",null);
                JSONObject ticketJson = JSONObject.parseObject(ticketReturn);
                String ticket = ticketJson.getString("ticket");
                redisUtil.set(WECHATSHAREJSAPITICKET,ticket,timeout);

                //3.获取sign信息
                Map<String,String> map = WechatShareUtil.createSign(ticket,url);

                result = ResultUtil.success(map);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }




}
