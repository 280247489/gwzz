package com.memory.cms.repository;

import com.memory.entity.jpa.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName AdvertiseCmsRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 11:14
 */
public interface AdvertiseCmsRepository extends JpaRepository<Advertise,String>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query("update Advertise a set a.advertiseOnline =2 where a.advertiseOnline=1  and  a.advertiseType=?1 and a.id  <> ?2")
    int closeOnlineByType(Integer type,String id);


}
