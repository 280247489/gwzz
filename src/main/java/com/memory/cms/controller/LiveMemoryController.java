package com.memory.cms.controller;

import com.memory.cms.service.LiveMemoryService;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author INS6+
 * @date 2019/5/28 13:44
 */
@RestController
@RequestMapping(value="courseMemory")
public class LiveMemoryController {

    @Autowired
    private LiveMemoryService LIveMemoryService;



    @Autowired
    private RedisUtil redisUtil;



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addMemory(String masterId){
        Result result = new Result();
        try{
            LIveMemoryService.addLiveMemory(masterId);
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
            result = ResultUtil.success( LIveMemoryService.getLiveSlaveById(courseId));

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result clear(String courseId){
        Result result = new Result();
        try {
            if(Utils.isNotNull(courseId)){
                LIveMemoryService.clear(courseId);
                result = ResultUtil.success( "移除"+courseId+"缓存成功");
            }else{
                result = ResultUtil.error(-1,"非法id");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/clearAll", method = RequestMethod.POST)
    public Result clearAll(){
        Result result = new Result();
        try {
            LIveMemoryService.clearAll();
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
            result = ResultUtil.success(LIveMemoryService.findAll());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }







}
