package com.memory.cms.service;

import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/28 16:03
 */

public interface LiveMemoryService {


    void clear(String masterId);

    void clearAll();

    Map<String,Object> findAll();

    void addLiveMemory(String masterId);

    Object getLiveSlaveById(String masterId);


}
