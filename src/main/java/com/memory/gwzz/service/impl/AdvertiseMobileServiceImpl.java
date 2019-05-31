package com.memory.gwzz.service.impl;

import com.memory.entity.jpa.Advertise;
import com.memory.gwzz.repository.AdvertiseMobileRepository;
import com.memory.gwzz.service.AdvertiseMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AdvertiseMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/31 9:08
 */
@Service("advertiseCommentMobileService")
public class AdvertiseMobileServiceImpl implements AdvertiseMobileService {

    @Autowired
    private AdvertiseMobileRepository advertiseMobileRepository;

    @Override
    public List<Advertise> getAdvertiseOnline(){
        return advertiseMobileRepository.findByAdvertiseOnline(1);
    }
}
