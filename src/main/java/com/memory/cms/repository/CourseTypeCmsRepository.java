package com.memory.cms.repository;

import com.memory.entity.jpa.CourseExt;
import com.memory.entity.jpa.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:18
 */

public interface CourseTypeCmsRepository extends JpaRepository<CourseType,String>, JpaSpecificationExecutor<CourseType> {

    @Query(value = "select  c.id,c.type_name,c.type_create_time,c.img,c.sum,c.is_use " +
            "from course_type c  where  c.is_use =?1 ORDER BY  c.type_create_time desc", nativeQuery = true)
    List<CourseType> queryCourseTypeList(Integer isUse);


    @Query(value = "select  c.id,c.type_name,c.type_create_time,c.img,c.sum,c.is_use " +
            "from course_type c   ORDER BY  c.type_create_time desc", nativeQuery = true)
    List<CourseType> queryAllCourseTypeListByTypeCreateTimeDesc();





}
