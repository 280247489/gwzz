package com.memory.gwzz.service;

import com.memory.gwzz.model.LiveMaster;

import java.util.Map;

/**
 * @ClassName HomePageMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/13 16:21
 */
public interface HomePageMobileService {
    Map<String,Object> HomePageOne();
    LiveMaster HomePageTwo();

    Map<String, Object> getAdvertiseById(com.memory.entity.jpa.Banner banner, String userId, String openId, Integer terminal, Integer os);

    void setVV(String userId, Integer os);
}