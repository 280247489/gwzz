package com.memory.gwzz.service;

import com.memory.gwzz.model.LiveSlave;

import java.util.List;

/**
 * @ClassName LiveMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/21 16:38
 */
public interface LiveMobileService {
    List<LiveSlave> queryLiveSlaveList(String id);
}
