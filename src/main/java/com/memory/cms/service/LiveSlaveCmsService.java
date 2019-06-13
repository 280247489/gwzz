package com.memory.cms.service;

import com.memory.entity.jpa.LiveSlave;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/6/6 15:54
 */

public interface LiveSlaveCmsService {

    List<LiveSlave> saveAll(List<LiveSlave> list);

    int setLiveSlaveStaticPathByMasterIdAndLiveSlaveSort(String master_id,String sort,String img_url,String audio_url);

    int changeLiveSlaveStatus(String id,Integer status);

    List<com.memory.entity.bean.LiveSlave> queryLiveSlaveList(String masterId);

    void deleteLiveSlaveByLiveMasterId(String masterId);

    List<LiveSlave> deleteAndSave(List<LiveSlave> removeList,List<LiveSlave> list);

    List<LiveSlave> queryLiveSlaveByLiveMasterId(String masterId);

}
