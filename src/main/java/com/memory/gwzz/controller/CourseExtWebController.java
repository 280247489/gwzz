package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.service.CourseExtWebService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName CourseExtController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:55
 */
@RestController
@RequestMapping(value = "courseExt")
public class CourseExtWebController extends BaseController {

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private CourseExtWebService courseExtWebService;
//    method = RequestMethod.GET
    @RequestMapping(value = "list")
    public Message list(@RequestParam String courseId){
        msg = Message.success();
        msg.setRecode(0);
        msg.setData(courseExtWebService.getCourseExt(courseId));
        return msg;
    }



    @RequestMapping(value = "aaa",method = RequestMethod.GET)
    public Message aaa(@RequestParam String courseId){
        redisUtil.hmset(CacheConstantConfig.COURSERXT+":"+courseId,courseExtWebService.getCourseExtMap(courseId));
        msg = Message.success();
        msg.setRecode(0);
        msg.setData(redisUtil.hmget(CacheConstantConfig.COURSERXT+":"+courseId));
        return msg;
    }


}
