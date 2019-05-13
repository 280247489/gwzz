package com.memory.gwzz.repository;

import com.memory.gwzz.entity.CourseExt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName CourseExtRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 13:57
 */
public interface CourseExtRepository extends JpaRepository<CourseExt,String> {
    List<CourseExt> findAllByCourseId(String courseId);
}
