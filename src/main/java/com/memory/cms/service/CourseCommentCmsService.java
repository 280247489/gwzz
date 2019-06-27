package com.memory.cms.service;

import com.memory.entity.bean.CourseComment;

import java.util.Date;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/30 10:19
 */

public interface CourseCommentCmsService {

    List<CourseComment> queryCourseCommentByQueHql(int pageIndex,int limit,String key_words,String phone_number, String article_name, String user_name, Integer comment_type, String query_start_time, String query_end_time, Integer sort_role,String comment_root_id, String id,String courseId);

    int queryCourseCommentByQueHqlCount(String key_words,String phone_number, String article_name, String user_name, Integer comment_type, String query_start_time, String query_end_time, Integer sort_role,String comment_root_id, String id,String courseId);


    com.memory.entity.jpa.CourseComment addCourseComment(com.memory.entity.jpa.CourseComment courseComment);

    com.memory.entity.jpa.CourseComment  updateCourseComment(com.memory.entity.jpa.CourseComment courseComment);

    com.memory.entity.jpa.CourseComment  queryCourseCommentById(String id);

    void deleteAll(List<com.memory.entity.jpa.CourseComment> list);

    void deleteCourseCommentByCommentRootId(String root_id);

    List<com.memory.entity.jpa.CourseComment> queryCourseCommentList(String  comment_root_id, Date comment_create_time );




}
