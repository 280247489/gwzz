package com.memory.cms.service.impl;

import com.memory.cms.repository.ArticleCmsRepository;
import com.memory.cms.service.ArticleCommentCmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author INS6+
 * @date 2019/5/23 17:01
 */
@Service
public class ArticleCommentCmsServiceImpl implements ArticleCommentCmsService {

    @Autowired
    private ArticleCmsRepository repository;



}
