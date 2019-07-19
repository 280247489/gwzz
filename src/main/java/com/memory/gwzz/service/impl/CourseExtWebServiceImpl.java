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

import static com.memory.redis.CacheConstantConfig.*;

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
    private CourseExtWebRepository repository;



    @Override
    public boolean delCourseExt (String courseId){
        boolean flag = false;
        try {
       /*     String keyHash = SHARECOURSECONTENT + courseId;
            redisUtil.del(keyHash);
            flag = true;*/
        }catch (Exception e){
            throw e;
        }
        return flag;

    }

    @Override
    public List<CourseExt> getCourseExtByDB(String courseId) {

        return repository.queryCourseExtByCourseIdOrderByCourseExtSortAsc(courseId);
    }



    @Override
    public Map<String,Object> getCourseExt (String courseId){
        Map<String,Object> map =new HashMap<String, Object>();
     /*   String keyHash = SHARECOURSECONTENT + courseId;
        System.out.println("keyHash ===========" +keyHash);
        map.put(COURSE,  redisUtil.hget(keyHash,COURSE));
        map.put(COURSEEXT,  redisUtil.hget(keyHash,COURSEEXT));*/
        return map;
    }

    @Override
    public void setCourseExtView(String courseId,String openId) {
  /*      String key = SHARECOURSEVIEW + courseId;
        String keyIncr = SHARECOURSEVIEWOPENID + courseId;
        redisUtil.hincr(keyIncr,openId,1);
        redisUtil.incr(key,1);*/
    }
}
