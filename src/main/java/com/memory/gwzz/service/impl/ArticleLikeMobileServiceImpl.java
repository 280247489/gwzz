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
}
