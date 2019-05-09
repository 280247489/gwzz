package com.memory.cms.repository;

import com.memory.cms.entity.ArticleExt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:31
 */

public interface ArticleExtRepository extends JpaRepository<ArticleExt,Integer> {

    List<ArticleExt> queryArticleExtByArticleId(Integer articleId);

    void deleteArticleExtByArticleId(Integer articleId);

}
