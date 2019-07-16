package com.memory.gwzz.service;

import java.util.Map;

/**
 * @ClassName ArticleLikeMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/25 10:13
 */
public interface ArticleLikeMobileService {
    int like(String aid, String uid);
    Map<String,Object> ListArticleLikeByUserId(String userId, Integer start, Integer limit);

//    int isLike(String aid, String uid);
}
