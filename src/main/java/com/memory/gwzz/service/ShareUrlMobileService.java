package com.memory.gwzz.service;

import com.memory.entity.jpa.ShareUrl;

/**
 * @ClassName ShareUrlMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 20:42
 */
public interface ShareUrlMobileService {
    ShareUrl getShareUrlByName(String name);
}
