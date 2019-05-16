package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.service.CourseExtWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName CourseExtController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:55
 */
@RestController
@RequestMapping(value = "courseExt/web")
public class CourseExtWebController extends BaseController {


    @Autowired
    private CourseExtWebService courseExtWebService;

    /**
     * URL：192.168.1.185：8081/gwzz/courseExt/list
     * @param courseId 课程ID
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public Message list(@RequestParam String courseId){
        msg = Message.success();
        msg.setRecode(0);
        msg.setData(courseExtWebService.getCourseExt(courseId));
        return msg;
    }


}
