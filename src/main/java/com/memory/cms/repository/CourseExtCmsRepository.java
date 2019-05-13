package com.memory.cms.repository;

import com.memory.entity.jpa.CourseExt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:31
 */

public interface CourseExtCmsRepository extends JpaRepository<CourseExt,String> {

    List<CourseExt> queryCourseExtByCourseId(String courseId);

    void deleteCourseExtByCourseId(String courseId);


}
