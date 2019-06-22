package com.memory.gwzz.repository;

import com.memory.entity.jpa.LiveSlave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName LiveSlaveMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/21 16:46
 */
public interface LiveSlaveMobileRepository extends JpaRepository<LiveSlave,String> {

    @Query("select new com.memory.gwzz.model.LiveSlave(l.id,l.liveSlaveNickname,l.liveSlaveLogo,l.liveSlaveType,l.liveSlaveWords,l.liveSlaveImgurl,l.liveSlaveAudio,l.liveSlaveAudioTime,l.liveSlaveSort) " +
            " from LiveSlave l where l.liveMasterId =?1 order by l.liveSlaveSort ASC ")
    List<com.memory.gwzz.model.LiveSlave> queryLiveSlaveList(String masterId);
}
