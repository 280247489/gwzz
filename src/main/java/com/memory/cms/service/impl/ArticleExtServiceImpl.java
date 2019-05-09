package com.memory.cms.service.impl;

import com.memory.cms.entity.ArticleExt;
import com.memory.cms.repository.ArticleExtRepository;
import com.memory.cms.service.ArticleExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:32
 */
@Service
public class ArticleExtServiceImpl implements ArticleExtService {

    @Autowired
    private ArticleExtRepository repository;

    @Override
    public ArticleExt getArticleExcById(Integer id) {
        if(repository.findById(id).hashCode() != 0){
            return repository.findById(id).get();
        }else {
            return null;
        }
    }

    @Override
    public ArticleExt save(ArticleExt ext) {

        return repository.save(ext);
    }

    @Override
    public ArticleExt update(ArticleExt ext) {

        return repository.save(ext);
    }

    @Override
    public void deleteArticleExtByArticleId(Integer article_id) {
         repository.deleteArticleExtByArticleId(article_id);
    }

    @Override
    public List<ArticleExt> queryArticleExtListByArticleId(Integer article_id) {
        return repository.queryArticleExtByArticleId(article_id);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
