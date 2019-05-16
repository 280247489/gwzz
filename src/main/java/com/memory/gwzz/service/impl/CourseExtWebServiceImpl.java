package com.memory.gwzz.service.impl;

import com.memory.gwzz.service.CourseExtWebService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CourseExtWebServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:45
 */
@Service("courseExtService")
public class CourseExtWebServiceImpl implements CourseExtWebService {

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Object getCourseExt (String courseId){
        return redisUtil.hmget(CacheConstantConfig.COURSERXT+":"+courseId);

    }
    @Override
    public boolean delCourseExt (String courseId){
        boolean flag = false;
        try {
            redisUtil.del(CacheConstantConfig.COURSERXT+":"+courseId);
            flag = true;
        }catch (Exception e){
            throw e;
        }
        return flag;

    }
}