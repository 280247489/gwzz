package com.memory.cms.redis.service.impl;

import com.memory.cms.redis.service.ArticleRedisCmsService;
import com.memory.common.utils.Utils;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.redis.CacheConstantConfig.*;

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

    @Override
    public void setArticleRedisTotal(String articleId,Integer cumulativeValue) {
        //后台添加阅读量
        String key = ARTICLEVIEWMANAGER + articleId;
        //设置指定阅读量
        redisUtil.set(key,cumulativeValue);
    }

    @Override
    public Integer getArticleRedisAllViewTotal(String articleId) {
        //文章实际阅读量
        Integer realViewTotal = getArticleRedisRealViewTotal(articleId);

        String managerViewTotalKey = ARTICLEVIEWMANAGER + articleId;
        Integer managerViewTotal = (Utils.isNotNull(redisUtil.get(managerViewTotalKey)))?((Integer) redisUtil.get(managerViewTotalKey)):0;

        //总阅读数 = 实际阅读数 + 管理阅读数
        Integer allViewTotal = realViewTotal + managerViewTotal;

        return allViewTotal;
    }

    @Override
    public Integer getArticleRedisRealViewTotal(String articleId) {
        //获取文章实际阅读量
        String allViewTotalKey = ARTICLEVIEW + articleId;
        Integer allViewTotal = (Utils.isNotNull(redisUtil.get(allViewTotalKey)))?(Integer.valueOf(redisUtil.get(allViewTotalKey).toString())):0;
        return allViewTotal;
    }

    @Override
    public Integer getArticleRedisShareTotal(String articleId) {

        //获取文章分享量
        String articleShareTotalKey = ARTICLESHARE + articleId;
        Integer articleShareTotal = (Utils.isNotNull(redisUtil.get(articleShareTotalKey)))?(Integer.valueOf(redisUtil.get(articleShareTotalKey).toString())):0;
        return articleShareTotal;
    }

    @Override
    public Integer getArticleRedisLikeTotal(String articleId) {
        //获取文章点赞量
        String articleLikeTotalKey = ARTICLELIKE + articleId;
        Integer articleLikeTotal = (Utils.isNotNull( redisUtil.get(articleLikeTotalKey)))?(Integer.valueOf(redisUtil.get(articleLikeTotalKey).toString())):0;

        return articleLikeTotal;
    }
}
