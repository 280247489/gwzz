package com.memory.gwzz.controller;


import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import com.memory.entity.jpa.LiveMaster;
import com.memory.gwzz.service.LiveMemoryMobileService;
import com.memory.gwzz.service.LiveMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LiveMemoryMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(LiveMemoryMobileController.class);

    @Autowired
    private LiveMemoryMobileService liveMemoryMobileService;

    @Autowired
    private LiveMobileService liveMobileService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Message addMemory(String masterId){
        try{
            msg = Message.success();
            LiveMaster master = liveMobileService.getLiveMasterById(masterId);
            if(Utils.isNotNull(master)){
                liveMemoryMobileService.addLiveMemory(masterId);
                msg.setRecode(0);
                msg.setMsg("课程缓存成功!");
            }else {
                msg.setRecode(1);
                msg.setMsg("非法直播!");
            }
        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Message getCourseExtById(String courseId){
        try {
            msg = Message.success();
            msg.setData(liveMemoryMobileService.getLiveSlaveById(courseId));
        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Message clear(String masterId){
        try {
            msg = Message.success();
            if(Utils.isNotNull(masterId)){
                liveMemoryMobileService.clear(masterId);
                msg.setRecode(0);
                msg.setMsg("移除"+masterId+"缓存成功");
            }else{
                msg.setRecode(1);
                msg.setMsg("非法id");
            }

        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/clearAll", method = RequestMethod.POST)
    public Message clearAll(){
        try {
            msg = Message.success();
            liveMemoryMobileService.clearAll();
            msg.setRecode(0);
            msg.setMsg("移除全部缓存成功");

        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
        }
        return msg;
    }


    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Message findAll(){
        try{
            msg = Message.success();
            msg.setRecode(0);
            msg.setData(liveMemoryMobileService.findAll());
        }catch (Exception e){
            msg= Message.error();
            e.printStackTrace();
        }
        return msg;
    }

}
