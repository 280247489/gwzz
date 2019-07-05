package com.memory.gwzz.redis.service.impl;

import com.memory.gwzz.redis.service.CourseRedisMobileService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.redis.CacheConstantConfig.SEARCHCOURSESEARCHAPPID;


/**
 * @ClassName CourseRedisMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/4 15:57
 */
@Service("courseRedisMobileService")
public class CourseRedisMobileServiceImpl implements CourseRedisMobileService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void searchCourse(String userId, String searchKey){
        String keyIncr = SEARCHCOURSESEARCHAPPID + userId;
        redisUtil.hincr(keyIncr,searchKey,1);
    }
}
