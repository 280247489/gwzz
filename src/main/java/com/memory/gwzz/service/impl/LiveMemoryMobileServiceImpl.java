package com.memory.gwzz.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.common.utils.Utils;
import com.memory.entity.jpa.LiveMaster;
import com.memory.entity.jpa.LiveMemoryLoad;
import com.memory.gwzz.redis.service.LiveRedisMobileService;
import com.memory.gwzz.service.LiveMemoryLoadMobileService;
import com.memory.gwzz.service.LiveMemoryMobileService;
import com.memory.gwzz.service.LiveMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.memory.redis.CacheConstantConfig.*;

/**
 * @author INS6+
 * @date 2019/5/28 16:03
 */
@Service
public class LiveMemoryMobileServiceImpl implements LiveMemoryMobileService {

    @Autowired
    private LiveMemoryLoadMobileService liveMemoryLoadMobileService;

    @Autowired
    private LiveMobileService liveMobileService;

    @Autowired
    private LiveRedisMobileService liveRedisMobileService;

    public void clear(String masterId){
            String keyHash = liveRedisMobileService.getKey(masterId);
            LIVEMAP.remove(keyHash);
            LiveMemoryLoad liveMemoryLoad = liveMemoryLoadMobileService.getLiveMemoryLoadById(masterId);
            if(liveMemoryLoad != null && liveMemoryLoad.getLoadStatus() != 1){
                liveMemoryLoad.setId(masterId);
                liveMemoryLoad.setLoadStatus(1);
                liveMemoryLoadMobileService.updateLiveMemoryLoadById(liveMemoryLoad);

            }

    }

    public void clearAll(){

            for (Map.Entry<String, Object> entry : LIVEMAP.entrySet()) {
                String mapKey = entry.getKey();
                String masterId = mapKey.substring(mapKey.lastIndexOf(":")+1,mapKey.length());

                LiveMemoryLoad liveMemoryLoad = liveMemoryLoadMobileService.getLiveMemoryLoadById(masterId);
                if(liveMemoryLoad != null && liveMemoryLoad.getLoadStatus() != 1){
                    liveMemoryLoad.setId(masterId);
                    liveMemoryLoad.setLoadStatus(1);
                    liveMemoryLoadMobileService.updateLiveMemoryLoadById(liveMemoryLoad);

                }
            }

            LIVEMAP.clear();

    }


    public Map<String,Object> findAll(){
        return LIVEMAP;
    }


    @Override
    public void addLiveMemory(String masterId) {
        com.memory.gwzz.model.LiveSlave liveSlave = new   com.memory.gwzz.model.LiveSlave();
        String keyHash = liveRedisMobileService.getKey(masterId);
        Map<String,Object> returnMap = new HashMap<String, Object>();

        List<com.memory.gwzz.model.LiveSlave> list = liveMobileService.queryLiveSlaveList(masterId);
        List<Map<String,Object>> showList = liveSlave.refactorData(list);
        LiveMaster master = liveMobileService.getLiveMasterById(masterId);

        if(Utils.isNotNull(master)){
            returnMap.put("master",master.getLiveMasterName());
            returnMap.put("slave",showList);
            LIVEMAP.put(keyHash,returnMap);

            initMemoryLoad(masterId, keyHash, returnMap);
        }





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
        liveMemoryLoadMobileService.addLiveMemoryLoad(liveMemoryLoad);
    }

    @Override
    public Object getLiveSlaveById(String masterId) {
        String keyHash = liveRedisMobileService.getKey(masterId);
        if( !LIVEMAP.containsKey(keyHash)){
            return null;
        }
        return LIVEMAP.get(keyHash);
    }
}
