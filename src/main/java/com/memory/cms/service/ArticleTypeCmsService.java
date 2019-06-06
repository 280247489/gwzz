package com.memory.cms.service;

import com.memory.entity.jpa.ArticleType;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:19
 */

public interface ArticleTypeCmsService {

    List<ArticleType>  queryArticleTypeList();

    ArticleType add(ArticleType articleType);



    void delete(String id);

    List<ArticleType> queryArticleTypeList(Integer isUse);

    int updateArticleTypeIsUseById(int is_use,String id);

    ArticleType queryArticleTypeById(String id);

    ArticleType update(ArticleType articleType);
}
