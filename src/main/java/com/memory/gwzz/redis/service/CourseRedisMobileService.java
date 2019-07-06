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
}
