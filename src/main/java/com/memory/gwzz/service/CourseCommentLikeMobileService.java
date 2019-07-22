package com.memory.gwzz.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CourseCommentLikeMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/5 10:17
 */
public interface CourseCommentLikeMobileService {

    @Transactional
    int like(String courseId, String courseCommentId, String userId);
}
