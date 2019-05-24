package com.memory.cms.controller;

import com.memory.cms.service.ArticleCommentCmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author INS6+
 * @date 2019/5/23 17:03
 */
@RestController
@RequestMapping(value = "articleComment/cms")
public class ArticleCommentCmsController {

    @Autowired
    private ArticleCommentCmsService articleCommentCmsService;






}
