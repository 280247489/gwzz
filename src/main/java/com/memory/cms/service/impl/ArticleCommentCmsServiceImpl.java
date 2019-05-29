package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.repository.ArticleCommentCmsRepository;
import com.memory.cms.service.ArticleCommentCmsService;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.bean.ArticleComment;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.swing.*;
import java.util.*;

/**
 * @author INS6+
 * @date 2019/5/23 17:01
 */
@Service
public class ArticleCommentCmsServiceImpl implements ArticleCommentCmsService {

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private ArticleCommentCmsRepository repository;


    @Override
    public int queryArticleCommentByQueHqlCount(String key_words, String phone_number, String article_name, String user_name, Integer comment_type, String query_start_time, String query_end_time, Integer sort_role,String comment_root_id) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT count(*) " +
                "FROM ArticleComment  ac , Article a ,User us " +
                "WHERE ac.articleId = a.id  and ac.userId = us.id ");
        Map<String,Object> whereClause = getWhereClause(key_words, phone_number, article_name, user_name, comment_type, query_start_time, query_end_time, sort_role,comment_root_id);
        stringBuffer.append(whereClause.get("where"));
        Map<String,Object> map = (  Map<String,Object>) whereClause.get("param");

        return daoUtils.getTotalByHQL(stringBuffer.toString(),map);
    }

    @Override
    public List<ArticleComment> queryArticleCommentByQueHql(int pageIndex,int limit,String key_words,String phone_number, String article_name, String user_name, Integer comment_type, String query_start_time, String query_end_time, Integer sort_role,String comment_root_id) {
        List<ArticleComment>  list =new ArrayList<ArticleComment>();
        StringBuffer stringBuffer = new StringBuffer();
        //    private String commentParentId;
        //    private String commentParentUserName;
        stringBuffer.append("SELECT new com.memory.entity.bean.ArticleComment(ac.id, ac.userName, us.userTel, a.articleTitle, ac.commentContent,ac.commentRootId , ac.commentTotalLike, (select count(*) from ArticleComment WHERE commentRootId = ac.commentRootId) as commentSum," +
                "ac.commentCreateTime,ac.commentType,ac.commentParentId,ac.commentParentUserName) " +
                                "FROM ArticleComment  ac , Article a ,User us " +
                                "WHERE ac.articleId = a.id  and ac.userId = us.id ");

        DaoUtils.Page page = daoUtils.getPage(pageIndex, limit);
        Map<String,Object> whereClause = getWhereClause(key_words, phone_number, article_name, user_name, comment_type, query_start_time, query_end_time, sort_role,comment_root_id);

        stringBuffer.append(whereClause.get("where"));
        Map<String,Object> map = (  Map<String,Object>) whereClause.get("param");
        System.out.println("where ==========="+whereClause.get("where").toString());
        System.out.println("hql ============= " + stringBuffer.toString() );
        System.out.println("map ============= " + JSON.toJSONString(map));
        list= daoUtils.findByHQL(stringBuffer.toString(), map, page);

        return list;
    }

    /**
     * 文章评论 动态查询条件
     * @param key_words
     * @param phone_number
     * @param article_name
     * @param user_name
     * @param comment_type
     * @param query_start_time
     * @param query_end_time
     * @param sort_role
     * @return
     */
    public Map<String,Object> getWhereClause(String key_words,String phone_number, String article_name, String user_name, Integer comment_type, String query_start_time, String query_end_time, Integer sort_role,String comment_root_id){
        Map<String,Object> returnMap = new HashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<String, Object>();

            //评论关键字 模糊查询
            if(!"".equals(key_words)){
                stringBuffer.append(" AND ac.commentContent like :commentContent  ");
                paramMap.put("commentContent",'%'+ key_words+'%');
            }

            //课程名称 模糊查询
            if(!"".equals(article_name)){
                stringBuffer.append(" AND a.articleTitle like :articleTitle  ");
                paramMap.put("articleTitle",'%'+ article_name+'%');
            }

           //用户昵称 模糊查询
            if(!"".equals(user_name)){
                stringBuffer.append(" AND ac.userName like :userName  ");
                paramMap.put("userName",'%'+ user_name+'%');
            }

            //手机号 模糊查询
            if(!"".equals(phone_number)){
                stringBuffer.append(" AND us.userTel like :userTel  ");
                paramMap.put("userTel",'%'+ phone_number+'%');
            }

            //评论类型 0 评论 1 评论回复
            if(comment_type != null){
                stringBuffer.append(" AND ac.commentType = :commentType  ");
                paramMap.put("commentType", comment_type);
            }

            if(!"".equals(comment_root_id )){
                stringBuffer.append(" AND ac.commentRootId = :commentRootId  ");
                stringBuffer.append(" AND ac.commentParentId <> ''  ");
                paramMap.put("commentRootId", comment_root_id);

            }


            //筛选时间
            if(!"".equals(query_start_time) && !"".equals(query_end_time)){
                stringBuffer.append(" AND ac.commentCreateTime between "+query_start_time+" and  " +query_end_time);
            }else{

                if(!"".equals(query_start_time)){

                    String yhmTime = query_start_time.substring(0,10);
                    String lastTime = yhmTime + " 23:59:59";
                    stringBuffer.append(" AND ac.commentCreateTime between "+query_start_time+" and  " +lastTime);
                }

                if(!"".equals(query_end_time)){

                    String yhmTime = query_end_time.substring(0,10);
                    String firstTime = yhmTime + " 00:00:00";
                    stringBuffer.append(" AND ac.commentCreateTime between "+firstTime+" and  " +query_end_time);
                }

            }

            //排序规则  0 时间倒叙 1 时间正序 2 评论点赞数量倒叙 3评论点赞数量正序
            if(sort_role != null){
                if(sort_role == 0){
                    stringBuffer.append(" ORDER BY ac.commentCreateTime DESC");
                }

                if(sort_role == 1){
                    stringBuffer.append(" ORDER BY ac.commentCreateTime ASC");
                }

                if(sort_role == 2){
                    stringBuffer.append(" ORDER BY ac.commentTotalLike DESC");
                }

                if(sort_role == 3){
                    stringBuffer.append(" ORDER BY ac.commentTotalLike ASC");
                }

            }else{
                //默认按照时间倒叙
                stringBuffer.append(" ORDER BY ac.commentCreateTime DESC");
            }

        returnMap.put("where",stringBuffer.toString());
        returnMap.put("param",paramMap);
        return returnMap;
    }


    @Override
    public Page<Painter> queryArticleCommentByQue(Pageable pageable, String key_words, String phone_number, String article_name, String user_name, Integer comment_type, String query_start_time, String query_end_time, Integer sort_role) {
        return null;
    }

    @Override
    public com.memory.entity.jpa.ArticleComment addArticleComment(com.memory.entity.jpa.ArticleComment articleComment) {
       return repository.save(articleComment);
    }

    @Override
    public com.memory.entity.jpa.ArticleComment updateArticleComment(com.memory.entity.jpa.ArticleComment articleComment) {
        return repository.save(articleComment);
    }

    @Override
    public List<com.memory.entity.jpa.ArticleComment> queryArticleCommentList(String comment_root_id, Date comment_create_time ) {
        return repository.queryArticleCommentList(comment_root_id,comment_create_time);
    }

    @Override
    public com.memory.entity.jpa.ArticleComment getArticleCommentById(String id) {
        return repository.getArticleCommentById(id);
    }

    @Override
    public void deleteAll(List<com.memory.entity.jpa.ArticleComment> list) {
         repository.deleteAll(list);
    }

    @Override
    public void deleteArticleCommentByCommentRootId(String root_id) {
        repository.deleteArticleCommentByCommentRootId(root_id);
    }
}
