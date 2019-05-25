package com.memory.gwzz.service;

import java.util.Map;

/**
 * @ClassName ArticleCommentWebService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/24 16:36
 */
public interface ArticleCommentWebService {
    Map<String,Object> add(String articleId, String userId, String userLogo, String userName,
                           Integer commentType, String commentParentId, String commentParentUserName,
                           String commentContent);
}
