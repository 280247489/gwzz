package com.memory.cms.service.impl;

import com.memory.cms.repository.ArticleTypeCmsRepository;
import com.memory.cms.service.ArticleTypeCmsService;
import com.memory.entity.jpa.ArticleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:22
 */
@Service
public class ArticleTypeCmsServiceImpl implements ArticleTypeCmsService {

    @Autowired
    private ArticleTypeCmsRepository repository;

    @Override
    public List<ArticleType> queryArticleTypeList() {
        return repository.queryAllArticleTypeListByTypeCreateTimeDesc();
    }

    @Override
    public ArticleType add(ArticleType articleType) {
        return repository.save(articleType);
    }


    @Override
    public void delete(String id) {

    }

    @Override
    public List<ArticleType> queryArticleTypeList(Integer isUse) {
        return repository.queryArticleTypeTypeList(isUse);
    }


    @Override
    public int updateArticleTypeIsUseById(int is_use, String id) {
        return repository.updateArticleTypeIsUseById(is_use,id);
    }

    @Override
    public ArticleType queryArticleTypeById(String id) {
        return repository.queryArticleTypeById(id);
    }

    @Override
    public ArticleType update(ArticleType articleType) {
        return repository.save(articleType);
    }
}
