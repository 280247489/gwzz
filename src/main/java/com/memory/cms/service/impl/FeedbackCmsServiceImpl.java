package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.repository.FeedbackCmsRepository;
import com.memory.cms.service.FeedbackCmsService;
import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/7/3 13:56
 */
@Service
public class FeedbackCmsServiceImpl implements FeedbackCmsService {

    @Autowired
    private FeedbackCmsRepository repository;

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public List<Feedback> queryFeedbackByQue(Integer pageIndex, Integer limit, String feedbackName, String feedbackContactUs, String feedbackType) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" FROM Feedback f where 1=1 ");
        Map<String,Object> whereClause = getWhereClause(feedbackName,feedbackContactUs,feedbackType);
        stringBuffer.append(whereClause.get("where"));
        Map<String,Object> map = (  Map<String,Object>) whereClause.get("param");
        DaoUtils.Page page = daoUtils.getPage(pageIndex, limit);
        System.out.println("hql ============= " + stringBuffer.toString() );
        System.out.println("map ============= " + JSON.toJSONString(map));
        return daoUtils.findByHQL(stringBuffer.toString(), map, page);
    }

    @Override
    public int queryFeedbackCountByQue(String feedbackName, String feedbackContactUs, String feedbackType) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" SELECT count(*) FROM Feedback f where 1=1 ");
        Map<String,Object> whereClause = getWhereClause(feedbackName,feedbackContactUs,feedbackType);
        stringBuffer.append(whereClause.get("where"));
        Map<String,Object> map = (  Map<String,Object>) whereClause.get("param");

        return daoUtils.getTotalByHQL(stringBuffer.toString(),map);
    }

    @Override
    public Feedback queryFeedbackById(String id) {
        if(repository.findById(id).hashCode() != 0){
            return repository.findById(id).get();
        }else{
            return null;
        }
    }

    public Map<String,Object> getWhereClause(String feedbackName, String feedbackContactUs, String feedbackType){
        Map<String,Object> returnMap = new HashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        if(Utils.isNotNull(feedbackName)){
            stringBuffer.append(" AND f.feedbackType like :feedbackType  ");
            paramMap.put("feedbackType",'%'+ feedbackType+'%');
        }

        if(Utils.isNotNull(feedbackContactUs)){
            stringBuffer.append(" AND f.feedbackContactUs like :feedbackContactUs  ");
            paramMap.put("feedbackContactUs",'%'+ feedbackContactUs+'%');
        }

        if(Utils.isNotNull(feedbackType)){
            stringBuffer.append(" AND f.feedbackType = :feedbackType  ");
            paramMap.put("feedbackType",feedbackType);
        }


        stringBuffer.append(" ORDER BY f.feedbackCreateTime DESC");

        returnMap.put("where",stringBuffer.toString());
        returnMap.put("param",paramMap);
        return returnMap;
    }

}
