package com.memory.gwzz.service;

import com.memory.entity.jpa.LiveMemoryLoad;
import com.memory.entity.jpa.LiveMemoryLoad;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/28 14:54
 */

public interface LiveMemoryLoadMobileService {

    LiveMemoryLoad getLiveMemoryLoadById(String courseId);

    LiveMemoryLoad updateLiveMemoryLoadById(LiveMemoryLoad liveMemoryLoad);

    LiveMemoryLoad addLiveMemoryLoad(LiveMemoryLoad liveMemoryLoad);

    List<LiveMemoryLoad> queryAllLiveMemoryLoadByLoadStatus(int load_status);

}
