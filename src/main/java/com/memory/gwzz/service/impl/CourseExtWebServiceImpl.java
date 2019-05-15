package com.memory.gwzz.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.entity.jpa.CourseExt;
import com.memory.gwzz.repository.CourseExtWebRepository;
import com.memory.gwzz.service.CourseExtWebService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CourseExtWebRepository courseExtWebRepository;

//    @Override
//    public Map<String,Object> setCourseExt (Map<String,Object> map){
//
//        for (int i = 0; i<list.size();i++ ){
//            redisUtil.lSet(CacheConstantConfig.COURSERXT+":"+courseId,JSON.toJSONString(list.get(i)));
//        }
//        return redisUtil.lGet(CacheConstantConfig.COURSERXT+":"+courseId,0,-1);
//
//    }
    @Override
    public Map<String,Object> getCourseExtMap(String courseId){
        Map<String,Object> map = new HashMap<>();
        List<CourseExt> list = courseExtWebRepository.findAllByCourseId(courseId);
        map.put("course","测试第一期");
        map.put("courseExt", JSON.toJSONString(list));
        return  map;
    }

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
