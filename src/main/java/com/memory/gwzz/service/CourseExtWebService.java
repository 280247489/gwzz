package com.memory.gwzz.service;

import java.util.Map;

/**
 * @ClassName CourseExtWebService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:45
 */
public interface CourseExtWebService {
//    List<Object> setCourseExt (List<CourseExt> list);
    Map<String,Object> getCourseExtMap(String courseId);
    Object getCourseExt (String courseId);
    boolean delCourseExt (String courseId);
}
