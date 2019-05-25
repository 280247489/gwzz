package com.memory.gwzz.service;

import com.memory.entity.jpa.ArticleLike;

/**
 * @ClassName ArticleLikeWebService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/25 10:13
 */
public interface ArticleLikeWebService {
    ArticleLike like(String aid, String uid);
}
