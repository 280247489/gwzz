package com.memory.cms.repository;

import com.memory.entity.jpa.ArticleType;
import com.memory.entity.jpa.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:18
 */

public interface ArticleTypeCmsRepository extends JpaRepository<ArticleType,String> {

    @Query(value = "select  c.id,c.type_name,c.type_create_time,c.img,c.sum,c.is_use " +
            "from article_type c  where  c.is_use =?1 ORDER BY  c.type_create_time desc", nativeQuery = true)
    List<ArticleType> queryArticleTypeTypeList(Integer isUse);


    @Query(value = "select  c.id,c.type_name,c.type_create_time,c.img,c.sum,c.is_use " +
            "from article_type c   ORDER BY  c.type_create_time desc", nativeQuery = true)
    List<ArticleType> queryAllArticleTypeListByTypeCreateTimeDesc();

}
