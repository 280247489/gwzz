package com.memory.gwzz.repository;

import com.memory.entity.jpa.ArticleCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName ArticleCommentLikeMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 13:39
 */
public interface ArticleCommentLikeMobileRepository extends JpaRepository<ArticleCommentLike,String> {
    ArticleCommentLike findByCommentIdAndUserId(String commentId,String userId);
}
