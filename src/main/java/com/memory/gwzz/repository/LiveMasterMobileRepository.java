package com.memory.gwzz.repository;

import com.memory.entity.jpa.LiveMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName LiveMasterMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/21 16:45
 */
public interface LiveMasterMobileRepository extends JpaRepository<LiveMaster,String> {
    LiveMaster findByCourseIdAndLiveMasterIsOnline(String cid,Integer isOnline);
}
