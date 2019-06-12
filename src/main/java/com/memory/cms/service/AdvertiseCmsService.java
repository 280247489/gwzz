package com.memory.cms.service;

import com.memory.entity.jpa.Advertise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName AdvertiseCmsService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 11:16
 */
public interface AdvertiseCmsService {
    void addAdvertise(String aName, MultipartFile aLogo, Integer aType, String aH5Type, String aH5Url, String createId);
    Advertise checkAdvertiseName(String aName, String id);
    Page<Advertise> findAdvertise(Pageable pageable, String aName, Integer aType);
    Advertise upd(Advertise advertise, String aName, MultipartFile aLogo, String aH5Type, String aH5Url, String createId);
    Advertise updOnine (Advertise advertise);
    void del(Advertise advertise);
}
