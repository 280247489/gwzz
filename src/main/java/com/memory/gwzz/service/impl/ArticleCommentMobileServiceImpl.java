package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.ArticleComment;
import com.memory.gwzz.repository.ArticleCommentMobileRepository;
import com.memory.gwzz.service.ArticleCommentMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ArticleCommentMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/24 16:35
 */
@Service("articleCommentMobileService")
public class ArticleCommentMobileServiceImpl implements ArticleCommentMobileService {

    @Autowired
    private ArticleCommentMobileRepository articleCommentMobileRepository;

    @Autowired
    private DaoUtils daoUtils;

    @Transient
    @Override
    public Map<String,Object> add(String articleId, String userId, String userLogo,String userName,
                                  Integer commentType, String commentParentId, String commentParentUserName,
                                  String commentContent){
        Map<String,Object> returnMap = new HashMap<>();
        ArticleComment articleComment = new ArticleComment();
        articleComment.setId(Utils.generateUUIDs());
        articleComment.setArticleId(articleId);
        articleComment.setUserId(userId);
        articleComment.setUserLogo(userLogo);
        articleComment.setUserName(userName);
        articleComment.setCommentType(commentType);
        if(commentType==0){
            articleComment.setCommentRootId(userId);
            articleComment.setCommentParentId("");
            articleComment.setCommentParentUserName("");
        }else if (commentType==1){
            articleComment.setCommentRootId(this.getByPid(commentParentId).getCommentRootId());
            articleComment.setCommentParentId(commentParentId);
            articleComment.setCommentParentUserName("回复@"+commentParentUserName);
        }
        articleComment.setCommentContent(commentContent);
        articleComment.setCommentCreateTime(new Date());
        articleComment.setCommentTotalLike(0);
        articleCommentMobileRepository.save(articleComment);

        returnMap.put("articleComment",articleComment);

        return returnMap;
    }

    public ArticleComment getByPid(String pid){
        return (ArticleComment) daoUtils.getById("ArticleComment",pid);
    }




}
