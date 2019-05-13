package com.memory.gwzz.controller;

import com.memory.cms.entity.ExtModel;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/1
 * @Description:
 */
@RestController
public class DemoController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("test")
    @ResponseBody
    public Message test(ExtModel extList) {
        System.out.println(extList.getExtList().size());
        msg = Message.success();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.setMsg("访问成功");
        return msg;
    }
}