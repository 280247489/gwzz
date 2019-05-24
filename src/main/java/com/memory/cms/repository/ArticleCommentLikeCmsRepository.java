package com.memory.cms.repository;

import com.memory.entity.jpa.ArticleCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author INS6+
 * @date 2019/5/23 16:59
 */

public interface ArticleCommentLikeCmsRepository extends JpaRepository<ArticleCommentLike,String> {
}
