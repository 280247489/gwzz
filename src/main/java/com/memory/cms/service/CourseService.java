package com.memory.cms.service;

import com.memory.cms.entity.Article;
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

    Course getCourseById(int id);

    Course add(Course course);

    Course update(Course course);

    void delete(int id);

    int updateCourseOnlineById(int online, int id);

    Page<Painter> queryCourseByQue(Pageable pageable, String article_title, String article_update_id, Integer article_online, String sort_status, String type_id);


}
