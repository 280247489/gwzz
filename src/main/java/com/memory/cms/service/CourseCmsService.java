package com.memory.cms.service;

import com.memory.entity.jpa.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/8 10:56
 */

public interface CourseCmsService {

    List<Course> getCourseList();

    Course getCourseById(String id);

    Course add(Course course);

    Course update(Course course);

    void delete(String id);

    int updateCourseOnlineById(int online, String id);

    Page<Painter> queryCourseByQue(Pageable pageable, String course_title, String course_update_id, Integer course_online, String sort_status, String type_id,String album_id);

    int updateCourseLiveStatus(int course_live_status,String id);

    List<com.memory.entity.bean.Course> queryCourseOptions();

    int  updateCourseUpdateTimeById(Date update_time, String id);

    List<Course> queryAllOnlineCourse();

    List<com.memory.entity.bean.Course> queryCourseByQueHql(int pageIndex,int limit, String course_title, String course_update_id, Integer course_online, String sort_status, String type_id,String album_id);

    int queryCourseCountByQueHql(String course_title, String course_update_id, Integer course_online, String sort_status, String type_id,String album_id);

    Course queryCourseByCourseTitle(String courseTitle);

    Course queryCourseByCourseNumber(Integer courseNumber);

    Course queryCourseByCourseTitle(String courseTitle,String id);

    Course queryCourseByCourseNumber(Integer courseNumber,String id);


}
