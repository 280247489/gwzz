package com.memory.cms.service.impl;

import com.memory.entity.jpa.ArticleType;
import com.memory.cms.repository.ArticleTypeCmsRepository;
import com.memory.cms.service.ArticleTypeCmsService;
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
        return repository.findAll();
    }

    @Override
    public ArticleType add(ArticleType articleType) {
        return repository.save(articleType);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
