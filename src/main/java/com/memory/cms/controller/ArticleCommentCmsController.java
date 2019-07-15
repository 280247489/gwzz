package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.ArticleCmsService;
import com.memory.cms.service.ArticleCommentCmsService;
import com.memory.cms.service.SysAdminCmsService;
import com.memory.common.utils.PageResult;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import com.memory.entity.bean.ArticleComment;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.SysAdmin;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author INS6+
 * @date 2019/5/23 17:03
 */
@RestController
@RequestMapping(value = "articleComment/cms")
public class ArticleCommentCmsController {

    @Autowired
    private ArticleCommentCmsService articleCommentCmsService;

    @Autowired
    private SysAdminCmsService sysAdminCmsService;

    @Autowired
    private ArticleCmsService articleCmsService;



    @RequestMapping(value = "list")
    public Result queryArticleCommentByQue(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                           @RequestParam("key_words") String key_words, @RequestParam("phone_number") String phone_number,
                                           @RequestParam("article_name") String article_name,@RequestParam("user_name") String user_name,
                                           @RequestParam("comment_type") Integer comment_type,@RequestParam("query_start_time") String query_start_time,
                                           @RequestParam("query_end_time") String query_end_time, @RequestParam("sort_role") Integer sort_role,
                                           @RequestParam("comment_root_id") String comment_root_id, @RequestParam(value = "id",required = false) String id,
                                           @RequestParam(value = "article_id",required = false) String article_id){

        int pageIndex = page+1;
        int limit = size;
        List<ArticleComment> list = articleCommentCmsService.queryArticleCommentByQueHql( pageIndex, limit, key_words, phone_number,  article_name,  user_name,  comment_type,  query_start_time,  query_end_time,  sort_role,comment_root_id,id,article_id);
        int totalElements = articleCommentCmsService.queryArticleCommentByQueHqlCount(  key_words, phone_number,  article_name,  user_name,  comment_type,  query_start_time,  query_end_time,  sort_role,comment_root_id,id,article_id);
        PageResult pageResult = PageResult.getPageResult(page, size, list, totalElements);
        return ResultUtil.success(pageResult);
    }




    /**
     *
     * @param user_id
     * @param user_logo
     * @param user_name
     * @param comment_parent_id
     * @param content
     * @return
     */
    @RequestMapping(value = "add")
    public  Result addAdminComment(@RequestParam("user_id") String user_id,@RequestParam("user_logo") String user_logo,@RequestParam("user_name") String user_name,@RequestParam("comment_parent_id") String comment_parent_id,@RequestParam("content") String content ,@RequestParam("content_replace") String content_replace  ){
        Result result = new Result();
        try{

            SysAdmin sysAdmin =sysAdminCmsService.getSysAdminById(user_id);
            if(sysAdmin==null){
                return ResultUtil.error(-1,"非法用户！");
            }

            System.out.println("content_replace == " + content_replace);
            com.memory.entity.jpa.ArticleComment parentArticleComment =  articleCommentCmsService.getArticleCommentById(comment_parent_id);
            com.memory.entity.jpa.ArticleComment articleComment  = new com.memory.entity.jpa.ArticleComment();
            articleComment.setId(Utils.getShortUUTimeStamp());
            articleComment.setUserId(user_id);
            articleComment.setUserLogo(user_logo);
            articleComment.setUserName(user_name);
            articleComment.setArticleId(parentArticleComment.getArticleId());
            articleComment.setCommentType(1);
            articleComment.setCommentRootId(parentArticleComment.getCommentRootId());
            articleComment.setCommentParentId(comment_parent_id);
       //     if(parentArticleComment.getCommentType() == 1){
                System.out.println("parentUserName = " + "@"+parentArticleComment.getUserName());
                articleComment.setCommentParentUserName("@"+parentArticleComment.getUserName());
                articleComment.setCommentParentContent(parentArticleComment.getCommentContentReplace());
     /*       }else{
                articleComment.setCommentParentUserName("");
                articleComment.setCommentParentContent("");
            }*/
            articleComment.setCommentContent(content);
            articleComment.setCommentCreateTime(new Date());
            articleComment.setCommentTotalLike(0);
            articleComment.setCommentContentReplace(content_replace);
            com.memory.entity.jpa.ArticleComment articleComment1 =   articleCommentCmsService.addArticleComment(articleComment);

            Article  article = articleCmsService.getArticleById(parentArticleComment.getArticleId());
            //查询当前文章的评论数量
            int articleTotalComment = articleCommentCmsService.getArticleTotalCommentByArticleId(parentArticleComment.getArticleId());
            //同步文章评论数
            article.setArticleTotalComment(articleTotalComment);
            articleCmsService.update(article);

            result = ResultUtil.success(articleComment1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }





    @RequestMapping(value = "remove")
    public  Result remove(@RequestParam("comment_id") String comment_id){
        Result result = new Result();
        try{

            List<com.memory.entity.jpa.ArticleComment> removeList = new ArrayList<>();

            com.memory.entity.jpa.ArticleComment articleComment = articleCommentCmsService.getArticleCommentById(comment_id);

            Article  article = articleCmsService.getArticleById(articleComment.getArticleId());
            if(articleComment == null || article == null){
                return ResultUtil.error(-1,"非法id");
            }
            String comment_root_id = articleComment.getCommentRootId();

            if(articleComment.getCommentType() == 0){
                articleCommentCmsService.deleteArticleCommentByCommentRootId(comment_root_id);
            }else{
                removeList = articleCommentCmsService.queryArticleCommentByCommentParentId(articleComment.getId());
                removeList.add(articleComment);
                articleCommentCmsService.deleteAll(removeList);
            }
            //查询当前文章的评论数量
            int articleTotalComment = articleCommentCmsService.getArticleTotalCommentByArticleId(article.getId());
            //同步文章评论数
            article.setArticleTotalComment(articleTotalComment);
            articleCmsService.update(article);
            result = ResultUtil.success("删除成功");

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    public List<com.memory.entity.jpa.ArticleComment> removeComment(List< com.memory.entity.jpa.ArticleComment> list, String parentId,  List<com.memory.entity.jpa.ArticleComment> removeList){

        if(list.size()<=100){
            if(list.size() == 1){
                removeList.add(list.get(0));
            }else{
                for (int i = 0; i < list.size(); i++) {
                    com.memory.entity.jpa.ArticleComment dg_obj = list.get(i);
                    if(parentId.equals(dg_obj.getCommentParentId())){
                        removeList.add(dg_obj);
                        removeComment(list, dg_obj.getId(),removeList);
                    }
                }
            }


        }else{
            System.out.println("递归数据大于100");
        }
        return removeList;
    }

}
