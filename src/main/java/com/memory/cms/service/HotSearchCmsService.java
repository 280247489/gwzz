package com.memory.cms.service;

import com.memory.entity.jpa.HotSearch;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/7/22 13:11
 */

public interface HotSearchCmsService {

    HotSearch add(HotSearch hotSearch);

    HotSearch update(HotSearch hotSearch);

    void delete(String id);

    HotSearch getHotSearchById(String id);

    List<HotSearch> queryHotSearchByQue(String keyWord,String operatorId);

    List<HotSearch> getHotSearchByKeyWord(String keyWord);



}
