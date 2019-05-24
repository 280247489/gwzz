package com.memory.cms.repository;

import com.memory.entity.jpa.CourseExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:31
 */

public interface CourseExtCmsRepository extends JpaRepository<CourseExt,String> {

    List<CourseExt> queryCourseExtByCourseIdOrderByCourseExtSortAsc(String course_id );

   // List<CourseExt> queryCourseExtBy


    //  @Query("update Course a set a.courseOnline  = ?1 where a.id =?2 ")
    @Query("select  new com.memory.entity.bean.CourseExt(c.id,c.courseId,c.courseExtNickname,c.courseExtLogo,c.courseExtType,c.courseExtWords,c.courseExtImgUrl,c.courseExtAudio,c.courseExtAudioTimes,c.courseExtSort,c.courseExtCreateTime) " +
            " from CourseExt c  where  c.courseId =?1 ORDER BY  c.courseExtSort ASC")
    List<com.memory.entity.bean.CourseExt> queryCourseExtList(String courseId);

    void deleteCourseExtByCourseId(String courseId);






}
