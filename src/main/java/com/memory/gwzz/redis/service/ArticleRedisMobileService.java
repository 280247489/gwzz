package com.memory.gwzz.redis.service;

import java.util.Map;

/**
 * @ClassName ArticleRedisMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/4 15:43
 */
public interface ArticleRedisMobileService {
    //文章搜索记录
    void searchArticle(String userId, String searchKey);

    void articleView(String articleId, String userId, Integer os,Integer terminal);

    void articleShare(String articleId, String userId, Integer os);

    void articleLike(String articleId, String userId);

    Integer getArticleView(String articleId);

    Integer getArticleShare(String articleId);

    Integer getArticleLike(String articleId);

    int isLike(String aid, String uid);

    Map<Object,Object> userLike(String userId);
}
