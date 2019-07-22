package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.ArticleLike;
import com.memory.entity.jpa.User;
import com.memory.gwzz.redis.service.ArticleRedisMobileService;
import com.memory.gwzz.repository.ArticleLikeMobileRepository;
import com.memory.gwzz.service.ArticleLikeMobileService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleLikeMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/25 10:13
 */
@Service("articleLikeMobileService")
public class ArticleLikeMobileServiceImpl implements ArticleLikeMobileService {

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private ArticleLikeMobileRepository articleLikeMobileRepository;

    @Autowired
    private ArticleRedisMobileService articleRedisMobileService;

    @Autowired
    private RedisUtil redisUtil;


//    @Transactional
//    @Override
//    public ArticleLike like(String aid, String uid) {
//        Article article = (Article) daoUtils.getById("Article", aid);
//        User user = (User) daoUtils.getById("User", uid);
//        ArticleLike articleLike = null;
//        if(article != null && user != null){
//            articleLike = this.getByAidUid(aid, uid);
//            if(articleLike != null){
//                if(articleLike.getLikeStatus()==1){
//                    articleLike.setLikeStatus(0);
//                    article.setArticleTotalLike(article.getArticleTotalLike()-1);
//                }else{
//                    articleLike.setLikeStatus(1);
//                    article.setArticleTotalLike(article.getArticleTotalLike()+1);
//                }
//            }else{
//                if(article !=null && user != null){
//                    articleLike = new ArticleLike();
//                    articleLike.setId(Utils.generateUUIDs());
//                    articleLike.setArticleId(aid);
//                    articleLike.setUserId(uid);
//                    articleLike.setLikeStatus(1);
//                    articleLike.setCreateTime(new Date());
//
//                    article.setArticleTotalLike(article.getArticleTotalLike()+1);
//                }
//            }
//            daoUtils.save(article);
//            daoUtils.save(articleLike);
//        }
//        return articleLike;
//    }

    @Transactional
    @Override
    public int like(String aid, String uid) {
        Article article = (Article) daoUtils.getById("Article", aid);
        User user = (User) daoUtils.getById("User", uid);
        ArticleLike articleLike = null;
        Integer isLike = 0;
        if(article != null && user != null){
            articleRedisMobileService.articleLike(aid,uid);
            articleLike = this.getByAidUid(aid, uid);
            isLike = articleRedisMobileService.isLike(aid,uid);
            if (articleLike!=null){
               articleLike.setLikeStatus(isLike);
            }else {
                articleLike = new ArticleLike();
                articleLike.setId(Utils.generateUUIDs());
                articleLike.setArticleId(aid);
                articleLike.setUserId(uid);
                articleLike.setLikeStatus(1);
                articleLike.setCreateTime(new Date());
            }
            daoUtils.save(articleLike);
        }

        return isLike;
    }

    public ArticleLike getByAidUid(String aid, String uid){
        return articleLikeMobileRepository.findByArticleIdAndUserId(aid,uid);
    }

    @Override
    public Map<String,Object> ListArticleLikeByUserId(String userId, Integer start, Integer limit){
        Map<String,Object> returnMap = new HashMap<>();
//        Map<Object,Object> articleLikeMap = new HashMap<>();
//        articleLikeMap = articleRedisMobileService.userLike(userId);
//        for(Object value : articleLikeMap.values()){
//            if (value.toString() == "1"){
//                System.out.println("keys is "+ articleLikeMap.keySet());
//            }
//            System.out.println("value is "+ value);
//        }

        StringBuffer stringBuffer = new StringBuffer(" SELECT NEW com.memory.gwzz.model.Article(a.id, a.typeId, a.articleTitle, a.articleLogo1, a.articleLogo2, a.articleLogo3, " +
                "a.articleLabel, a.articleOnline, a.articleTotalComment, a.articleTotalView, a.articleReleaseTime) FROM Article a, ArticleLike al WHERE a.id=al.articleId AND al.likeStatus = 1 ");
        Map<String,Object> map = new HashMap<>();

        StringBuffer stringBuffer1 = new StringBuffer("SELECT count(*) FROM article_like where like_status = 1 ");
        map.put("userId",userId);
        stringBuffer.append(" AND al.userId =: userId ");
        stringBuffer1.append("AND user_id =:userId");

        DaoUtils.Page pageArticle = new DaoUtils.Page();
        pageArticle.setPageIndex(start);
        pageArticle.setLimit(limit);

        stringBuffer.append(" ORDER BY al.createTime DESC");

        List<com.memory.gwzz.model.Article> articleList = daoUtils.findByHQL(stringBuffer.toString(),map,pageArticle);
        //重写文章阅读量
        for (int i = 0;i<articleList.size();i++){
            String articleId = articleList.get(i).getId();
            articleList.get(i).setArticleTotalView(articleRedisMobileService.getArticleView(articleId));
        }

        Integer articleCount = daoUtils.getTotalBySQL(stringBuffer1.toString(),map);

        returnMap.put("articleList",articleList);
        returnMap.put("articleCount",articleCount);

        return returnMap;
    }

//    @Override
//    public int isLike(String aid,String uid){
//        Integer isLike =0;
//        ArticleLike articleLike = articleLikeMobileRepository.findByArticleIdAndUserId(aid,uid);
//        if (articleLike==null){
//            isLike=0;
//        }else{
//            if (articleLike.getLikeStatus()==1){
//                isLike=1;
//            }else if (articleLike.getLikeStatus()==0){
//                isLike=0;
//            }
//        }
//        return isLike;
//    }
}
