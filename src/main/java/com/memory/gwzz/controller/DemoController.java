package com.memory.gwzz.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.common.utils.Utils;
import com.memory.common.yml.MyRedisConfig;
import com.memory.gwzz.service.CourseExtMobileService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RedisUtil redisUtils;

    @Autowired
    private CourseRedisCmsService courseRedisCmsService;

    @Autowired
    private CourseExtMobileService courseExtWebService;

    @Autowired
    private MyRedisConfig myRedisConfig;


    int i = 0;

    @RequestMapping("test")
    public Message test() {
        msg = Message.success();
        try {
           // System.out.println(JSON.toJSONString(myRedisConfig));
            i++;
            String courseId = "xrLlwgWI1558084748010";
            courseId ="9nx5w0A71558003844754";
            String keyHash = CacheConstantConfig.COURSERXT + ":hash:" +courseId;
            String keySum =CacheConstantConfig.COURSERXT + ":sum:"+courseId;
           // RedisAPI redisAPI = new RedisAPI(com.memory.redis.RedisUtil.getDialStatsPool());

            //  redisTemplate.opsForValue().increment(keySum, 1);
            //  msg.setData(  redisTemplate.opsForHash().entries(keyHash));
            // redisTemplate.opsForValue().increment(keySum, 1);
            //  Map<Object, Object> returnMap = redisTemplate.opsForHash().entries(keyHash);

            // msg.setData(courseExtWebService.getCourseExt(courseId));
            //  msg.setData(returnMap);
            int y =0;
            for(int x =0;x<100;x++){
                y++;
            }

            msg = Message.success();
            msg.setRecode(0);
            msg.setData("i="+i);
           // msg.setData( redisAPI.hgetAll(keyHash));
            //msg.setData(redisAPI.hget(keyHash,"courseExt"));
            //msg.setData(courseExtWebService.getCourseExt(courseId));

         /*   String courseId = "aaaasdfsdfs";
            String keyHash = CacheConstantConfig.COURSERXT + ":hash:" +courseId;
            String keySum  = CacheConstantConfig.COURSERXT +":sum:" + courseId;
            Map<Object,Object> map = new HashMap<>();
            map.put("aaaaaaa","1111");
            map.put("bbbbbb","22222");
            courseRedisCmsService.setHashAndIncr(keyHash,keySum,map);*/
            //redisUtils.setHashAndIncr(keyHash,keySum,map);
          //  redisUtils.setHashAndIncr(keyHash,"sum",map);

           // redisUtils.incr("courseExt:sum:OYlVHEVv1558081779821",1);
         //   incr
        } catch (Exception e) {
            e.printStackTrace();
        }


        return msg;
    }
    public static void main(String[] args) {
        String msgId ="{\"msg_id\":\"770920259755\"}";
//        Utils.sendSMSCode(1,"15844064331");
//        Utils.sendValidSMSCode("769585791451","730440");
//        Utils.sendValidSMSCode("769584789081","496507");
//        System.out.println();769584332810
        System.out.println(JSON.parse(msgId));

    }

}