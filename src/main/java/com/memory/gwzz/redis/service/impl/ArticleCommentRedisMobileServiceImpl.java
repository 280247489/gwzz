package com.memory.gwzz.redis.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.ArticleComment;
import com.memory.entity.jpa.ArticleCommentLike;
import com.memory.gwzz.redis.service.ArticleCommentRedisMobileService;
import com.memory.gwzz.repository.ArticleCommentLikeMobileRepository;
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

    @Autowired
    private ArticleCommentLikeMobileRepository articleCommentLikeMobileRepository;

    /**
     * 添加文章评论点赞
     */
    @Override
    public void articleCommentLike(String articleId, String articleCommentId, String userId){
        String articleCommentLike = ARTICLECOMMENTLIKE + articleId;
        String articleCommentLikeDetail =ARTICLECOMMENTLIKEDEATIL +userId;
        try {
            Object isLike = redisUtil.hget(articleCommentLikeDetail,articleCommentId);
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
        }catch (Exception e){
            e.printStackTrace();
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
        try {
            acl = redisUtil.hget(ARTICLECOMMENTLIKE + articleId,articleCommentId);
            if (acl != null){
                articleCommentLike = Integer.valueOf(acl.toString());
            }else {
                ArticleComment articleComment = (ArticleComment) daoUtils.getById("ArticleComment",articleCommentId);
                redisUtil.hset(ARTICLECOMMENTLIKE +articleId,articleCommentId,articleComment.getCommentTotalLike()+"");
                acl = redisUtil.hget(ARTICLECOMMENTLIKE + articleId,articleCommentId);
                if (acl==null){
                    articleCommentLike = articleComment.getCommentTotalLike();
                }else{
                    articleCommentLike = Integer.valueOf(acl.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
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
        try {
            Object userArticleCommentLike =  redisUtil.hget(ARTICLECOMMENTLIKEDEATIL + userId,articleCommentId);
            if (userArticleCommentLike != null){
                isLike = Integer.valueOf(userArticleCommentLike.toString());
            }else {
                ArticleCommentLike articleCommentLike = articleCommentLikeMobileRepository.findByCommentIdAndUserId(articleCommentId,userId);
                if (articleCommentLike != null){
                    isLike = articleCommentLike.getCommentLikeYn();
                }else{
                    isLike = 0;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return isLike;
    }

}
