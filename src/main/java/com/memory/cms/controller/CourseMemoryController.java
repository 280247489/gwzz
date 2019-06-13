package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.CourseExtCmsService;
import com.memory.cms.service.CourseMemoryService;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.entity.jpa.CourseExt;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.memory.redis.CacheConstantConfig.SHARECOURSECONTENT;


/**
 * @author INS6+
 * @date 2019/5/28 13:44
 */
@RestController
@RequestMapping(value="courseMemory")
public class CourseMemoryController {

    @Autowired
    private CourseMemoryService courseMemoryService;

    @Autowired
    private CourseExtCmsService courseExtCmsService;

    @Autowired
    private RedisUtil redisUtil;



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addMemory(String courseId){
        Result result = new Result();
        try{
            courseMemoryService.addMemory(courseId);
            result = ResultUtil.success( "课程缓存成功!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result getCourseExtById(String courseId){
        Result result = new Result();
        try {
            result = ResultUtil.success( courseMemoryService.getCourseExtById(courseId));

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result clear(String courseId){
        Result result = new Result();
        try {
            courseMemoryService.clear(courseId);
            result = ResultUtil.success( "移除"+courseId+"缓存成功");

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/clearAll", method = RequestMethod.POST)
    public Result clearAll(){
        Result result = new Result();
        try {
            courseMemoryService.clearAll();
            result = ResultUtil.success( "移除全部缓存成功");

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(){
        Result result = new Result();
        try{
            result = ResultUtil.success(courseMemoryService.findAll());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "test")
    public void test(){
    try {
        List<CourseExt> extListSave =  courseExtCmsService.queryCourseExtByCourseId("uTzgHUow1558857333329");


        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < extListSave.size(); i++) {
            CourseExt courseExt1 = extListSave.get(i);
            Map<String, Object> mapObj = new HashMap<>();
            mapObj.put("courseExtNickname", courseExt1.getCourseExtNickname());
            mapObj.put("courseExtSort", courseExt1.getCourseExtSort());
            mapObj.put("courseExtType", courseExt1.getCourseExtType());
            if(courseExt1.getCourseExtType()==1){
                mapObj.put("courseExtWords", courseExt1.getCourseExtWords());
            }else if(courseExt1.getCourseExtType()==2){
                mapObj.put("courseExtAudio", courseExt1.getCourseExtAudio());
                mapObj.put("courseExtAudioTimes", courseExt1.getCourseExtAudioTimes());
            }else if(courseExt1.getCourseExtType()==3){
                mapObj.put("courseExtImgUrl", courseExt1.getCourseExtImgUrl());
            }
            resultList.add(mapObj);
        }


        String courseId ="uTzgHUow1558857333329";


        String keyHash =SHARECOURSECONTENT +courseId;
        redisUtil.hset(keyHash,"courseExt",JSON.toJSONString(resultList));
        redisUtil.hset(keyHash,"course","爱中医第328课：中医谈肺癌，能否打破“死神诅咒”？");



    }catch (Exception e){
        e.printStackTrace();
    }


    }






}
