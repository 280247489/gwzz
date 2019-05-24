package com.memory.cms.redis.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/14 10:56
 */

public interface CourseRedisCmsService {


    Boolean delAndHashSet(String courseId,Map<Object,Object> value);

    Map<Object,Object> setHashAndIncr(String keyHash,String keySum,Map<Object,Object> value);




}
