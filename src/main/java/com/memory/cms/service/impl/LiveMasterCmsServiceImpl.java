package com.memory.cms.service.impl;

import com.memory.cms.repository.LiveMasterCmsRepository;
import com.memory.cms.service.LiveMasterCmsService;
import com.memory.entity.jpa.LiveMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/6/6 15:52
 */
@Service
public class LiveMasterCmsServiceImpl implements LiveMasterCmsService {


    @Autowired
    private LiveMasterCmsRepository repository;


    @Override
    public LiveMaster getLiveMasterById(String id) {
        if (repository.findById(id).hashCode() != 0) {
            return repository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public List<LiveMaster> queryLiveMasterList() {
        return repository.queryLiveMasterOrderByLiveMasterUpdateTimeDesc();
    }

    @Override
    public LiveMaster add(LiveMaster liveMaster) {
        return repository.save(liveMaster);
    }

    @Override
    public LiveMaster update(LiveMaster liveMaster) {
        return repository.save(liveMaster);
    }

    @Override
    @Transactional
    public int updateLiveMasterStatus(int status, String id) {
        //变更未直播状态时将其他直播状态的数据设置成直播完毕
        if(status == 1){
            repository.changeAllStatus2close();
        }
        return repository.updateLiveMasterStatus(status, id);
    }

    @Override
    public int updateLiveMasterOnline(int online, String id) {
        return repository.updateLiveMasterOnline(online, id);
    }

    @Override
    public int updateLiveMasterIsSynthesisAudio(int is_synthesis_audio, String id) {
        return repository.updateLiveMasterIsSynthesisAudio(is_synthesis_audio, id);
    }

    @Override
    public int upgradeLiveMasterSynthesisAudio(int is_synthesis_audio, String synthesis_audio_url, String id) {
        return repository.upgradeLiveMasterSynthesisAudio(is_synthesis_audio, synthesis_audio_url, id);
    }

    @Override
    public int updateLiveMasterIsPush(int is_push, String id) {
        return repository.updateLiveMasterIsPush(is_push, id);
    }

    @Override
    public int updateLiveMasterIsRelation(int is_relation, String id) {
        return repository.updateLiveMasterIsRelation(is_relation, id);
    }

    @Override
    public int upgradeLiveMasterIsRelation(int is_relation, String course_id, String id) {
        return repository.upgradeLiveMasterIsRelation(is_relation, course_id, id);
    }

    @Override
    public List<LiveMaster> queryLiveMasterByLiveMasterIsSynthesisAudio() {
        //查询所有未被关联的课程
        return repository.queryLiveMasterByLiveMasterIsSynthesisAudio(0);
    }

    @Override
    public List<LiveMaster> queryListMasterOptions() {
        return repository.queryListMasterOptions();
    }
}