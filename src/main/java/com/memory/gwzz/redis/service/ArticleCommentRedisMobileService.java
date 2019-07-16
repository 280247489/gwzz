package com.memory.gwzz.redis.service;

/**
 * @ClassName ArticleCommentRedisMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/15 15:27
 */
public interface ArticleCommentRedisMobileService  {
    void articleCommentLike(String articleId, String articleCommentId, String userId);

    int getArticleCommentLike(String articleId, String articleCommentId);

    int isLike(String articleCommentId, String userId);
}
