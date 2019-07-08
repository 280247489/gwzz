package com.memory.cms.controller;

import com.memory.cms.service.FeedbackCmsService;
import com.memory.common.utils.PageResult;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.entity.jpa.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/7/3 13:54
 */
@RestController
@RequestMapping("feedback/cms/")
public class FeedbackCmsController {
    private static final Logger log = LoggerFactory.getLogger(FeedbackCmsController.class);

    @Autowired
    private FeedbackCmsService feedbackCmsService;

    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                       @RequestParam String feedbackName, @RequestParam String feedbackContactUs,@RequestParam String feedbackType){
        Result result = new Result();
        try {
            int pageIndex = page+1;
            int limit = size;
            List<Feedback> list = feedbackCmsService.queryFeedbackByQue(pageIndex,size,feedbackName,feedbackContactUs,feedbackType);
            int totalElements = feedbackCmsService.queryFeedbackCountByQue(feedbackName,feedbackContactUs,feedbackType);
            PageResult pageResult = PageResult.getPageResult(page, size, list, totalElements);
            result= ResultUtil.success(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            log.error("/feedback/cms/list",e.getMessage());
        }
        return result;
    }

    @RequestMapping("detail")
    public Result detail(@RequestParam String id){
        Result result = new Result();
        try {
            Feedback feedback = feedbackCmsService.queryFeedbackById(id);
            result = ResultUtil.success(feedback);
        }catch (Exception e){
            e.printStackTrace();
            log.error("/feedback/cms/detail",e.getMessage());
        }
        return result;
    }



}
