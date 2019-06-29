package com.memory.gwzz.service;

import com.memory.entity.jpa.Feedback;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName FeedbackMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/28 11:04
 */
public interface FeedbackMobileService {
    Feedback add(String userId, String feedbackType, String feedbackContent, String feedbackName, String feedbackContactUs, HttpServletRequest request);
}
