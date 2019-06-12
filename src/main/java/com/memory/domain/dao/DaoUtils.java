package com.memory.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program parent
 * @Author: cui.Memory
 * @Date: 2018/11/29 21:14
 * @description:
 */
@Component
public class DaoUtils {

    @Autowired
    private EntityManager em;
    private Page page;

    public Object save(Object obj){
        em.merge(obj);
        return obj;
    }
    public Object del(Object obj){
        em.detach(obj);
        return obj;
    }

    public Object getById(String className, String id){
        StringBuffer sb=new StringBuffer(" from "+className+" where id=:id");
        Map<String, Object> getByIdMap=new HashMap<>();
        getByIdMap.put("id", id);
        Object obj=this.findObjectHQL(sb.toString(), getByIdMap);
        return obj;
    }

    /**
     * HQL语句查询结果集
     * @param hql HQL查询语句
     * @param map 查询条件MAP
     * @return
     */
    public List findByHQL(String hql, Map<String, Object> map, Page page){
        Query query = getHQLQuery(hql, map);
        if(page != null){
            query.setFirstResult(page.getStart());
            query.setMaxResults(page.getLimit());
        }
        return query.getResultList();
    }

    /**
     * SQL语句查询结果集
     * @param sql SQL查询语句
     * @param map 查询条件MAP
     * @return
     */
    public List findBySQL(String sql, Map<String, Object> map, Page page, Class cla){
        Query query = getSQLQuery(sql, map, cla);
        //query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if(page != null){
            query.setFirstResult(page.getStart());
            query.setMaxResults(page.getLimit());
        }
        return query.getResultList();
    }

    /**
     * HQL查询记录总数
     * @param hql
     * @param map
     * @return
     */
    public int getTotalByHQL(String hql, Map map) {
        int total = 0;
        Query query = getHQLQuery(hql, map);
        total = Integer.parseInt(query.getResultList().get(0).toString());
        return total;
    }

    /**
     * SQL查询记录总数
     * @param sql
     * @param map
     * @return
     */
    public int getTotalBySQL(String sql, Map map) {
        int total = 0;
        Query query = getSQLQuery(sql, map, null);
        total = Integer.parseInt(query.getResultList().get(0).toString());
        return total;
    }

    /**
     * 执行一条HQL并返回受影响行数
     * @param hql
     * @param map
     * @return
     */
    public int excuteHQL(String hql, Map map) {
        int rows = 0;
        Query query = getHQLQuery(hql, map);
        rows = query.executeUpdate();
        return rows;
    }

    /**
     * 执行一条SQL并返回受影响行数
     * @param sql
     * @param map
     * @return
     */
    public int excuteSQL(String sql, Map map) {
        int rows = 0;
        Query query = getSQLQuery(sql, map, null);
        rows = query.executeUpdate();
        return rows;
    }

    /**
     * HQL查询并返回对象
     * @param hql
     * @param map
     * @return
     */
    public Object findObjectHQL(String hql, Map map) {
        Query query = getHQLQuery(hql, map);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //获取HQLQuery
    private Query getHQLQuery(String hql, Map<String, Object> map){
        Query query = em.createQuery(hql);
        if(map != null){
            for (Map.Entry<String, Object> entry : map.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query;
    }
    //获取SQLQuery
    private Query getSQLQuery(String sql, Map<String, Object> map, Class cla){
        Query query = null;
        if(cla != null){
            query = em.createNativeQuery(sql, cla);
        }else{
            query = em.createNativeQuery(sql);
        }
        if(map != null){
            for (Map.Entry<String, Object> entry : map.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query;
    }

    public Page getPage(Integer pageIndex, Integer limit){
        if(page == null){
            page = new Page();
        }
        if(pageIndex == null || pageIndex == 0)
            pageIndex = 1;
        if(limit == null || limit == 0)
            limit = 10;
        page.setPageIndex(pageIndex);
        page.setLimit(limit);
        return page;
    }
    public class Page{
        private int start = 0;
        private int limit = 10;
        private int pageIndex = 1;

        public Page(){}

        public Page(int pageIndex, int limit) {
            this.pageIndex = pageIndex;
            this.limit = limit;
        }

        public int getStart() {
            return start=(pageIndex-1)*limit;
        }


        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }
    }
}
