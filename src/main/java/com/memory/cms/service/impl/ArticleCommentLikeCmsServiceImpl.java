package com.memory.cms.service.impl;

import com.memory.cms.repository.ArticleCommentCmsRepository;
import com.memory.cms.service.ArticleCommentLikeCmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author INS6+
 * @date 2019/5/23 17:16
 */
@Service
public class ArticleCommentLikeCmsServiceImpl implements ArticleCommentLikeCmsService {

    @Autowired
    private ArticleCommentCmsRepository repository;




}
