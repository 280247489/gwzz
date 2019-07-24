package com.memory.gwzz.repository;

import com.memory.entity.jpa.LiveMemoryLoad;
import com.memory.entity.jpa.LiveMemoryLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/28 14:53
 */

public interface LiveMemoryLoadMobileRepository extends JpaRepository<LiveMemoryLoad,String> {



    @Query(value = "select  c.id,c.content,c.operator,c.load_status,c.create_time,c.update_time,c.live_redis_key " +
            "from live_memory_load c  where  c.load_status =?1 ORDER BY  c.create_time desc", nativeQuery = true)
    List<LiveMemoryLoad> queryLiveMemoryLoadList(Integer load_status);



}
