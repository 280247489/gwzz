package com.memory.gwzz.service;

import com.memory.entity.jpa.CourseCommentLike;

/**
 * @ClassName CourseCommentLikeMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/5 10:17
 */
public interface CourseCommentLikeMobileService {
    CourseCommentLike like(String cid,String uid);
}
