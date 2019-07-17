package com.memory.cms.redis.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/14 10:56
 */

public interface CourseRedisCmsService {
    //redis course manager 的后台阅读数
    void initCourseRedisViewTotal(String courseId);

    //设置 course manager 阅读量
    void setCourseRedisViewTotal(String courseId,Integer cumulativeValue);

    //获取所有阅读量
    Integer getCourseAndLiveRedisAllViewTotal(String courseId);

    //获取真实阅读量
    Integer getCourseAndLiveRedisRealViewTotal(String courseId);

    //获取课程伪阅读量
    Integer getCourseManagerViewTotal(String courseId);

    //获取课程所有阅读量
    Integer getCourseRedisAllViewTotal(String courseId);

    //获取课程实际阅读量
    Integer getCourseRedisRealViewTotal(String courseId);


    //获取分享量
    Integer getCourseRedisShareTotal(String courseId);

    //获取点赞量
    Integer getCourseRedisLikeTotal(String courseId);





}
