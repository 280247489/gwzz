package com.memory.gwzz.redis.service;

/**
 * @ClassName CourseCommentRedisMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/17 10:46
 */
public interface CourseCommentRedisMobileService {
    void courseCommentLike(String courseId, String courseCommentId, String userId);

    int getCourseCommentLike(String courseId, String courseCommentId);

    int isLike(String courseCommentId, String userId);
}
