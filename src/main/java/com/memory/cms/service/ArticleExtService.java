package com.memory.cms.service;

import com.memory.cms.entity.ArticleExt;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:32
 */

public interface ArticleExtService {

    ArticleExt getArticleExcById(Integer id);

    ArticleExt save(ArticleExt ext);

    ArticleExt update(ArticleExt ext);

    void deleteArticleExtByArticleId(Integer article_id);

    void delete(Integer id);

    List<ArticleExt> queryArticleExtListByArticleId(Integer article_id);

}
