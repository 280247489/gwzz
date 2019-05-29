package com.memory.cms.controller;

import com.memory.cms.service.CourseMemoryLoadService;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.entity.jpa.CourseMemoryLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/29 12:15
 */
@RestController
@RequestMapping(value="courseMemoryLoad")
public class CourseMemoryLoadController {

    @Autowired
    private CourseMemoryLoadService courseMemoryLoadService;

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(){
        Result result = new Result();
        try {
            List<CourseMemoryLoad> list=  courseMemoryLoadService.queryAllCourseMemoryLoadByLoadStatus(0);
            result = ResultUtil.success(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }





}
