package com.memory.cms.service.impl;

import com.memory.cms.repository.HotSearchCmsRepository;
import com.memory.cms.service.HotSearchCmsService;
import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.HotSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/7/22 13:12
 */
@Service
public class HotSearchCmsServiceImpl implements HotSearchCmsService {

    @Autowired
    private HotSearchCmsRepository repository;
    @Autowired
    private DaoUtils daoUtils;

    @Override
    public HotSearch add(HotSearch hotSearch) {
        return repository.save(hotSearch);
    }

    @Override
    public HotSearch update(HotSearch hotSearch) {
        return repository.save(hotSearch);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public HotSearch getHotSearchById(String id) {
        if(repository.findById(id).hashCode() != 0){
            return repository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public List<HotSearch> queryHotSearchByQue(String keyWord, String operatorId) {
        StringBuffer sb = new StringBuffer();
        Map<String,Object> params = new HashMap<String, Object>();
        sb.append(" FROM HotSearch  hs WHERE 1=1 ");
        if(Utils.isNotNull(keyWord)){
            sb.append(" AND hs.keyWord like :keyWord");
            params.put("keyWord","%"+keyWord+"%");
        }

        if(Utils.isNotNull(operatorId)){
            sb.append(" AND hs.operatorId = :operatorId");
            params.put("operatorId",operatorId);
        }

        sb.append(" ORDER BY hs.status desc,hs.sort asc,hs.lastUpdateTime desc");


        return  daoUtils.findByHQL(sb.toString(),params,null);
    }


    @Override
    public List<HotSearch> getHotSearchByKeyWord(String keyWord) {
        StringBuffer sb = new StringBuffer();
        Map<String,Object> params = new HashMap<String, Object>();
        sb.append(" FROM HotSearch  hs WHERE 1=1 ");
        if(Utils.isNotNull(keyWord)){
            sb.append(" AND hs.keyWord = :keyWord");
            params.put("keyWord",keyWord);
        }

        return  daoUtils.findByHQL(sb.toString(),params,null);
    }
}
