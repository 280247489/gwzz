package com.memory.cms.redis.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/14 10:56
 */

public interface CourseRedisCmsService {

    Map<Object, Object> setCourseCms(String key,Map<String,Object> value);

    Boolean delAndHashSet(String key,Map<String,Object> value);




}
