package com.memory.gwzz.controller;

import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("test")
    public Message test() {
        msg = Message.success();
        try {
            String courseId = "aaaasdfsdfs";
            String keyHash = CacheConstantConfig.COURSERXT + ":hash:" +courseId;
            String keySum  = CacheConstantConfig.COURSERXT +":sum:" + courseId;
            Map<Object,Object> map = new HashMap<>();
            map.put("aaaaaaa","1111");
            map.put("bbbbbb","22222");
            courseRedisCmsService.setHashAndIncr(keyHash,keySum,map);
            //redisUtils.setHashAndIncr(keyHash,keySum,map);
          //  redisUtils.setHashAndIncr(keyHash,"sum",map);

           // redisUtils.incr("courseExt:sum:OYlVHEVv1558081779821",1);
         //   incr
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.setMsg("访问成功");
        return msg;
    }
}