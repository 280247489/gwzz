package com.memory.gwzz.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.gwzz.model.Article;
import com.memory.gwzz.service.ArticleMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/18 11:14
 */
@Service("articleMobileService")
public class ArticleMobileServiceImpl implements ArticleMobileService {

    @Autowired
    private DaoUtils daoUtils;

    public Map<String,Object> findArticleByKey(Integer start,Integer limit, String key){
        Map<String,Object> returnMap = new HashMap<>();
        //查询最新5篇养生文章  文章类型Id：tYmvO0Ub1558922279863
        StringBuffer sbArticle = new StringBuffer( "SELECT NEW com.memory.gwzz.model.Article( id, typeId, articleTitle, articleLogo1, articleLogo2, articleLogo3, " +
                "articleLabel, articleOnline, articleTotalComment, articleTotalView, articleReleaseTime)  " +
                "FROM Article WHERE articleOnline = 1 AND typeId = 'tYmvO0Ub1558922279863' ");

        StringBuffer sbArticleCount = new StringBuffer( "SELECT COUNT(*) FROM article WHERE article_online = 1 AND type_id = 'tYmvO0Ub1558922279863' ");
        Map<String, Object> map = new HashMap<String, Object>();
        if (!"".equals(key)&& key!=null){
            sbArticle.append(" OR articleTitle LIKE :articleTitle");
            sbArticleCount.append(" OR article_title LIKE :articleTitle");
            map.put("articleTitle", "%"+key+"%");
        }
        sbArticle.append(" ORDER BY articleReleaseTime DESC");
        DaoUtils.Page pageArticle = new DaoUtils.Page();
        pageArticle.setPageIndex(start);
        pageArticle.setLimit(limit);
        List<Article> articleList = daoUtils.findByHQL(sbArticle.toString(),map,pageArticle);
        Integer articleCount = daoUtils.getTotalBySQL(sbArticleCount.toString(),map);

        returnMap.put("articleList",articleList);
        returnMap.put("articleCount",articleCount);

        return returnMap;
    }

}
