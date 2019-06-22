package com.memory.gwzz.repository;

import com.memory.entity.jpa.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName AdvertiseMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/31 9:10
 */
public interface AdvertiseMobileRepository extends JpaRepository<Advertise,String> {
    List<Advertise> findByAdvertiseOnline(Integer advertiseOnline);
    Advertise findByIdAndAdvertiseOnline(String id,Integer online);
}
