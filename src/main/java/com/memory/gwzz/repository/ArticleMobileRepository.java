package com.memory.gwzz.repository;

import com.memory.entity.jpa.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName ArticleMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/21 14:11
 */
public interface ArticleMobileRepository extends JpaRepository<Article,String> {
    Article findByIdAndArticleOnline(String id,Integer online);
}
