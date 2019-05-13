package com.memory.gwzz.controller;

import com.alibaba.fastjson.JSON;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.entity.CourseExt;
import com.memory.gwzz.service.CourseExtService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @ClassName CourseExtController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:55
 */
@RestController
@RequestMapping(value = "courseExt")
public class CourseExtController extends BaseController {

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private CourseExtService courseExtService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Message list(@RequestParam String courseId){
        msg = Message.success();
        courseExtService.setCourseExt(courseExtService.courseExtList(courseId));
//        redisUtil.del(CacheConstantConfig.COURSERXT+":"+courseId);
        msg.setRecode(0);
        msg.setData(redisUtil.lGet("1", 0, -1));
        return msg;
    }


}
