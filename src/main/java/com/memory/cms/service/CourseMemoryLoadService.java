package com.memory.cms.service;

import com.memory.entity.jpa.CourseMemoryLoad;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/28 14:54
 */

public interface CourseMemoryLoadService {

    CourseMemoryLoad getCourseMemoryLoadById(String courseId);

    CourseMemoryLoad updateCourseMemoryLoadById(CourseMemoryLoad courseMemoryLoad);

    CourseMemoryLoad addCourseMemoryLoad(CourseMemoryLoad courseMemoryLoad);

    List<CourseMemoryLoad> queryAllCourseMemoryLoadByLoadStatus(int load_status);

}
