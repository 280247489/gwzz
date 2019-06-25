package com.memory.gwzz.service;

import com.memory.entity.jpa.CourseLike;

import java.util.Map;

/**
 * @ClassName CourseLikeMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 17:16
 */
public interface CourseLikeMobileService {
    CourseLike like(String cid,String uid);
    Map<String,Object> ListCourseLikeByUserId(String userId, Integer start, Integer limit);
}
