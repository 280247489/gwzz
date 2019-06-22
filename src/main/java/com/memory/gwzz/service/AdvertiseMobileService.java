package com.memory.gwzz.service;

import com.memory.entity.jpa.Advertise;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AdvertiseMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/31 9:08
 */
public interface AdvertiseMobileService {
    List<Advertise> getAdvertiseOnline();
    Map<String, Object> getAdvertiseById(Advertise advertise, String openId, Integer terminal, Integer os);
}
