package com.memory.gwzz.service;

import java.util.Map;

/**
 * @ClassName ArticleMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/18 11:15
 */
public interface ArticleMobileService {
    Map<String,Object> findArticleByKey(Integer start, Integer limit, String key);
}
