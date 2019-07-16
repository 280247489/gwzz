package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/1
 * @Description:
 */
@RestController
public class DemoController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("test")
    public Message test() {
        msg = Message.success();
        return msg;
    }
    public static   void main(String[] args) {
//        String msgId ="{\"msg_id\":\"770920259755\"}";
//        Utils.sendSMSCode(1,"15844064331");
//        Utils.sendValidSMSCode("769585791451","730440");
//        Utils.sendValidSMSCode("769584789081","496507");
//        System.out.println();769584332810
//      RedisUtil redisUtil = new RedisUtil();
//        redisUtil.incr(CacheConstantConfig.USER_SMS_SUM+":"+,RedisUtil.CACHE_TIME_D_1,1);

    }

}