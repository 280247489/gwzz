package com.memory.cms.repository;

import com.memory.entity.jpa.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author INS6+
 * @date 2019/5/23 16:56
 */

public interface ArticleCommentCmsRepository extends JpaRepository<ArticleComment,String> {
}
