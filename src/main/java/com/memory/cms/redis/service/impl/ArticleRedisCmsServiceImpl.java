package com.memory.cms.redis.service.impl;

import com.memory.cms.redis.service.ArticleRedisCmsService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.redis.CacheConstantConfig.ARTICLEVIEWMANAGER;

/**
 * @author INS6+
 * @date 2019/7/15 13:40
 */
@Service
public class ArticleRedisCmsServiceImpl implements ArticleRedisCmsService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void initArticleRedisTotal(String articleId) {
        //后台添加阅读量
        String key = ARTICLEVIEWMANAGER + articleId;
        //默认设置成0
        redisUtil.set(key,0);
    }
}
