package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.ArticleComment;
import com.memory.entity.jpa.User;
import com.memory.gwzz.redis.service.ArticleCommentRedisMobileService;
import com.memory.gwzz.repository.ArticleCommentMobileRepository;
import com.memory.gwzz.service.ArticleCommentMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private ArticleCommentRedisMobileService articleCommentRedisMobileService;

    @Transactional
    @Override
    public synchronized Map<String,Object> add(String articleId, User user, Integer commentType, String commentParentId, String content, String content_replace){
        Map<String,Object> returnMap = new HashMap<>();
        ArticleComment articleComment = new ArticleComment();
        Article article = (Article) daoUtils.getById("Article",articleId);
        if (article!=null){
            String userId = user.getId();
            String articleCommentId =Utils.generateUUIDs();
            articleComment.setId(articleCommentId);
            articleComment.setArticleId(articleId);
            articleComment.setUserId(userId);
            articleComment.setUserLogo(user.getUserLogo());
            articleComment.setUserName(user.getUserNickName());
            articleComment.setCommentType(commentType);
            if(commentType==0){
                articleComment.setCommentRootId(articleCommentId);
                articleComment.setCommentParentId("");
                articleComment.setCommentParentUserName("");
                articleComment.setCommentParentContent("");
            }else if (commentType==1){
                ArticleComment articleComment1 = this.getByPid(commentParentId);
                articleComment.setCommentRootId(articleComment1.getCommentRootId());
                articleComment.setCommentParentId(commentParentId);
                articleComment.setCommentParentUserName("@"+articleComment.getUserName());
                articleComment.setCommentParentContent(articleComment1.getCommentContentReplace());
            }
            articleComment.setCommentContent(content);
            articleComment.setCommentContentReplace(content_replace);
            articleComment.setCommentCreateTime(new Date());
            articleComment.setCommentTotalLike(0);

            article.setArticleTotalComment(articleCommentMobileRepository.countAllByArticleId(articleId));
        }

        daoUtils.save(article);
        daoUtils.save(articleComment);

        returnMap.put("articleComment",articleComment);

        return returnMap;
    }

    public ArticleComment getByPid(String pid){
        return (ArticleComment) daoUtils.getById("ArticleComment",pid);
    }

    @Override
    public Map<String, Object> listArtComByAid(String articleId, String uid, Integer start, Integer limit) {
        Map<String,Object> returnMap = new HashMap<>();
        Article article = (Article) daoUtils.getById("Article",articleId);
        //查询一级评论列表
        StringBuffer sbAC = new StringBuffer("select id AS articleCommentId, article_id, user_id, user_logo, user_name, comment_content_replace, comment_create_time, comment_total_like," +
                "(select count(*) from article_comment where article_id=:articleId and comment_root_id = articleCommentId and comment_type=1) " +
//                "(SELECT comment_like_yn FROM article_comment_like WHERE comment_id = articleCommentId AND user_id = '"+uid+"') " +
                "from article_comment where article_id=:articleId AND comment_type=0 order by comment_create_time  desc");
        //查询一级评论总数
        StringBuffer sbCount = new StringBuffer("select count(*) from article_comment where article_id=:articleId AND comment_type=0 ");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("articleId", articleId);
        DaoUtils.Page page = new DaoUtils.Page();
        page.setPageIndex(start);
        page.setLimit(limit);
        List<Object[]> list = daoUtils.findBySQL(sbAC.toString(),map,page,null);
        List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> objMap=new HashMap<String, Object>();
            String articleCommentId = list.get(i)[0].toString();
            Integer commentTotalLike = articleCommentRedisMobileService.getArticleCommentLike(articleId,articleCommentId);
            objMap.put("id", articleCommentId);
            objMap.put("articleId",list.get(i)[1]);
            objMap.put("userId", list.get(i)[2]);
            objMap.put("userLogo", list.get(i)[3]);
            objMap.put("userName", list.get(i)[4]);
            objMap.put("commentContentReplace", list.get(i)[5]);
            objMap.put("commentCreateTime", list.get(i)[6]);

            objMap.put("commentTotalLike", commentTotalLike);
            objMap.put("commentReplySum", list.get(i)[8]);
           //redis 中查询当前用户是否点赞
            objMap.put("commentLike", articleCommentRedisMobileService.isLike(articleCommentId,uid));

            returnList.add(objMap);
        }

        Integer commentCount = daoUtils.getTotalBySQL(sbCount.toString(),map);

        returnMap.put("listArtCom",returnList);
        returnMap.put("commentCount",commentCount);
        returnMap.put("count",article.getArticleTotalComment());

        return returnMap;
    }

    @Override
    public Map<String, Object> listArtComByRid(String commentId, String uid,Integer start, Integer limit) {
        Map<String,Object> returnMap = new HashMap<>();
        //查询一级评论对象
        ArticleComment articleComment = (ArticleComment) daoUtils.getById("ArticleComment",commentId);
        if (articleComment!=null){
            //redis 中查询当前用户是否点赞
            Integer isCommentLike = articleCommentRedisMobileService.isLike(commentId,uid);
            Integer conmmentLikeSum = articleCommentRedisMobileService.getArticleCommentLike(articleComment.getArticleId(),commentId);
            articleComment.setCommentTotalLike(conmmentLikeSum);

            String commentRootId = articleComment.getId();
            //查询子级评论列表
            StringBuffer sbACTWO = new StringBuffer("select id,article_id,user_id,user_logo,user_name,comment_content_replace,comment_parent_user_name,comment_parent_content," +
                    "comment_create_time,comment_total_like from article_comment where comment_root_id = '"+commentRootId+"' and comment_type=1 " +
                    " order by comment_create_time desc");
            StringBuffer sbCount = new StringBuffer("select count(*) from article_comment where  comment_root_id = '"+commentRootId+"' and comment_type=1 ");
            DaoUtils.Page page = new DaoUtils.Page();
            page.setPageIndex(start);
            page.setLimit(limit);
            List<Object[]> list = daoUtils.findBySQL(sbACTWO.toString(),null,page,null);
            List<Map<String, Object>> twoList=new ArrayList<Map<String,Object>>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> objMap=new HashMap<String, Object>();
                objMap.put("id", list.get(i)[0]);
                objMap.put("articleId", list.get(i)[1]);
                objMap.put("userId", list.get(i)[2]);
                objMap.put("userLogo", list.get(i)[3]);
                objMap.put("userName", list.get(i)[4]);
                objMap.put("commentContentReplace", list.get(i)[5]);
                objMap.put("commentParentUserName", list.get(i)[6]);
                objMap.put("commentParentContent", list.get(i)[7]);
                objMap.put("commentCreateTime", list.get(i)[8]);
                objMap.put("commentTotalLike", list.get(i)[9]);

                twoList.add(objMap);
            }

            Integer commentCount = daoUtils.getTotalBySQL(sbCount.toString(),null);

            returnMap.put("articleComment",articleComment);
            returnMap.put("twoList",twoList);
            returnMap.put("commentCount",commentCount);
            returnMap.put("isCommentLike",isCommentLike);
        }else{
            returnMap.put("null",null);
        }
        return returnMap;
    }
}
