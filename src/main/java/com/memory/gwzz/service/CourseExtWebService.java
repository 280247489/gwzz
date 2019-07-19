package com.memory.gwzz.service;

import com.memory.entity.jpa.CourseExt;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseExtWebService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:45
 */
public interface CourseExtWebService {

    Map<String,Object> getCourseExt (String courseId);
    boolean delCourseExt (String courseId);

    List<CourseExt> getCourseExtByDB(String courseId);

    void setCourseExtView(String courseId,String openId);
}
