package com.memory.cms.service;

import com.memory.entity.bean.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.*;
import java.util.Date;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/23 17:00
 */
public interface ArticleCommentCmsService {


    Page<Painter> queryArticleCommentByQue(Pageable pageable,String key_words,String phone_number, String article_name, String user_name, Integer comment_type, String query_start_time, String query_end_time, Integer sort_role );


    List<ArticleComment> queryArticleCommentByQueHql(int pageIndex,int limit,String key_words,String phone_number, String article_name, String user_name, Integer comment_type, String query_start_time, String query_end_time, Integer sort_role,String comment_root_id);

    int queryArticleCommentByQueHqlCount(String key_words,String phone_number, String article_name, String user_name, Integer comment_type, String query_start_time, String query_end_time, Integer sort_role,String comment_root_id);


    com.memory.entity.jpa.ArticleComment addArticleComment(com.memory.entity.jpa.ArticleComment articleComment);

    com.memory.entity.jpa.ArticleComment updateArticleComment(com.memory.entity.jpa.ArticleComment articleComment);

    List<com.memory.entity.jpa.ArticleComment> queryArticleCommentList(String  comment_root_id,  Date comment_create_time );


    com.memory.entity.jpa.ArticleComment getArticleCommentById(String id);

    void deleteAll(List<com.memory.entity.jpa.ArticleComment> list);

    void deleteArticleCommentByCommentRootId(String root_id);

}
