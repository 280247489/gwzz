package com.memory.gwzz.service;

import com.memory.gwzz.model.Course;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/22 9:16
 */
public interface CourseMobileService {
    List<Course> getCourseById(String albumId);

}
