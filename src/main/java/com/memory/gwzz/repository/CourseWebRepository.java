package com.memory.gwzz.repository;

import com.memory.entity.jpa.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author INS6+
 * @date 2019/6/3 13:44
 */

public interface CourseWebRepository extends JpaRepository<Course,String> {

}
