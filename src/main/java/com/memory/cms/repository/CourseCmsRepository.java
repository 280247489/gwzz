package com.memory.cms.repository;

import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseMemoryLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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


}
