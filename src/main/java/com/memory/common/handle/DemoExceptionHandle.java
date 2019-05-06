package com.memory.common.handle;

import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
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
public class DemoExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(DemoExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandle(Exception e) {
        logger.error("***异常***\t"+e);
        return ResultUtil.error(100, e.toString());
    }
}
