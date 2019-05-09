package com.memory.cms.service;

import com.memory.cms.entity.CourseExt;
import com.memory.cms.entity.CourseExt;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:32
 */

public interface CourseExtService {

    CourseExt getCourseExtById(Integer id);

    CourseExt save(CourseExt ext);

    CourseExt update(CourseExt ext);

    void deleteCourseExtByArticleId(Integer article_id);

    void delete(Integer id);

    List<CourseExt> queryCourseExtListByArticleId(Integer article_id);

}
