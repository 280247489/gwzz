package com.memory.gwzz.repository;

import com.memory.entity.jpa.CourseExt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName CourseExtWebRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/14 10:39
 */
public interface CourseExtWebRepository extends JpaRepository<CourseExt,String> {
    List<CourseExt> findAllByCourseId(String courseId);
}
