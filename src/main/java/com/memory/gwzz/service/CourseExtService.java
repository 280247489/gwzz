package com.memory.gwzz.service;

import com.memory.entity.jpa.CourseExt;

import java.util.List;

/**
 * @ClassName CourseExtCmsService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:45
 */
public interface CourseExtService {
    List<Object> setCourseExt (List<CourseExt> list);
    List<Object> getCourseExt (String courseId);
    boolean delCourseExt (String courseId);
}
