package com.memory.cms.service;

import com.memory.entity.jpa.CourseExt;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:32
 */

public interface CourseExtCmsService {

    CourseExt getCourseExtById(String id);

    CourseExt save(CourseExt ext);

    CourseExt update(CourseExt ext);

    void deleteCourseExtByCourseId(String course_id);

    void delete(String id);

    List<CourseExt> queryCourseExtByCourseId(String course_id);

    List<CourseExt>  saveAll(List<CourseExt> list);

    List<CourseExt> deleteAndSave(List<CourseExt> removeList,List<CourseExt> updateList );

    List<com.memory.entity.bean.CourseExt> queryCourseExtList(String courseId);

    int setCourseExtStaticPathByCourseIdAndCourseExtSort(String course_id,String sort,String img_url,String audio_url);



}
