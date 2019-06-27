package com.memory.cms.repository;

import com.memory.entity.jpa.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/8 10:53
 */

public interface CourseCmsRepository extends JpaRepository<Course,String>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query("update Course a set a.courseOnline  = ?1 where a.id =?2 ")
    int  updateCourseOnlineById(int courseOnline, String id);

    @Modifying
    @Transactional
    @Query("update Course c set c.courseLiveStatus = ?1 where c.id =?2")
    int updateCourseLiveStatus(Integer courseLiveStatus,String id);


    @Query(value = "select  new com.memory.entity.bean.Course(c.id,c.courseTitle,c.courseOnline ) " +
            "from Course c  where  c.courseOnline = 1 ORDER BY  c.courseCreateTime desc")
    List<com.memory.entity.bean.Course> queryCourseOptions();


    @Modifying
    @Transactional
    @Query("update Course a set a.courseUpdateTime  = ?1 where a.id =?2 ")
    int  updateCourseUpdateTimeById(Date update_time, String id);


    @Query(" from Course where courseOnline = 1 ")
    List<Course> queryAllOnlineCourse();

    Course queryCourseByCourseTitle(String courseTitle);

    Course queryCourseByCourseNumber(Integer courseNumber);

    @Query(value="from Course a where a.courseTitle =?1 AND a.id <> ?2")
    Course queryCourseByCourseTitle(String courseTitle,String id);

    @Query(value="from Course a where a.courseNumber =?1 AND a.id <> ?2")
    Course queryCourseByCourseNumber(Integer courseNumber,String id);



}
