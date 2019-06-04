package com.memory.gwzz.service;

import com.memory.entity.jpa.ArticleCommentLike;

/**
 * @ClassName ArticleCommentLikeMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 13:38
 */
public interface ArticleCommentLikeMobileService {

    ArticleCommentLike like(String cid,String uid);
}
