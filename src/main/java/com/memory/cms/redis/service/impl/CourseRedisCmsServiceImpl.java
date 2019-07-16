package com.memory.cms.redis.service.impl;

import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/14 11:00
 */
@Service
public class CourseRedisCmsServiceImpl  implements CourseRedisCmsService {

    @Autowired
    private RedisUtil redisUtil;



    @Override
    public Boolean delAndHashSet(String courseId, Map<Object,Object> value) {

//        String keyHash = CacheConstantConfig.COURSERXT + ":hash:"+courseId;
//        return redisUtil.delAndHashSet(keyHash,value);
        return null;
    }

    @Override
    public Map<Object,Object> setHashAndIncr(String keyHash,String keySum,Map<Object,Object> value) {

       // String keyHash = CacheConstantConfig.COURSERXT + ":hash:" +courseId;
     //   String keySum  = CacheConstantConfig.COURSERXT +":sum:" + courseId;
        return  redisUtil.setHashAndIncr(keyHash,keySum,value);

    }
}
