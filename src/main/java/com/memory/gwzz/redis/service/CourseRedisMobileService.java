package com.memory.gwzz.redis.service;

/**
 * @ClassName CourseRedisMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/4 15:57
 */
public interface CourseRedisMobileService  {
    //课程搜索记录
    void searchCourse(String userId, String searchKey);

    void courseView(String courseId, String userId, Integer os, Integer terminal);

    void courseShare(String courseId, String userId, Integer os);

    void courseLike(String courseId, String userId);

    int getCourseView(String courseId);

    int getCourseShare(String courseId);

    int getCourseLike(String courseId);

    int getUserCourseLike(String courseId, String userId);
}
