package com.memory.cms.repository;

import com.memory.entity.jpa.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author INS6+
 * @date 2019/5/8 10:53
 */

public interface ArticleCmsRepository extends JpaRepository<Article,String>, JpaSpecificationExecutor {

    @Modifying
    @Query("update Article a set a.articleOnline  = ?1 where a.id =?2 ")
    int  updateArticleOnlineById(int articleOnline,String id);

    Article queryArticleByArticleTitle(String articleTitle);

    @Query(value="from Article a where a.articleTitle =?1 AND a.id <> ?2")
    Article queryArticleByArticleTitleAndId(String articleTitle,String id);


}
