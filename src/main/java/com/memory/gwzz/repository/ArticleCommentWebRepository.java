package com.memory.gwzz.repository;

import com.memory.entity.jpa.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName ArticleCommentWebRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/24 16:38
 */
public interface ArticleCommentWebRepository extends JpaRepository<ArticleComment,String> {
}
