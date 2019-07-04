package com.memory.gwzz.redis.service.impl;

import com.memory.gwzz.redis.service.ArticleRedisMobileService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.redis.CacheConstantConfig.SEARCHARTICLESEARCHAPPID;


/**
 * @ClassName ArticleRedisMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/4 15:42
 */
@Service("articleRedisMobileService")
public class ArticleRedisMobileServiceImpl implements ArticleRedisMobileService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void searchArticle(String userId, String searchKey){
        String keyIncr = SEARCHARTICLESEARCHAPPID + userId;
        redisUtil.hincr(keyIncr,searchKey,1);
    }
}
