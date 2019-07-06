package com.memory.gwzz.redis.service;

/**
 * @ClassName ArticleRedisMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/4 15:43
 */
public interface ArticleRedisMobileService {
    //文章搜索记录
    void searchArticle(String userId, String searchKey);
}
