package com.memory.gwzz.repository;

import com.memory.entity.jpa.CourseExt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/25 17:47
 */
public interface CourseExtWebRepository extends JpaRepository<CourseExt,String> {

    List<CourseExt> queryCourseExtByCourseId(String courseId);

    List<CourseExt> queryCourseExtByCourseIdOrderByCourseExtSortAsc(String courseId);




}

