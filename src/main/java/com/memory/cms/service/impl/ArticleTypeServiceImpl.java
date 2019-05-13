package com.memory.cms.service.impl;

import com.memory.cms.entity.ArticleType;
import com.memory.cms.repository.ArticleTypeRepository;
import com.memory.cms.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:22
 */
@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {

    @Autowired
    private ArticleTypeRepository repository;

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
