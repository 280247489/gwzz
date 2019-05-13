package com.memory.cms.service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:19
 */

public interface CourseTypeCmsService {

    List<CourseType>  queryCourseTypeList();

    CourseType add(CourseType courseType);

    void delete(Integer id);


}
