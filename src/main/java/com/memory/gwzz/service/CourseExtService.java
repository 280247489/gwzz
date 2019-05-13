package com.memory.gwzz.service;

import com.memory.gwzz.entity.CourseExt;

import java.util.List;

/**
 * @ClassName CourseExtService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:45
 */
public interface CourseExtService {
    List<CourseExt> courseExtList (String courseId);
    List<Object> setCourseExt (List<CourseExt> list);
    List<Object> getCourseExt (String courseId);
    boolean delCourseExt (String courseId);
}
