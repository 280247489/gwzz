package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.ArticleCommentCmsService;
import com.memory.common.utils.PageResult;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import com.memory.entity.bean.ArticleComment;
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


    @RequestMapping(value = "list")
    public Result queryArticleCommentByQue(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                       @RequestParam("key_words") String key_words, @RequestParam("phone_number") String phone_number,
                       @RequestParam("article_name") String article_name,@RequestParam("user_name") String user_name,
                       @RequestParam("comment_type") Integer comment_type,@RequestParam("query_start_time") String query_start_time,
                       @RequestParam("query_end_time") String query_end_time, @RequestParam("sort_role") Integer sort_role,
                                           @RequestParam("comment_root_id") String comment_root_id){

        int pageIndex = page+1;
        int limit = size;
        List<ArticleComment> list = articleCommentCmsService.queryArticleCommentByQueHql( pageIndex, limit, key_words, phone_number,  article_name,  user_name,  comment_type,  query_start_time,  query_end_time,  sort_role,comment_root_id);
        int totalElements = articleCommentCmsService.queryArticleCommentByQueHqlCount(  key_words, phone_number,  article_name,  user_name,  comment_type,  query_start_time,  query_end_time,  sort_role,comment_root_id);
        int totalPages = totalElements/size;
        if(totalElements%size != 0){
            totalPages+=1;
        }
        PageResult pageResult = new PageResult();
        pageResult.setPageNumber(page + 1);
        pageResult.setOffset(0L);
        pageResult.setPageSize(size);
        pageResult.setTotalPages(totalPages);
        pageResult.setTotalElements(Long.valueOf(totalElements));
        pageResult.setData(list);
        return ResultUtil.success(pageResult);
    }





    @RequestMapping(value = "add")
    public  Result addAdminComment(@RequestParam String user_id,@RequestParam String article_id,@RequestParam String user_logo,@RequestParam String user_name,@RequestParam String comment_root_id,@RequestParam String comment_parent_id,@RequestParam String comment_parent_user_name,@RequestParam String comment_content  ){
        Result result = new Result();
        try{
            com.memory.entity.jpa.ArticleComment articleComment = new com.memory.entity.jpa.ArticleComment();
            articleComment.setId(Utils.getShortUUTimeStamp());
            articleComment.setUserId(user_id);
            articleComment.setUserLogo(user_logo);
            articleComment.setUserName(user_name);
            articleComment.setArticleId(article_id);
            articleComment.setCommentType(1);
            articleComment.setCommentRootId(comment_root_id);
            articleComment.setCommentParentId(comment_parent_id);
            articleComment.setCommentParentUserName(comment_parent_user_name);
            articleComment.setCommentContent(comment_content);
            articleComment.setCommentCreateTime(new Date());
            articleComment.setCommentTotalLike(0);
            articleCommentCmsService.addArticleComment(articleComment);
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

            if(articleComment == null){
                return ResultUtil.error(-1,"非法id");
            }
            String comment_root_id = articleComment.getCommentRootId();


            if(comment_id.equals(comment_root_id)){
                articleCommentCmsService.deleteArticleCommentByCommentRootId(comment_root_id);
            }else{
                Date comment_create_time = articleComment.getCommentCreateTime();
                List< com.memory.entity.jpa.ArticleComment> list = articleCommentCmsService.queryArticleCommentList(comment_root_id,comment_create_time);

                if(list.size()<=100){
                    removeComment(list, comment_id,removeList);
                }else{
                    removeList.add(articleComment);
                    result = ResultUtil.error(-1,"递归删除长度大于100，只删除当前评论!");
                }

            }
            articleCommentCmsService.deleteAll(removeList);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    public List<com.memory.entity.jpa.ArticleComment> removeComment(List< com.memory.entity.jpa.ArticleComment> list, String parentId,  List<com.memory.entity.jpa.ArticleComment> removeList){

        if(list.size()<=100){
            for (int i = 0; i < list.size(); i++) {
                com.memory.entity.jpa.ArticleComment dg_obj = list.get(i);
                if(parentId.equals(dg_obj.getCommentParentId())){
                    removeList.add(dg_obj);
                    removeComment(list, dg_obj.getId(),removeList);
                }
            }
        }else{
            System.out.println("递归数据大于100");
        }
        return removeList;
    }

}
