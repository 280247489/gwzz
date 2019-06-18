package com.memory.cms.repository;

import com.memory.entity.jpa.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName AdvertiseCmsRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 11:14
 */
public interface AdvertiseCmsRepository extends JpaRepository<Advertise,String>, JpaSpecificationExecutor {
//    Advertise findByAdvertiseOnline
}
