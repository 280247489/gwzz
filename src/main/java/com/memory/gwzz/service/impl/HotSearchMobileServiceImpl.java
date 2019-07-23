package com.memory.gwzz.service.impl;

import com.memory.entity.jpa.HotSearch;
import com.memory.gwzz.repository.HotSearchMobileRepository;
import com.memory.gwzz.service.HotSearchMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName HotSearchMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/23 10:19
 */
@Service("hotSearchMobileService")
public class HotSearchMobileServiceImpl implements HotSearchMobileService {

    @Autowired
    private HotSearchMobileRepository hotSearchMobileRepository;

    @Override
    public List<HotSearch> hotSearchList (){
        List<HotSearch> list = hotSearchMobileRepository.findByStatusOrderBySortDesc(1);
        return list;
    }



}
