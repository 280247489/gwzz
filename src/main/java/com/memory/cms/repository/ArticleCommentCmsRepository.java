package com.memory.cms.repository;

import com.memory.entity.jpa.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/23 16:56
 */

public interface ArticleCommentCmsRepository extends JpaRepository<ArticleComment,String>, JpaSpecificationExecutor {


    @Query(value = "select  new com.memory.entity.jpa.ArticleComment(c.id,c.articleId,c.userId,c.userLogo,c.userName,c.commentType,c.commentRootId,c.commentParentId,c.commentParentUserName,c.commentContent,c.commentCreateTime,c.commentTotalLike ) " +
            "from ArticleComment c  where  c.commentRootId =?1  AND c.commentCreateTime >?2 ORDER BY  c.commentCreateTime desc")
    List<ArticleComment> queryArticleCommentList(String  comment_root_id, Date comment_create_time );


    ArticleComment getArticleCommentById(String id);

    void deleteArticleCommentByCommentRootId(String root_id);


}
