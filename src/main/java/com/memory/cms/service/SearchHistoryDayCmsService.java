package com.memory.cms.service;

import com.memory.entity.jpa.SearchHistoryDay;

import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/7/22 14:55
 */

public interface SearchHistoryDayCmsService {


     List querySearchHistoryDayByQue(int pageIndex, int limit, String startTime, String endTime, String searchType);

    int querySearchHistoryDayCountByQue(String startTime, String endTime, String searchType);
}
