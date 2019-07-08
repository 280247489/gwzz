package com.memory.cms.service;

import com.memory.entity.jpa.Feedback;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/7/3 13:55
 */

public interface FeedbackCmsService {

    List<Feedback> queryFeedbackByQue(Integer pageIndex,Integer limit,String feedbackName,String feedbackContactUs,String feedbackType);

    int queryFeedbackCountByQue(String feedbackName,String feedbackContactUs,String feedbackType);

    Feedback queryFeedbackById(String id);



}
