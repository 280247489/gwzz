package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.entity.jpa.ArticleComment;
import com.memory.gwzz.repository.ArticleCommentWebRepository;
import com.memory.gwzz.service.ArticleCommentWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ArticleCommentWebServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/24 16:35
 */
@Service("articleCommentWebService")
public class ArticleCommentWebServiceImpl implements ArticleCommentWebService {

    @Autowired
    private ArticleCommentWebRepository articleCommentWebRepository;



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
        String cpId = commentType==0?articleId:commentType==1?commentParentId:"";
        articleComment.setCommentParentId(cpId);
        articleComment.setCommentParentUserName(commentParentUserName);
        articleComment.setCommentContent(commentContent);
        articleComment.setCommentCreateTime(new Date());
        articleComment.setCommentTotalLike(0);
        articleCommentWebRepository.save(articleComment);

        returnMap.put("articleComment",articleComment);

        return returnMap;
    }





}
