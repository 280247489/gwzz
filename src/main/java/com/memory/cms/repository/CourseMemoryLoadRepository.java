package com.memory.cms.repository;

import com.memory.entity.jpa.CourseMemoryLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/28 14:53
 */

public interface CourseMemoryLoadRepository extends JpaRepository<CourseMemoryLoad,String> {



    @Query(value = "select  c.course_id,c.content,c.create_time,c.create_time,c.operator,c.load_status,c.update_time,c.course_redis_key " +
            "from course_memory_load c  where  c.load_status =?1 ORDER BY  c.create_time desc", nativeQuery = true)
    List<CourseMemoryLoad> queryCourseMemoryLoadList(Integer load_status);



}
