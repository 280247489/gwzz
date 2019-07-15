package com.memory.cms.redis.service.impl;

import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.redis.CacheConstantConfig.COURSEVIEWMANAGER;

/**
 * @author INS6+
 * @date 2019/5/14 11:00
 */
@Service
public class CourseRedisCmsServiceImpl  implements CourseRedisCmsService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void initCourseRedisTotal(String courseId) {
        //后台添加阅读量
        String key = COURSEVIEWMANAGER + courseId;
        //默认设置成0
        redisUtil.set(key,0);

    }
}
