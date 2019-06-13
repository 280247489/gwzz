package com.memory.cms.repository;

import com.memory.entity.jpa.LiveSlave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/6/6 15:55
 */

public interface LiveSlaveCmsRepository extends JpaRepository<LiveSlave,String> {


    @Query("select new com.memory.entity.bean.LiveSlave(l.id,l.liveSlaveNickname,l.liveSlaveLogo,l.liveSlaveType,l.liveSlaveWords,l.liveSlaveImgurl,l.liveSlaveAudio,l.liveSlaveAudioTime,l.liveSlaveSort) " +
            " from LiveSlave l where l.liveMasterId =?1 order by l.liveSlaveSort ASC ")
    List<com.memory.entity.bean.LiveSlave> queryLiveSlaveList(String masterId);

    void deleteLiveSlaveByLiveMasterId(String masterId);

    List<LiveSlave> queryLiveSlaveByLiveMasterId(String masterId);


}
