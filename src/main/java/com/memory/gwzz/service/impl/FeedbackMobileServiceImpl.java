package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.entity.jpa.Feedback;
import com.memory.gwzz.repository.FeedbackMobileRepository;
import com.memory.gwzz.service.FeedbackMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName FeedbackMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/28 11:04
 */
@Service("feedbackMobileService")
public class FeedbackMobileServiceImpl implements FeedbackMobileService {
    @Autowired
    private FeedbackMobileRepository feedbackMobileRepository;

    @Value(value = "${filePath}")
    private String filePath;
    @Value(value = "${fileUrl}")
    private String fileUrl;

    @Transactional
    @Override
    public Feedback add(String userId, String feedbackType, String feedbackContent, String feedbackName, String feedbackContactUs, HttpServletRequest request){
        Feedback feedback = new Feedback();
        String id = Utils.generateUUIDs();
        feedback.setId(id);
        feedback.setUserId(userId);
        feedback.setFeedbackType(feedbackType);
        feedback.setFeedbackContent(feedbackContent);
        feedback.setFeedbackName(feedbackName);
        feedback.setFeedbackContactUs(feedbackContactUs);



        feedbackMobileRepository.save(feedback);

        return feedback;
    }
}
