package com.memory.gwzz.service;

import com.memory.entity.jpa.User;

import java.util.Map;

/**
 * @ClassName ArticleCommentMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/24 16:36
 */
public interface ArticleCommentMobileService {
    Map<String,Object> add(String articleId, User user, Integer commentType, String commentParentId, String content, String content_replace);
}
