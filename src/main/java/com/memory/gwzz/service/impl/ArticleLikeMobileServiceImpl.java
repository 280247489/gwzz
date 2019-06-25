package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.ArticleLike;
import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.ArticleLikeMobileRepository;
import com.memory.gwzz.service.ArticleLikeMobileService;
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


    @Transactional
    @Override
    public ArticleLike like(String aid, String uid) {
        Article article = (Article) daoUtils.getById("Article", aid);
        User user = (User) daoUtils.getById("User", uid);
        ArticleLike articleLike = null;
        if(article != null && user != null){
            articleLike = this.getByAidUid(aid, uid);
            if(articleLike != null){
                if(articleLike.getLikeStatus()==1){
                    articleLike.setLikeStatus(0);
                    article.setArticleTotalLike(article.getArticleTotalLike()-1);
                }else{
                    articleLike.setLikeStatus(1);
                    article.setArticleTotalLike(article.getArticleTotalLike()+1);
                }
            }else{
                if(article !=null && user != null){
                    articleLike = new ArticleLike();
                    articleLike.setId(Utils.getShortUUID());
                    articleLike.setArticleId(aid);
                    articleLike.setUserId(uid);
                    articleLike.setLikeStatus(1);
                    articleLike.setCreateTime(new Date());

                    article.setArticleTotalLike(article.getArticleTotalLike()+1);
                }
            }
            daoUtils.save(article);
            daoUtils.save(articleLike);
        }
        return articleLike;
    }

    public ArticleLike getByAidUid(String aid, String uid){
        return articleLikeMobileRepository.findByArticleIdAndUserId(aid,uid);
    }

    @Override
    public Map<String,Object> ListArticleLikeByUserId(String userId, Integer start, Integer limit){
        Map<String,Object> returnMap = new HashMap<>();

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

        Integer articleCount = daoUtils.getTotalBySQL(stringBuffer1.toString(),map);

        returnMap.put("articleList",articleList);
        returnMap.put("articleCount",articleCount);

        return returnMap;
    }
}
