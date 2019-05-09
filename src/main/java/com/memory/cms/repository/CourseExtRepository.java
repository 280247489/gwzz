package com.memory.cms.repository;

import com.memory.cms.entity.CourseExt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:31
 */

public interface CourseExtRepository extends JpaRepository<CourseExt,Integer> {

    List<CourseExt> queryCourseExtByArticleId(Integer articleId);

    void deleteCourseExtByArticleId(Integer articleId);

}
