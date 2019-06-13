package com.memory.cms.service;

import com.memory.entity.jpa.LiveMaster;
import com.memory.entity.jpa.LiveSlave;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/6/6 15:51
 */

public interface LiveMasterCmsService {


    LiveMaster getLiveMasterById(String id);

    List<LiveMaster> queryLiveMasterList();

    LiveMaster add(LiveMaster liveMaster);

    LiveMaster update(LiveMaster liveMaster);

    int updateLiveMasterStatus(int status,String id);

    int updateLiveMasterOnline(int online,String id);

    int updateLiveMasterIsSynthesisAudio(int is_synthesis_audio,String id);

    int upgradeLiveMasterSynthesisAudio(int is_synthesis_audio ,String synthesis_audio_url,String id);

    int updateLiveMasterIsPush(int is_push,String id);

    int updateLiveMasterIsRelation(int is_relation,String id);

    int upgradeLiveMasterIsRelation(int is_relation,String course_id,String id);

    List<LiveMaster> queryLiveMasterByLiveMasterIsSynthesisAudio();

    List<LiveMaster> queryListMasterOptions();


}
