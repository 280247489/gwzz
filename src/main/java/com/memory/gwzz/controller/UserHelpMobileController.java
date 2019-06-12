package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.service.UserHelpMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserHelpMobileController
 * @Descriotion TODO
 * @Author Ganxiqing 、
 * @Date 2019/6/10 15:44
 */
@RestController
@RequestMapping(value = "userHelp/mobile")
public class UserHelpMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(UserHelpMobileController.class);

    @Autowired
    private UserHelpMobileService userHelpMobileService;

    /**
     * 查询帮助中心内容
     * URL:192.168.1.185:8081/gwzz/userHelp/mobile/listUserHelp
     * @return Map《classProblem【问题分类】》 Map《commonProblem【常见问题】》
     *
     */
    @RequestMapping(value = "listUserHelp",method = RequestMethod.POST)
    public Message listUserHelp(){
        msg = Message.success();
        try {
            msg.setRecode(0);
            msg.setData(userHelpMobileService.listUserHelp());

        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            logger.error("异常信息");
        }
        return msg;
    }

}