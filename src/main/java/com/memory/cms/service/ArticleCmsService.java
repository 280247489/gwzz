package com.memory.cms.service;

import com.memory.entity.jpa.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.*;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/8 10:56
 */

public interface ArticleCmsService {

    List<Article> getArticleList();

    Article getArticleById(String id);

    Article add(Article Article);

    Article update(Article Article);

    void delete(String id);

    Article queryArticleByLoginName(String loginName);

    int updateArticleOnlineById(int online,String id);

    Page<Painter> queryArticleByQue(Pageable pageable,String article_title,String article_update_id,Integer article_online,String sort_status,String type_id);


}
