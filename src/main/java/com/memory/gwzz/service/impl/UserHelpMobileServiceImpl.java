package com.memory.gwzz.service.impl;

import com.memory.gwzz.repository.UserHelpMobileRepository;
import com.memory.gwzz.service.UserHelpMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserHelpMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/10 15:35
 */
@Service("userHelpMobileService")
public class UserHelpMobileServiceImpl implements UserHelpMobileService {
    @Autowired
    private UserHelpMobileRepository userHelpMobileRepository;

    @Override
    public Map<String, Object> listUserHelp() {
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("classProblem",userHelpMobileRepository.findByHelpTypeAndUseYnOrderByHelpSortAsc(1,0));
        returnMap.put("commonProblem",userHelpMobileRepository.findByHelpTypeAndUseYnOrderByHelpSortAsc(2,0));
        return returnMap;
    }
}
