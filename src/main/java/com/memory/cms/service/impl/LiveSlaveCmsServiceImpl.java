package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.repository.LiveSlaveCmsRepository;
import com.memory.cms.service.LiveSlaveCmsService;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.LiveSlave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/6/6 15:54
 */
@Service
public class LiveSlaveCmsServiceImpl implements LiveSlaveCmsService {

    @Autowired
    private LiveSlaveCmsRepository repository;

    @Autowired
    private DaoUtils daoUtils;




    @Override
    public List<LiveSlave> saveAll(List<LiveSlave> list) {
        return repository.saveAll(list);
    }

    @Override
    @Transactional
    public int setLiveSlaveStaticPathByMasterIdAndLiveSlaveSort(String master_id, String sort, String img_url, String audio_url) {

        StringBuffer stringBuffer = new StringBuffer();
        Map<String,Object> params = new HashMap<String,Object>();
        stringBuffer.append("UPDATE LiveSlave c  ");

        if(img_url != null){
            stringBuffer.append("  SET c.liveSlaveImgurl = :liveSlaveImgurl");
            params.put("liveSlaveImgurl",img_url);
        }else{
            stringBuffer.append("  SET c.liveSlaveAudio = :liveSlaveAudio");
            params.put("liveSlaveAudio",audio_url);
        }
        stringBuffer.append(" WHERE c.liveMasterId = :liveMasterId AND c.liveSlaveSort = :liveSlaveSort ");
        params.put("liveMasterId",master_id);
        //params.put("courseExtSort",sort);
        params.put("liveSlaveSort",Integer.valueOf(sort));

        System.out.println("hql = " + stringBuffer);
        System.out.println("map = " + JSON.toJSONString(params));

        return   daoUtils.excuteHQL(stringBuffer.toString(),params);

    }

    @Override
    @Transactional
    public int changeLiveSlaveStatus(String id, Integer status) {
        return 0;
    }

    @Override
    public List<com.memory.entity.bean.LiveSlave> queryLiveSlaveList(String masterId) {
        return repository.queryLiveSlaveList(masterId);
    }

    @Override
    public void deleteLiveSlaveByLiveMasterId(String masterId) {
        repository.deleteLiveSlaveByLiveMasterId(masterId);
    }

    @Override
    @Transactional
    public List<LiveSlave> deleteAndSave(List<LiveSlave> removeList, List<LiveSlave> list) {
        List<LiveSlave> result = null;
        try {
            repository.deleteAll(removeList);
            result = repository.saveAll(list);
        }catch (Exception e){
            e.printStackTrace();
        }
      return result;
    }

    @Override
    public List<LiveSlave> queryLiveSlaveByLiveMasterId(String masterId) {
        return repository.queryLiveSlaveByLiveMasterId(masterId);
    }
}
