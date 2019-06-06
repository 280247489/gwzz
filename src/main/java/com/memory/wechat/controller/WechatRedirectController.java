package com.memory.wechat.controller;
import com.alibaba.fastjson.*;
import com.memory.common.utils.*;
import com.memory.http.https.HttpsUtil;
import com.memory.wechat.platform.WechatUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/6/4 14:00
 */
@RestController
public class WechatRedirectController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String WX_APPID = "wxbf553fc8992a2e86";
    private static final String WX_APPSECRET = "47e23b55b9e2e91be90bc23bd5c3aa5e";

    /**
     * 获取code地址
     * @param state
     * @param redirect_url
     * @return
     */
    @RequestMapping(value = "wechat/getCode")
    public void wechatGetCode(@RequestParam(name = "state") String state,@RequestParam(name = "redirect_url") String redirect_url){
        //默认获取所有用户信息
           String scop = "snsapi_userinfo";
        String getCodeUrl = WechatUserInfoUtil.getCode(WX_APPID, redirect_url, scop,state);
        HttpsUtil.httpsRequestToString(getCodeUrl, "GET", null);
    }


        /**
         * 访问授权，返回用户信息
         * 微信网页授权流程:
         * 1. 用户同意授权,获取 code
         * 2. 通过 code 换取网页授权 access_token
         * 3. 使用获取到的 access_token 和 openid 拉取用户信息
         *
         * @param code  用户同意授权后,获取到的code
         * @param state 重定向状态参数
         * @return
         */
        @RequestMapping(value = "wechat/auth")
        public Result wechatLogin(@RequestParam(name = "code", required = false) String code,
                                  @RequestParam(name = "state") String state) {
            Result result = new Result();

            Map<String,Object> returnMap = new HashMap<String, Object>();
            // 1. 用户同意授权,获取code
            logger.info("收到微信重定向跳转.");
            logger.info("用户同意授权,获取code:{} , state:{}", code, state);

            String openId = "";


            // 2. 通过code换取网页授权access_token
            if (code != null || !(code.equals(""))) {

                String APPID = WX_APPID;
                String SECRET = WX_APPSECRET;
                String CODE = code;
                String WebAccessToken = "";

              // String getCodeUrl = WechatUserInfoUtil.getCode(APPID, REDIRECT_URI, SCOPE,state);
               // logger.info("第一步:用户授权, get Code URL:{}", getCodeUrl);

                // 替换字符串，获得请求access token URL
                String tokenUrl = WechatUserInfoUtil.getWebAccess(APPID, SECRET, CODE);
                logger.info("第二步:get Access Token URL:{}", tokenUrl);

                // 通过https方式请求获得web_access_token
                String responseStr = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);

                JSONObject jsonObject = JSON.parseObject(responseStr);
                logger.info("请求到的Access Token:{}", jsonObject.toJSONString());

                if (null != jsonObject) {
                    try {

                        WebAccessToken = jsonObject.getString("access_token");
                        openId = jsonObject.getString("openid");
                        logger.info("获取access_token成功!");
                        logger.info("WebAccessToken:{} , openId:{}", WebAccessToken, openId);

                        // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                        String userMessageUrl = WechatUserInfoUtil.getUserMessage(WebAccessToken, openId);
                        logger.info("第三步:获取用户信息的URL:{}", userMessageUrl);

                        // 通过https方式请求获得用户信息响应
                        String userMessageResponse = HttpsUtil.httpsRequestToString(userMessageUrl, "GET", null);

                        JSONObject userMessageJsonObject = JSON.parseObject(userMessageResponse);

                        logger.info("用户信息:{}", userMessageJsonObject.toJSONString());

                        if (userMessageJsonObject != null) {
                            try {

                                String country = userMessageJsonObject.getString("country");
                                String unionid = userMessageJsonObject.getString("unionid");
                                String province =  userMessageJsonObject.getString("province");
                                String city =  userMessageJsonObject.getString("city");
                                String sex = userMessageJsonObject.getString("sex");
                                String nickname = userMessageJsonObject.getString("nickname");
                                String headimgurl=userMessageJsonObject.getString("headimgurl");
                                String language = userMessageJsonObject.getString("language");
                                sex = (sex.equals("1")) ? "男" : "女";

                                returnMap.put("country",country);
                                returnMap.put("unionid",unionid);
                                returnMap.put("province",province);
                                returnMap.put("city",city);
                                returnMap.put("sex",sex);
                                returnMap.put("nickname",nickname);
                                returnMap.put("headimgurl",headimgurl);
                                returnMap.put("language",language);
                                returnMap.put("state",state);
                                returnMap.put("openId",openId);

                                result = ResultUtil.success(returnMap);
                            } catch (JSONException e) {
                                logger.error("获取用户信息失败");
                            }
                        }
                    } catch (JSONException e) {
                        logger.error("获取Web Access Token失败");
                    }
                }
            }

            return result;
        }
}
