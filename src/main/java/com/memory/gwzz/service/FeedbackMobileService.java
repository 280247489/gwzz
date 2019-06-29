package com.memory.gwzz.service;

import com.memory.entity.jpa.Feedback;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName FeedbackMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/28 11:04
 */
public interface FeedbackMobileService {
    Feedback add(String userId, String feedbackType, String feedbackContent, String feedbackName, String feedbackContactUs, List<MultipartFile> files);
}
