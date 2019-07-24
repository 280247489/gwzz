package com.memory.cms.controller;

import com.memory.cms.service.LiveMemoryLoadService;
import com.memory.cms.service.LiveMasterCmsService;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.entity.jpa.LiveMemoryLoad;
import com.memory.redis.pub.RedisPubUtils;
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
@RequestMapping(value="liveMemoryLoad")
public class LiveMemoryLoadController {

    @Autowired
    private LiveMemoryLoadService liveMemoryLoadService;

    @Autowired
    private LiveMasterCmsService liveMasterCmsService;

    @Autowired
    private RedisPubUtils redisPubUtils;

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(){
        Result result = new Result();
        try {
            List<Map<String,Object>> returnMap = new ArrayList<Map<String,Object>>();
            Map<String ,Object> mapper = new HashMap<String ,Object>();
            //List<LiveMaster> options = liveMasterCmsService.queryListMasterOptions();
            List<com.memory.entity.bean.LiveMaster>  optionsList = liveMasterCmsService.queryLiveMasterOptions();
            for (com.memory.entity.bean.LiveMaster master : optionsList) {
                mapper.put(master.getId(),master.getLiveMasterName());
            }

            List<LiveMemoryLoad> list=  liveMemoryLoadService.queryAllLiveMemoryLoadByLoadStatus(0);
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
            result = ResultUtil.success(returnMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping("redisPubTest")
    public Result redisPubTest(){
        Result result = new Result();
        try {

            redisPubUtils.publish("syncLive","liuty","sync live memory ...");


            result = ResultUtil.success();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }





}
