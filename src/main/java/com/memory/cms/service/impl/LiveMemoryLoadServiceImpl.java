package com.memory.cms.service.impl;

import com.memory.cms.repository.LiveMemoryLoadRepository;
import com.memory.cms.service.LiveMemoryLoadService;
import com.memory.entity.jpa.LiveMemoryLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/28 14:54
 */
@Service
public class LiveMemoryLoadServiceImpl implements LiveMemoryLoadService {

    @Autowired
    private LiveMemoryLoadRepository repository;

    @Override
    public LiveMemoryLoad getLiveMemoryLoadById(String courseId) {
        if(repository.findById(courseId).hashCode() != 0){
            return repository.findById(courseId).get();
        }else{
            return null;
        }
    }

    @Override
    public LiveMemoryLoad updateLiveMemoryLoadById(LiveMemoryLoad courseMemoryLoad) {
        return repository.save(courseMemoryLoad);
    }

    @Override
    public LiveMemoryLoad addLiveMemoryLoad(LiveMemoryLoad courseMemoryLoad) {
        return repository.save(courseMemoryLoad);
    }

    @Override
    public List<LiveMemoryLoad> queryAllLiveMemoryLoadByLoadStatus(int load_status) {
        return repository.queryLiveMemoryLoadList(load_status);
    }
}
