package com.memory.gwzz.redis.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.ArticleComment;
import com.memory.gwzz.redis.service.ArticleCommentRedisMobileService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.redis.CacheConstantConfig.ARTICLECOMMENTLIKE;
import static com.memory.redis.CacheConstantConfig.ARTICLECOMMENTLIKEDEATIL;

/**
 * @ClassName ArticleCommentRedisMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/15 15:27
 */
@Service(value = "articleCommentRedisMobileService")
public class ArticleCommentRedisMobileServiceImpl implements ArticleCommentRedisMobileService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 添加文章评论点赞
     */
    @Override
    public void articleCommentLike(String articleId, String articleCommentId, String userId){
        String articleCommentLike = ARTICLECOMMENTLIKE + articleId;
        String articleCommentLikeDetail =ARTICLECOMMENTLIKEDEATIL +userId;
        Object isLike = redisUtil.hget(articleCommentLike,articleCommentId);
        if (isLike != null){
            Integer like = Integer.valueOf(isLike.toString());
            if (like==0){
                redisUtil.hincr(articleCommentLike,articleCommentId,1);
                redisUtil.hset(articleCommentLikeDetail,articleCommentId,"1");
            }else{
                redisUtil.hdecr(articleCommentLike,articleCommentId,1);
                redisUtil.hset(articleCommentLikeDetail,articleCommentId,"0");
            }
        }else {
            redisUtil.hincr(articleCommentLike,articleCommentId,1);
            redisUtil.hset(articleCommentLikeDetail,articleCommentId,"1");
        }

    }

    /**
     * 获取文章评论点赞次数
     * @param articleId
     * @param articleCommentId
     * @return
     */
    @Override
    public int getArticleCommentLike(String articleId, String articleCommentId ){
        Integer articleCommentLike = 0;
        Object acl = null;
        acl = redisUtil.hget(ARTICLECOMMENTLIKE + articleId,articleCommentId);
        if (acl != null){
            articleCommentLike = Integer.valueOf(acl.toString());
        }else {
            ArticleComment articleComment = (ArticleComment) daoUtils.getById("ArticleComment",articleCommentId);
            redisUtil.hset(ARTICLECOMMENTLIKE +articleId,articleCommentId,articleComment.getCommentTotalLike()+"");
            acl = redisUtil.hget(ARTICLECOMMENTLIKE + articleId,articleCommentId);
            articleCommentLike = Integer.valueOf(acl.toString());
        }
        return articleCommentLike;
    }

    /**
     * 查询当前用户是否点赞
     * @param articleCommentId
     * @param userId
     * @return
     */
    @Override
    public int isLike ( String articleCommentId, String userId){
        Integer isLike = 0;
        Object userArticleCommentLike =  redisUtil.hget(ARTICLECOMMENTLIKEDEATIL + userId,articleCommentId);
        if (userArticleCommentLike != null){
            isLike = Integer.valueOf(userArticleCommentLike.toString());
        }
        return isLike;
    }

}
