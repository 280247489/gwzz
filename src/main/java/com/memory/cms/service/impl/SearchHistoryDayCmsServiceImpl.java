package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.SearchHistoryDayCmsService;
import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.SearchHistoryDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/7/22 14:55
 */
@Service
public class SearchHistoryDayCmsServiceImpl implements SearchHistoryDayCmsService {

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public List querySearchHistoryDayByQue(int pageIndex,int limit,String startTime, String endTime, String searchType) {
        StringBuffer sb = new StringBuffer();
        Map<String,Object> map = new HashMap<String, Object>();
        sb.append(" select s.time,s.key_word,sum(s.sums) as total_sum from search_history_day  s  where 1=1 ");

       if(Utils.isNotNull(searchType)){
            sb.append(" AND s.search_type = :searchType");
            map.put("searchType",searchType);
        }

        sb.append(" AND s.time between :startTime and :endTime ");
        map.put("startTime",startTime);
        map.put("endTime",endTime);


        sb.append(" GROUP BY s.key_word ORDER BY total_sum desc");
        DaoUtils.Page page = daoUtils.getPage(pageIndex, limit);
        System.out.println("sql == " + sb.toString());
        System.out.println("map ==" + map.toString());
        System.out.println("Json return is " + JSON.toJSONString(daoUtils.findBySQL(sb.toString(),map,page,null)));

        return  daoUtils.findBySQL(sb.toString(),map,page,null);
    }



    @Override
    public int querySearchHistoryDayCountByQue(String startTime, String endTime, String searchType) {
        StringBuffer sb = new StringBuffer();
        Map<String,Object> map = new HashMap<String, Object>();
        sb.append("select count(*) from ( ");
        sb.append(" select count(*),sum(s.sums) as total_sum from search_history_day  s  where 1=1 ");

        if(Utils.isNotNull(searchType)){
            sb.append(" AND s.search_type = :searchType");
            map.put("searchType",searchType);
        }

        sb.append(" AND s.time between :startTime and :endTime ");
        map.put("startTime",startTime);
        map.put("endTime",endTime);

        sb.append(" GROUP BY s.key_word");
        sb.append(" ) T");
        System.out.println("sql ============="+sb.toString());

        return  daoUtils.getTotalBySQL(sb.toString(),map);
    }


}
