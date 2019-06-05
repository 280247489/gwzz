package com.memory.gwzz.service;

import com.memory.entity.jpa.CourseLike;

/**
 * @ClassName CourseLikeMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 17:16
 */
public interface CourseLikeMobileService {
    CourseLike like(String cid,String uid);
}
