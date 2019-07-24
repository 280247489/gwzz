package com.memory.gwzz.service.impl;

import com.memory.entity.jpa.LiveMaster;
import com.memory.gwzz.model.LiveSlave;
import com.memory.gwzz.repository.LiveMasterMobileRepository;
import com.memory.gwzz.repository.LiveSlaveMobileRepository;
import com.memory.gwzz.service.LiveMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName LiveMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/21 16:37
 */
@Service(value = "")
public class LiveMobileServiceImpl implements LiveMobileService {
    @Autowired
    private LiveSlaveMobileRepository liveSlaveMobileRepository;

    @Autowired
    private LiveMasterMobileRepository liveMasterMobileRepository;

    @Override
    public List<LiveSlave> queryLiveSlaveList(String id) {
        return liveSlaveMobileRepository.queryLiveSlaveList(id);
    }

    @Override
    public LiveMaster getLiveMasterById(String id) {
        if (liveMasterMobileRepository.findById(id).hashCode() != 0) {
            return liveMasterMobileRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public List<com.memory.gwzz.model.LiveMaster> queryLiveMasterOptions() {
        return liveMasterMobileRepository.queryLiveMasterOptions();
    }
}
