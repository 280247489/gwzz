package com.memory.gwzz.service.impl;

import com.memory.gwzz.model.LiveSlave;
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

    @Override
    public List<LiveSlave> queryLiveSlaveList(String id) {
        return liveSlaveMobileRepository.queryLiveSlaveList(id);
    }
}
