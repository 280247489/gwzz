package com.memory.gwzz.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.gwzz.entity.CourseExt;
import com.memory.gwzz.repository.CourseExtRepository;
import com.memory.gwzz.service.CourseExtService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CourseExtServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:45
 */
@Service("courseExtService")
public class CourseExtServiceImpl implements CourseExtService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CourseExtRepository courseExtRepository;

    @Override
    public List<CourseExt> courseExtList (String courseId){
        return courseExtRepository.findAllByCourseId(courseId);

    }



    @Override
    public List<Object> setCourseExt (List<CourseExt> list){
        String courseId = list.get(0).getCourseId();
        for (int i = 0; i<list.size();i++ ){
            redisUtil.lSet(CacheConstantConfig.COURSERXT+":"+courseId,JSON.toJSONString(list.get(i)));
        }
        return redisUtil.lGet(CacheConstantConfig.COURSERXT+":"+courseId,0,-1);

    }

    @Override
    public List<Object> getCourseExt (String courseId){
        return redisUtil.lGet(CacheConstantConfig.COURSERXT+":"+courseId,0,-1);

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
