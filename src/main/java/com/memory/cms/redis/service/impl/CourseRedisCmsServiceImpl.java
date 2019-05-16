package com.memory.cms.redis.service.impl;

import com.memory.cms.redis.service.CourseRedisCmsService;
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
    public  Map<Object, Object>  setCourseCms(String key, Map<String,Object> value) {
        redisUtil.hmset(key,value);

        return   redisUtil.hmget(key);
    }

    @Override
    public Boolean delAndHashSet(String key, Map<String,Object> value) {
        return redisUtil.delAndHashSet(key,value);
    }
}
