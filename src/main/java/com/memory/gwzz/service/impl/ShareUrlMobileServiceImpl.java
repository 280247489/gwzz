package com.memory.gwzz.service.impl;

import com.memory.entity.jpa.ShareUrl;
import com.memory.gwzz.repository.ShareUrlMobileRepository;
import com.memory.gwzz.service.ShareUrlMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ShareUrlMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 20:42
 */
@Service(value = "shareUrlMobileService")
public class ShareUrlMobileServiceImpl implements ShareUrlMobileService {

    @Autowired
    private ShareUrlMobileRepository shareUrlMobileRepository;

    @Override
    public ShareUrl getShareUrlByName(String name){return shareUrlMobileRepository.findByUrlName(name);}


}
