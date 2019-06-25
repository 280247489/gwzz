package com.memory.gwzz.service;

import com.memory.entity.jpa.ArticleLike;

import java.util.Map;

/**
 * @ClassName ArticleLikeMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/25 10:13
 */
public interface ArticleLikeMobileService {
    ArticleLike like(String aid, String uid);
    Map<String,Object> ListArticleLikeByUserId(String userId, Integer start, Integer limit);
}
