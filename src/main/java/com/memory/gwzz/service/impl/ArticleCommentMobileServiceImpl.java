package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.ArticleComment;
import com.memory.entity.jpa.User;
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
    public Map<String,Object> add(String articleId, User user, Integer commentType, String commentParentId, String content, String content_replace){
        Map<String,Object> returnMap = new HashMap<>();
        ArticleComment articleComment = new ArticleComment();
        String userId = user.getId();
        articleComment.setId(Utils.generateUUIDs());
        articleComment.setArticleId(articleId);
        articleComment.setUserId(userId);
        articleComment.setUserLogo(user.getUserLogo());
        articleComment.setUserName(user.getUserName());
        articleComment.setCommentType(commentType);
        if(commentType==0){
            articleComment.setCommentRootId(userId);
            articleComment.setCommentParentId("");
            articleComment.setCommentParentUserName("");
            articleComment.setCommentParentContent("");
        }else if (commentType==1){
            ArticleComment articleComment1 = this.getByPid(commentParentId);
            articleComment.setCommentRootId(articleComment1.getCommentRootId());
            articleComment.setCommentParentId(commentParentId);
            articleComment.setCommentParentUserName("@"+articleComment1.getUserName());
            articleComment.setCommentParentContent(articleComment1.getCommentContentReplace());
        }
        articleComment.setCommentContent(content);
        articleComment.setCommentContentReplace(content_replace);
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
