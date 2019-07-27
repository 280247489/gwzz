package com.memory.cms.controller;

import com.memory.cms.service.SearchHistoryDayCmsService;
import com.memory.common.utils.PageResult;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


/**
 * @author INS6+
 * @date 2019/7/22 14:55
 */
@RestController
@RequestMapping("searchHistoryDay/cms")
public class SearchHistoryDayCmsController {

    private static final Logger log = LoggerFactory.getLogger(SearchHistoryDayCmsController.class);

    @Autowired
    private SearchHistoryDayCmsService searchHistoryDayCmsService;


    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam String startTime,@RequestParam String endTime,@RequestParam String searchType){
        Result result = new Result();
        try {
            List<com.memory.entity.bean.SearchHistoryDay> resultList = new ArrayList<com.memory.entity.bean.SearchHistoryDay>();
            int pageIndex =page +1;

            List list = searchHistoryDayCmsService.querySearchHistoryDayByQue(pageIndex,limit,startTime,endTime,searchType);
            int totalElements = searchHistoryDayCmsService.querySearchHistoryDayCountByQue(startTime, endTime, searchType);
            Object [] obj = list.toArray();
            for (int i =0;i<obj.length;i++){
                Object [] obj1 = (Object[]) obj[i];
                com.memory.entity.bean.SearchHistoryDay historyDay =new com.memory.entity.bean.SearchHistoryDay();
                historyDay.setTimeHMD(startTime+" 至 "+endTime);
                historyDay.setKeyWord(obj1[1].toString());
                historyDay.setTotal(Long.valueOf(obj1[2].toString()));
                resultList.add(historyDay);
            }

            PageResult pageResult = PageResult.getPageResult(page, limit, list, totalElements);
            result = ResultUtil.success(pageResult);

        }catch (Exception e){
            e.printStackTrace();
            log.error("route",e.getMessage());
        }
        return result;
    }


}
