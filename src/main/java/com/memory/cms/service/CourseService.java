package com.memory.cms.service;

import com.memory.cms.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.*;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/8 10:56
 */

public interface CourseService {

    List<Course> getCourseList();

    Course getCourseById(String id);

    Course add(Course course);

    Course update(Course course);

    void delete(String id);

    int updateCourseOnlineById(int online, String id);

    Page<Painter> queryCourseByQue(Pageable pageable, String course_title, String course_update_id, Integer course_online, String sort_status, String type_id);


}
