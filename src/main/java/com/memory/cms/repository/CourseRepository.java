package com.memory.cms.repository;

import com.memory.cms.entity.Article;
import com.memory.cms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author INS6+
 * @date 2019/5/8 10:53
 */

public interface CourseRepository extends JpaRepository<Course,Integer>, JpaSpecificationExecutor {

    @Modifying
    @Query("update Course a set a.articleOnline  = ?1 where a.id =?2 ")
    int  updateCourseOnlineById(int articleOnline, int id);


}
