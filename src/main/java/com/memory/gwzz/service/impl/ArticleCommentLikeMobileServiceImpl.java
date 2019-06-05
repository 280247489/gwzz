package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.ArticleComment;
import com.memory.entity.jpa.ArticleCommentLike;
import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.ArticleCommentLikeMobileRepository;
import com.memory.gwzz.service.ArticleCommentLikeMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName ArticleCommentLikeMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 13:38
 */
@Service("articleCommentLikeMobileService")
public class ArticleCommentLikeMobileServiceImpl implements ArticleCommentLikeMobileService {
    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private ArticleCommentLikeMobileRepository articleCommentLikeMobileRepository;

    @Transactional
    @Override
    public ArticleCommentLike like(String cid, String uid) {
        ArticleComment articleComment = (ArticleComment) daoUtils.getById("ArticleComment",cid);
        User user = (User) daoUtils.getById("User",uid);
        ArticleCommentLike articleCommentLike = null;
        if(articleComment != null && user != null){
            articleCommentLike = this.getByCidUid(cid,uid);
            if (articleCommentLike !=null){
                if (articleCommentLike.getCommentLikeYn()==1){
                    Integer b =1;
                    articleCommentLike.setCommentLikeYn(0);
                    articleComment.setCommentTotalLike(articleComment.getCommentTotalLike()-b);
                }else {
                    articleCommentLike.setCommentLikeYn(1);
                    articleComment.setCommentTotalLike(articleComment.getCommentTotalLike()+1);
                }
            }else{
                if (articleComment != null && user != null){
                    articleCommentLike = new ArticleCommentLike();
                    articleCommentLike.setId(Utils.getShortUUID());
                    articleCommentLike.setUserId(uid);
                    articleCommentLike.setCommentId(cid);
                    articleCommentLike.setCommentLikeYn(1);
                    articleCommentLike.setCreateTime(new Date());

                    articleComment.setCommentTotalLike(articleComment.getCommentTotalLike()+1);

                }
            }
            daoUtils.save(articleComment);
            daoUtils.save(articleCommentLike);
        }
        return articleCommentLike;
    }
    public ArticleCommentLike getByCidUid(String cid, String uid){
        return articleCommentLikeMobileRepository.findByCommentIdAndUserId(cid,uid);
    }
}
