package com.memory.cms.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.memory.redis.CacheConstantConfig.COURSEEXTHASH;
import static com.memory.redis.CacheConstantConfig.SHARECOURSECONTENT;

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

        String keyHash =SHARECOURSECONTENT +courseId;
        return redisUtil.delAndHashSet(keyHash,value);
    }

    @Override
    public Map<Object,Object> setHashAndIncr(String courseId,Map<Object,Object> value) {

        String keyHash =SHARECOURSECONTENT +courseId;
        redisUtil.hset(keyHash,"courseExt",JSON.toJSONString(value));
        redisUtil.hset(keyHash,"course","爱中医第328课：肺癌的拯救");
      //  redisUtil.set(keyHash,JSON.toJSONString(value));
        return value;

    }
}
