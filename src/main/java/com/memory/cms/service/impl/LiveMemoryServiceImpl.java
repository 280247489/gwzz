package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.LiveRedisCmsService;
import com.memory.cms.service.*;
import com.memory.entity.jpa.LiveMaster;
import com.memory.entity.jpa.LiveMemoryLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.memory.redis.CacheConstantConfig.*;

/**
 * @author INS6+
 * @date 2019/5/28 16:03
 */
@Service
public class LiveMemoryServiceImpl implements LiveMemoryService {

    @Autowired
    private LiveMemoryLoadService liveMemoryLoadService;

    @Autowired
    private LiveSlaveCmsService liveSlaveCmsService;

    @Autowired
    private LiveMasterCmsService liveMasterCmsService;

    @Autowired
    private LiveRedisCmsService liveRedisCmsService;

    public void clear(String masterId){
            String keyHash = liveRedisCmsService.getKey(masterId);
            LIVEMAP.remove(keyHash);
            LiveMemoryLoad liveMemoryLoad = liveMemoryLoadService.getLiveMemoryLoadById(masterId);
            if(liveMemoryLoad != null && liveMemoryLoad.getLoadStatus() != 1){
                liveMemoryLoad.setId(masterId);
                liveMemoryLoad.setLoadStatus(1);
                liveMemoryLoadService.updateLiveMemoryLoadById(liveMemoryLoad);

            }

    }

    public void clearAll(){

            for (Map.Entry<String, Object> entry : LIVEMAP.entrySet()) {
                String mapKey = entry.getKey();
                String masterId = mapKey.substring(mapKey.lastIndexOf(":")+1,mapKey.length());

                LiveMemoryLoad liveMemoryLoad = liveMemoryLoadService.getLiveMemoryLoadById(masterId);
                if(liveMemoryLoad != null && liveMemoryLoad.getLoadStatus() != 1){
                    liveMemoryLoad.setId(masterId);
                    liveMemoryLoad.setLoadStatus(1);
                    liveMemoryLoadService.updateLiveMemoryLoadById(liveMemoryLoad);

                }
            }

            LIVEMAP.clear();

    }


    public Map<String,Object> findAll(){
        return LIVEMAP;
    }


    @Override
    public void addLiveMemory(String masterId) {
        com.memory.entity.bean.LiveSlave liveSlave = new  com.memory.entity.bean.LiveSlave();
        String keyHash = liveRedisCmsService.getKey(masterId);
        Map<String,Object> returnMap = new HashMap<String, Object>();

        List<com.memory.entity.bean.LiveSlave> list = liveSlaveCmsService.queryLiveSlaveList(masterId);
        List<Map<String,Object>> showList = liveSlave.refactorData(list);
        LiveMaster master = liveMasterCmsService.getLiveMasterById(masterId);

        returnMap.put("master",master.getLiveMasterName());
        returnMap.put("slave",showList);
        LIVEMAP.put(keyHash,returnMap);

        initMemoryLoad(masterId, keyHash, returnMap);


    }

    private void initMemoryLoad(String masterId, String keyHash, Map<String, Object> returnMap) {
        LiveMemoryLoad liveMemoryLoad = new LiveMemoryLoad();
        liveMemoryLoad.setId(masterId);
        liveMemoryLoad.setContent(JSON.toJSONString(returnMap));
        liveMemoryLoad.setOperator("admin");
        liveMemoryLoad.setLoadStatus(0);
        liveMemoryLoad.setCreateTime(new Date());
        liveMemoryLoad.setUpdateTime(new Date());
        liveMemoryLoad.setLiveRedisKey(keyHash);
        liveMemoryLoadService.addLiveMemoryLoad(liveMemoryLoad);
    }

    @Override
    public Object getLiveSlaveById(String masterId) {
        String keyHash = liveRedisCmsService.getKey(masterId);
        if( !LIVEMAP.containsKey(keyHash)){
            return null;
        }
        return LIVEMAP.get(keyHash);
    }
}
