package com.memory.cms.redis.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/14 10:56
 */

public interface CourseRedisCmsService {
    //redis 初始化course 的后台阅读数
    void initCourseRedisTotal(String courseId);






}
