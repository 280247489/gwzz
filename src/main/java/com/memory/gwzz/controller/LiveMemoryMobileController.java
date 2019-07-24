package com.memory.gwzz.controller;


import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import com.memory.entity.jpa.LiveMaster;
import com.memory.gwzz.service.LiveMemoryMobileService;
import com.memory.gwzz.service.LiveMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author INS6+
 * @date 2019/5/28 13:44
 */
@RestController
@RequestMapping(value="courseMemory/mobile")
public class LiveMemoryMobileController {

    @Autowired
    private LiveMemoryMobileService liveMemoryMobileService;

    @Autowired
    private LiveMobileService liveMobileService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addMemory(String masterId){
        Result result = new Result();
        try{

            LiveMaster master = liveMobileService.getLiveMasterById(masterId);
            if(Utils.isNotNull(master)){
                liveMemoryMobileService.addLiveMemory(masterId);
                result = ResultUtil.success( "课程缓存成功!");
            }else {
                result = ResultUtil.error(-1,"非法直播!" );
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result getCourseExtById(String courseId){
        Result result = new Result();
        try {
            result = ResultUtil.success( liveMemoryMobileService.getLiveSlaveById(courseId));

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result clear(String masterId){
        Result result = new Result();
        try {
            if(Utils.isNotNull(masterId)){
                liveMemoryMobileService.clear(masterId);
                result = ResultUtil.success( "移除"+masterId+"缓存成功");
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
            liveMemoryMobileService.clearAll();
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
            result = ResultUtil.success(liveMemoryMobileService.findAll());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }







}
