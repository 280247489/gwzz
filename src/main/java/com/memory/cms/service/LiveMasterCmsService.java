package com.memory.cms.service;

import com.memory.entity.jpa.LiveMaster;

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

    List<LiveMaster> queryLiveMasterByQueHql(int pageIndex,int limit,String live_master_name,String operator_id,Integer status );

    int queryLiveMasterByQueHqlCount(String live_master_name,String operator_id,Integer status);

    int changeAllStatus2close(String id);

    LiveMaster getLiveMasterByCourseId(String courseId);

    void syncOnline2Redis( String id, int online);

    List<com.memory.entity.bean.LiveMaster> queryLiveMasterOptions();





}
