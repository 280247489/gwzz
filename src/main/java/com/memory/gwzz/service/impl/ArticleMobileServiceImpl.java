package com.memory.gwzz.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.gwzz.model.Article;
import com.memory.gwzz.service.ArticleMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public Map<String,Object> listArticleByKey(Integer start, Integer limit, String key){
        Map<String,Object> returnMap = new HashMap<>();
        StringBuffer sbArticle = new StringBuffer( "SELECT  id, type_id, article_title, article_logo1, article_logo2, article_logo3, " +
                "article_label,article_key_words, article_online, article_total_comment, article_total_view, article_release_time " +
                "FROM article WHERE article_online = 1 AND type_id = 'tYmvO0Ub1558922279863' ");

        StringBuffer sbArticleCount = new StringBuffer( "SELECT COUNT(*) FROM article WHERE article_online = 1 AND type_id = 'tYmvO0Ub1558922279863' ");
        Map<String, Object> map = new HashMap<String, Object>();
        if (!"".equals(key)&& key!=null){
            sbArticle.append(" OR article_title LIKE :article_title");
            sbArticle.append(" OR article_label LIKE :article_label");
            sbArticle.append(" OR article_key_words LIKE :article_key_words");

            sbArticleCount.append(" OR article_title LIKE :article_title");
            sbArticleCount.append(" OR article_label LIKE :article_label");
            sbArticleCount.append(" OR article_key_words LIKE :article_key_words");
            map.put("article_title", "%"+key+"%");
            map.put("article_label", "%"+key+"%");
            map.put("article_key_words", "%"+key+"%");
        }
        sbArticle.append(" ORDER BY article_release_time DESC");
        DaoUtils.Page pageArticle = new DaoUtils.Page();
        pageArticle.setPageIndex(start);
        pageArticle.setLimit(limit);
        List<Object[]> articleList = daoUtils.findBySQL(sbArticle.toString(),map,pageArticle,null);
        List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < articleList.size(); i++) {
            Map<String, Object> objMap=new HashMap<String, Object>();
            objMap.put("id", articleList.get(i)[0]);
            objMap.put("typeId", articleList.get(i)[1]);
            objMap.put("article_title", articleList.get(i)[2]);
            objMap.put("article_logo1", articleList.get(i)[3]);
            objMap.put("article_logo2", articleList.get(i)[4]);
            objMap.put("article_logo3", articleList.get(i)[5]);
            objMap.put("article_label", articleList.get(i)[6]);
            objMap.put("article_key_words", articleList.get(i)[7]);
            objMap.put("article_online", articleList.get(i)[8]);
            objMap.put("article_total_comment", articleList.get(i)[9]);
            objMap.put("article_total_view", articleList.get(i)[10]);
            objMap.put("article_release_time", articleList.get(i)[11]);

            returnList.add(objMap);
        }
        Integer articleCount = daoUtils.getTotalBySQL(sbArticleCount.toString(),map);

        returnMap.put("articleList",returnList);
        returnMap.put("articleCount",articleCount);

        return returnMap;
    }

}
