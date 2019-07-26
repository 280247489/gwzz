package com.memory.gwzz.controller;


import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.entity.jpa.LiveMemoryLoad;
import com.memory.gwzz.service.LiveMemoryLoadMobileService;
import com.memory.gwzz.service.LiveMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/29 12:15
 */
@RestController
@RequestMapping(value="liveMemoryLoad/mobile")
public class LiveMemoryLoadMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(LiveMemoryLoadMobileController.class);

    @Autowired
    private LiveMemoryLoadMobileService liveMemoryLoadMobileService;

    @Autowired
    private LiveMobileService liveMobileService;

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Message findAll(){
        try {
            msg = Message.success();
            List<Map<String,Object>> returnMap = new ArrayList<Map<String,Object>>();
            Map<String ,Object> mapper = new HashMap<String ,Object>();
            //List<LiveMaster> options = liveMobileService.queryListMasterOptions();
            List<com.memory.gwzz.model.LiveMaster>  optionsList = liveMobileService.queryLiveMasterOptions();
            for (com.memory.gwzz.model.LiveMaster master : optionsList) {
                mapper.put(master.getId(),master.getLiveMasterName());
            }

            List<LiveMemoryLoad> list=  liveMemoryLoadMobileService.queryAllLiveMemoryLoadByLoadStatus(0);
            for (LiveMemoryLoad liveMemoryLoad : list) {
                Map<String,Object> map = new HashMap<>();
                map.put("liveId",liveMemoryLoad.getId());
                map.put("content",liveMemoryLoad.getContent());
                map.put("operator",liveMemoryLoad.getOperator());
                map.put("loadStatus",liveMemoryLoad.getLoadStatus());
                map.put("createTime",liveMemoryLoad.getCreateTime());
                map.put("updateTime",liveMemoryLoad.getUpdateTime());
                map.put("courseRedisKey",liveMemoryLoad.getLiveRedisKey());
                map.put("titleName",mapper.get(liveMemoryLoad.getId()));
                returnMap.add(map);

            }
            msg.setRecode(0);
            msg.setData(returnMap);
        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
        }
        return msg;
    }

}
