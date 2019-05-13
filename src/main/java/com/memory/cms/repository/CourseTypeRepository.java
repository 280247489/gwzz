package com.memory.cms.repository;

import com.memory.cms.entity.ArticleType;
import com.memory.cms.entity.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author INS6+
 * @date 2019/5/9 16:18
 */

public interface CourseTypeRepository extends JpaRepository<CourseType,Integer> {
}
