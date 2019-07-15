package com.memory.cms.redis.service;

/**
 * @author INS6+
 * @date 2019/7/15 13:40
 */

public interface ArticleRedisCmsService {
    //redis 初始化article 的后台阅读数
    void initArticleRedisTotal(String articleId);


}
