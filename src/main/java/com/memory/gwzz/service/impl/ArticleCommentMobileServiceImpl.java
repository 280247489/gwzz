package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
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
        articleComment.setCommentRootId(articleId);
        if(commentType==0){
            articleComment.setCommentParentId(articleId);
        }else if (commentType==1){
            articleComment.setCommentParentId(commentParentId);
        }
        articleComment.setCommentParentUserName(commentParentUserName);
        articleComment.setCommentContent(commentContent);
        articleComment.setCommentCreateTime(new Date());
        articleComment.setCommentTotalLike(0);
        articleCommentMobileRepository.save(articleComment);

        returnMap.put("articleComment",articleComment);

        return returnMap;
    }





}
