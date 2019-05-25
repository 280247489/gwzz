package com.memory.gwzz.repository;

import com.memory.entity.jpa.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName ArticleLikeMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/25 10:15
 */
public interface ArticleLikeMobileRepository extends JpaRepository<ArticleLike,String> {
    ArticleLike findByArticleIdAndUserId(String aid,String uid);
}
