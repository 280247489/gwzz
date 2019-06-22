package com.memory.gwzz.repository;

import com.memory.entity.jpa.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName CourseMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/22 9:38
 */
public interface CourseMobileRepository extends JpaRepository<Course,String> {

    Course findByIdAndCourseOnline(String cid,Integer onLine);

}
