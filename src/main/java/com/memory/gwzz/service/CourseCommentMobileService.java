package com.memory.gwzz.service;

import java.util.Map;

/**
 * @ClassName CourseCommentMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 16:43
 */
public interface CourseCommentMobileService {
    Map<String,Object> add(String courseId, String userId, String userLogo, String userName,
                           Integer commentType, String commentParentId, String commentParentUserName,
                           String commentContent);
}
