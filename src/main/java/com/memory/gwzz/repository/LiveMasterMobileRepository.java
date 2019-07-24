package com.memory.gwzz.repository;

import com.memory.entity.jpa.LiveMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName LiveMasterMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/21 16:45
 */
public interface LiveMasterMobileRepository extends JpaRepository<LiveMaster,String> {
    LiveMaster findByCourseIdAndLiveMasterIsOnline(String cid,Integer isOnline);
    LiveMaster findByCourseId(String cid);

    @Query(value = "select  new com.memory.gwzz.model.LiveMaster(l.id,l.liveMasterName ) " +
            "from LiveMaster l  where 1=1 ")
    List<com.memory.gwzz.model.LiveMaster> queryLiveMasterOptions();
}
