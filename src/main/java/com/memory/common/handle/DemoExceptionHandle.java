package com.memory.common.handle;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/2 13:07
 * @Description: 全局异常捕获类
 */
@ControllerAdvice
public class DemoExceptionHandle extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(DemoExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Message exceptionHandle(Exception e) {
        msg = Message.error();
        msg.setMsg(e.toString());
        logger.error("***异常***\t"+e);
        return msg;
    }
}
