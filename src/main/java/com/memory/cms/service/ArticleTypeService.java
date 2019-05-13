package com.memory.cms.service;

import com.memory.cms.entity.ArticleType;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:19
 */

public interface ArticleTypeService {

    List<ArticleType>  queryArticleTypeList();

    ArticleType add(ArticleType articleType);

    void delete(Integer id);


}
