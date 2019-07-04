package com.memory.gwzz.service.impl;

import com.memory.common.utils.FileUploadUtil;
import com.memory.common.utils.Utils;
import com.memory.entity.jpa.Feedback;
import com.memory.gwzz.repository.FeedbackMobileRepository;
import com.memory.gwzz.service.FeedbackMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Transactional
    @Override
    public synchronized Feedback add(String userId, String feedbackType, String feedbackContent, String feedbackName, String feedbackContactUs, List<MultipartFile> files){
        Feedback feedback = new Feedback();
        String id = Utils.generateUUIDs();
        feedback.setId(id);
        feedback.setUserId(userId);
        feedback.setFeedbackType(feedbackType);
        feedback.setFeedbackContent(feedbackContent);
        feedback.setFeedbackName(feedbackName);
        feedback.setFeedbackContactUs(feedbackContactUs);
        if(files != null){
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i = 0; i < files.size(); i++) {
                String path =fileUploadUtil .upload2PNG(i+"_"+Utils.getShortUUTimeStamp(), "gwzz_file/feedback/"+id, files.get(i));
                stringBuffer.append(path+",");
            }
          feedback.setFeedbackImg(stringBuffer.substring(0, stringBuffer.length()-1));
        }else{
            feedback.setFeedbackImg("");
        }
        feedback.setFeedbackCreateTime(new Date());
        feedbackMobileRepository.save(feedback);

        return feedback;
    }
}
