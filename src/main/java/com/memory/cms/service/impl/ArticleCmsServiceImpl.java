package com.memory.cms.service.impl;

import com.memory.cms.repository.ArticleCmsRepository;
import com.memory.cms.service.ArticleCmsService;
import com.memory.entity.jpa.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/8 10:56
 */
@Service
public class ArticleCmsServiceImpl implements ArticleCmsService {

    @Autowired
    private ArticleCmsRepository repository;


    @Override
    public List<Article> getArticleList() {
        return repository.findAll();
    }

    @Override
    public Article getArticleById(String id) {
        if(repository.findById(id).hashCode() != 0){
            return repository.findById(id).get();
        }else{
            return null;
        }

    }

    @Override
    public Article add(Article article) {
        return repository.save(article);
    }

    @Override
    public Article update(Article article) {
        return repository.save(article);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Article queryArticleByLoginName(String loginName) {
        return null;
    }

    @Override
    @Transactional
    public int updateArticleOnlineById(int online,String id) {

        return repository.updateArticleOnlineById(online,id);
    }

    @Override
    public Page<Painter> queryArticleByQue(Pageable pageable,String article_title,String article_update_id,Integer article_online,String sort_status,String type_id) {
        Specification specification =new Specification<Painter>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if(!"".equals(article_title)){
                    list.add(cb.like(root.get("articleTitle"),"%" + article_title + "%"));
                }

                if(!"".equals(article_update_id)){
                    list.add(cb.equal(root.get("articleUpdateId"),article_update_id));
                }

                if(article_online != null){
                    list.add(cb.equal(root.get("articleOnline"),article_online));
                }

                if(!"".equals(type_id)){
                    list.add(cb.equal(root.get("typeId"),type_id));
                }

                Predicate[] p = new Predicate[list.size()];
                query.where(cb.and(list.toArray(p)));

               if(sort_status != null && !"".equals(sort_status) && sort_status.equals("asc")){

                    query.orderBy(cb.asc(root.get("articleUpdateTime")));
                }else{
                   //默认根据更新时间倒叙排列
                    query.orderBy(cb.desc(root.get("articleUpdateTime")));
                }
                return query.getRestriction();
            }
        };

        return repository.findAll(specification,pageable);
    }


}

