package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.Feedback;
import com.memory.gwzz.service.FeedbackMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName FeedbackMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/29 10:39
 */
@RestController
@RequestMapping(value = "feedback/mobile")
public class FeedbackMobileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(FeedbackMobileController.class);
    @Autowired
    private FeedbackMobileService feedbackMobileService;

    /**
     * 添加用户反馈
     * URL:192.168.1.185:8081/gwzz/feedback/mobile/add
     * @param userId String 用户Id
     * @param feedbackType String 反馈类型
     * @param feedbackContent String 反馈内容
     * @param feedbackName String 反馈人
     * @param feedbackContactUs String 联系电话
     * @param files 反馈图片
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Message add(@RequestParam String userId, @RequestParam String feedbackType, @RequestParam String feedbackContent,
                       @RequestParam String feedbackName, @RequestParam String feedbackContactUs, @RequestParam List<MultipartFile> files){
        try {
            msg = Message.success();
            Feedback feedback = feedbackMobileService.add(userId, feedbackType, feedbackContent, feedbackName, feedbackContactUs, files);
            msg.setRecode(0);
            msg.setData(feedback);
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息"+e.toString());
            e.printStackTrace();
        }
        return msg;
    }

}
