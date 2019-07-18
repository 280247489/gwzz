package com.memory.cms.redis.service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/7/15 13:40
 */

public interface ArticleRedisCmsService {
    //redis 初始化article 的后台阅读数
    void initArticleRedisTotal(String articleId);

    //设置article manager 阅读数
    void setArticleRedisTotal(String articleId,Integer cumulativeValue);

    //获取总文章阅读量
    Integer getArticleRedisAllViewTotal(String articleId);

    //获取真实文章阅读量
    Integer getArticleRedisRealViewTotal(String articleId);

    //获取文章分享量
    Integer getArticleRedisShareTotal(String articleId);

    //获取文章点赞量
    Integer getArticleRedisLikeTotal(String articleId);

    //获取文章管理阅读量
    Integer getArticleRedisManagerViewTotal(String articleId);


    //获取真实文章阅读量
    List<Object> getArticleRedisRealViewTotal(List<String> articleIds);

    //获取文章分享量
    List<Object> getArticleRedisShareTotal(List<String> articleIds);

    //获取文章点赞量
    List<Object> getArticleRedisLikeTotal(List<String> articleIds);

    //获取文章管理阅读量
    List<Object> getArticleRedisManagerViewTotal(List<String> articleIds);



}
