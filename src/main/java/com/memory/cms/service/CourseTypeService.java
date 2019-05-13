package com.memory.cms.service;

import com.memory.cms.entity.ArticleType;
import com.memory.cms.entity.CourseType;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:19
 */

public interface CourseTypeService {

    List<CourseType>  queryCourseTypeList();

    CourseType add(CourseType courseType);

    void delete(Integer id);


}
