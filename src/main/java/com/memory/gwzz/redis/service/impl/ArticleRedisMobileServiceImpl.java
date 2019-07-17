package com.memory.gwzz.redis.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.ArticleLike;
import com.memory.gwzz.redis.service.ArticleRedisMobileService;
import com.memory.gwzz.repository.ArticleLikeMobileRepository;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.redis.CacheConstantConfig.*;


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

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private ArticleLikeMobileRepository articleLikeMobileRepository;

    /**
     * 搜索文章
     * @param userId
     * @param searchKey
     */
    @Override
    public void searchArticle(String userId, String searchKey) {
        try {
            String keyIncr = SEARCHARTICLESEARCHAPPID + userId;
            redisUtil.hincr(keyIncr, searchKey, 1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 添加文章阅读量
     * @param articleId
     * @param userId
     * @param os
     * @return
     */
    @Override
    public void articleView(String articleId, String userId, Integer os,Integer terminal){
        String articleView = ARTICLEVIEW + articleId;
        String articleViewId = ARTICLEVIEWID + articleId;
        String articleViewIosIn = ARTICLEVIEWIOSIN + articleId;
        String articleViewAndroidIn = ARTICLEVIEWANDROIDIN + articleId;
        String articleViewAndroidOut = ARTICLEVIEWANDROIDOUT + articleId;
        try {
            redisUtil.incr(articleView,1);
            redisUtil.hincr(articleViewId,userId,1);
            if (terminal==1){
                redisUtil.incr(articleViewAndroidOut,1);
            }else{
                if (os==0){
                    redisUtil.incr(articleViewIosIn,1);
                }else{
                    redisUtil.incr(articleViewAndroidIn,1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 添加分享次数
     * @param articleId
     * @param userId
     * @param os
     */
    @Override
    public void articleShare(String articleId,String userId,Integer os){
        String articleShare = ARTICLESHARE + articleId;
        String articleShareId = ARTICLESHAREID + articleId;
        String articleShareIos = ARTICLESHAREIOS + articleId;
        String articleShareAndroid = ARTICLESHAREANDROID + articleId;
        try {
            redisUtil.incr(articleShare,1);
            redisUtil.hincr(articleShareId,userId,1);
            if (os==1){
                redisUtil.incr(articleShareAndroid,1);
            }else {
                redisUtil.incr(articleShareIos,1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 添加用户点赞
     * @param articleId
     * @param userId
     */
    @Override
    public void articleLike(String articleId, String userId){
        String articleLike = ARTICLELIKE + articleId;
        String articleLikeDetail = ARTICLELIKEDETAIL + userId;
        try {
            //判断用户是否存在点赞数据
            Object isLike =  redisUtil.hget(articleLikeDetail,articleId);
            if (isLike != null){
                Integer like = Integer.valueOf(isLike.toString());
                if (like==0){
                    redisUtil.incr(articleLike,1);
                    redisUtil.hset(articleLikeDetail,articleId,"1");
                }else{
                    redisUtil.decr(articleLike,1);
                    redisUtil.hset(articleLikeDetail,articleId,"0");
                }
            }else{
                redisUtil.incr(articleLike,1);
                redisUtil.hset(articleLikeDetail,articleId,"1");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 查询阅读量
     * @param articleId
     * @return
     */
    @Override
    public Integer getArticleView(String articleId){
        Integer view = 0;
        Integer articleView = 0;
        Integer articleViewManager = 0;
        Object v1 =  null;
        Object v2 = null;
        try {
            v1 = redisUtil.get(ARTICLEVIEW + articleId);
            v2 = redisUtil.get(ARTICLEVIEWMANAGER + articleId);
            if (v1 != null){
                articleView = Integer.valueOf(v1.toString());
            }else{
                Article article = (Article) daoUtils.getById("Article",articleId);
                redisUtil.set(ARTICLEVIEW + articleId,article.getArticleTotalView()+"");
                v1 = redisUtil.get(ARTICLEVIEW + articleId);
                if (v1==null){
                    articleView = article.getArticleTotalView();
                }else {
                    articleView = Integer.valueOf(v1.toString());
                }
            }
            if (v2 != null){
                articleViewManager = Integer.valueOf(v2.toString());
            }else{
                redisUtil.set(ARTICLEVIEWMANAGER + articleId,"0");
                articleViewManager = 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        view = articleView + articleViewManager;
        return view;
    }

    /**
     * 查询文章分享量
     * @param articleId
     * @return
     */
    @Override
    public Integer getArticleShare(String articleId){
        Integer share = 0;
        Object s =  null;
        try {
            s = redisUtil.get(ARTICLESHARE + articleId);
            if ( s != null){
                share = Integer.valueOf(s.toString()) ;
            }else{
                Article article = (Article) daoUtils.getById("Article",articleId);
                redisUtil.set(ARTICLESHARE + articleId,article.getArticleTotalShare()+"");
                s =  redisUtil.get(ARTICLESHARE + articleId);
                if (s!=null){
                    share = Integer.valueOf(s.toString()) ;
                }else{
                    share = article.getArticleTotalShare();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return share;
    }

    /**
     * 查询文章点赞量
     * @param articleId
     * @return
     */
    @Override
    public Integer getArticleLike(String articleId){
        Integer like = 0;
        Object l = null;
        try {
            l = redisUtil.get(ARTICLELIKE + articleId);
            if (l != null){
                like = Integer.valueOf(l.toString());
            }else{
                Article article = (Article) daoUtils.getById("Article",articleId);
                redisUtil.set(ARTICLELIKE + articleId,article.getArticleTotalLike()+"");
                l = redisUtil.get(ARTICLELIKE + articleId);
                if (l == null){
                    like = article.getArticleTotalLike();
                }else {
                    like = Integer.valueOf(l.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return like;
    }


    /**
     * 查询当前用户是否点赞
     * @param aid
     * @param uid
     * @return
     */
    @Override
    public int isLike(String aid, String uid){
        Integer isLike =0;
        Object userLike = redisUtil.hget(ARTICLELIKEDETAIL+ uid,aid);
        try {
            if (userLike==null){
                ArticleLike  articleLike = articleLikeMobileRepository.findByArticleIdAndUserId(aid, uid);
                if (articleLike!=null){
                    isLike = articleLike.getLikeStatus();
                }else {
                    isLike=0;
                }
            }else{
                isLike  = Integer.valueOf(userLike.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isLike;
    }



}
