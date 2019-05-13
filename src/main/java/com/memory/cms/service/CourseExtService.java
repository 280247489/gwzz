package com.memory.cms.service;

import com.memory.cms.entity.CourseExt;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:32
 */

public interface CourseExtService {

    CourseExt getCourseExtById(String id);

    CourseExt save(CourseExt ext);

    CourseExt update(CourseExt ext);

    void deleteCourseExtByCourseId(String course_id);

    void delete(String id);

    List<CourseExt> queryCourseExtListByCourseId(String course_id);

    List<CourseExt>  saveAll(List<CourseExt> list);

    List<CourseExt> deleteAndSave(List<CourseExt> removeList,List<CourseExt> updateList );

}
