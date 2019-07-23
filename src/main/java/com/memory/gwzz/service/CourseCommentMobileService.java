package com.memory.gwzz.service;

import com.memory.entity.jpa.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @ClassName CourseCommentMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 16:43
 */
public interface CourseCommentMobileService {
    Map<String, Object> add(String courseId, User user, Integer commentType, String commentParentId, String content, String content_replace);

    Map<String, Object> listComByCid(String courseId,Integer start,Integer limit,String uid);

    Map<String, Object> listCouComByRid(String commentId,String uid, Integer start, Integer limit);

    @Transactional
    void delCourseComment(String courseCommentId);
}
