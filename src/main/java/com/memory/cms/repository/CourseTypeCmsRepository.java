package com.memory.cms.repository;

import com.memory.entity.jpa.CourseTypes;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author INS6+
 * @date 2019/5/9 16:18
 */

public interface CourseTypeCmsRepository extends JpaRepository<CourseTypes,String> {
}
