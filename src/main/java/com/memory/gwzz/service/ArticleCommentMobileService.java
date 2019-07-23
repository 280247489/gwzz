package com.memory.gwzz.service;

import com.memory.entity.jpa.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @ClassName ArticleCommentMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/24 16:36
 */
public interface ArticleCommentMobileService {
    Map<String,Object> add(String articleId, User user, Integer commentType, String commentParentId, String content, String content_replace);

    Map<String, Object> listArtComByAid(String articleId, String uid, Integer start, Integer limit);

    Map<String, Object> listArtComByRid(String commentId, String uid, Integer start, Integer limit);

    @Transactional
    void delArticleComment(String articleCommentId);
}
