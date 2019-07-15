package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.LiveRedisCmsService;
import com.memory.cms.repository.LiveMasterCmsRepository;
import com.memory.cms.service.LiveMasterCmsService;
import com.memory.cms.service.LiveMemoryService;
import com.memory.cms.service.LiveSlaveCmsService;
import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.LiveMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.memory.redis.CacheConstantConfig.LIVEMAP;


/**
 * @author INS6+
 * @date 2019/6/6 15:52
 */
@Service
public class LiveMasterCmsServiceImpl implements LiveMasterCmsService {


    @Autowired
    private LiveMasterCmsRepository repository;

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private LiveRedisCmsService liveRedisCmsService;

    private LiveSlaveCmsService liveSlaveCmsService;

    @Autowired
    private LiveMemoryService liveMemoryService;


    @Override
    public LiveMaster getLiveMasterById(String id) {
        if (repository.findById(id).hashCode() != 0) {
            return repository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public List<LiveMaster> queryLiveMasterList() {
        return repository.queryLiveMasterOrderByLiveMasterUpdateTimeDesc();
    }

    @Override
    public LiveMaster add(LiveMaster liveMaster) {
        return repository.save(liveMaster);
    }

    @Override
    public LiveMaster update(LiveMaster liveMaster) {
        return repository.save(liveMaster);
    }

    @Override
    @Transactional
    public int updateLiveMasterStatus(int status, String id) {
        //变更未直播状态时将其他直播状态的数据设置成直播完毕
        if(status == 1){
            repository.changeAllStatus2close(id);
        }
        return repository.updateLiveMasterStatus(status, id);
    }

    @Override
    public int updateLiveMasterOnline(int online, String id) {
        return repository.updateLiveMasterOnline(online, id);
    }

    @Override
    public int updateLiveMasterIsSynthesisAudio(int is_synthesis_audio, String id) {
        return repository.updateLiveMasterIsSynthesisAudio(is_synthesis_audio, id);
    }

    @Override
    public int upgradeLiveMasterSynthesisAudio(int is_synthesis_audio, String synthesis_audio_url, String id) {
        return repository.upgradeLiveMasterSynthesisAudio(is_synthesis_audio, synthesis_audio_url, id);
    }

    @Override
    public int updateLiveMasterIsPush(int is_push, String id) {
        return repository.updateLiveMasterIsPush(is_push, id);
    }

    @Override
    public int updateLiveMasterIsRelation(int is_relation, String id) {
        return repository.updateLiveMasterIsRelation(is_relation, id);
    }

    @Override
    public int upgradeLiveMasterIsRelation(int is_relation, String course_id, String id) {
        return repository.upgradeLiveMasterIsRelation(is_relation, course_id, id);
    }

    @Override
    public List<LiveMaster> queryLiveMasterByLiveMasterIsSynthesisAudio() {
        //查询所有未被关联的课程
        return repository.queryLiveMasterByLiveMasterIsRelation(0);
    }

    @Override
    public List<LiveMaster> queryListMasterOptions() {
        return repository.queryListMasterOptions();
    }

    @Override
    public List<LiveMaster> queryLiveMasterByQueHql(int pageIndex, int limit, String live_master_name, String operator_id, Integer status) {
        DaoUtils.Page page = daoUtils.getPage(pageIndex, limit);
            List<LiveMaster> list =new ArrayList<LiveMaster>();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(" FROM LiveMaster where 1=1 ");
        Map<String,Object> whereClause = getWhereClause(live_master_name,operator_id,status);
        stringBuffer.append(whereClause.get("where"));
        Map<String,Object> map = (  Map<String,Object>) whereClause.get("param");

        System.out.println("hql ============= " + stringBuffer.toString() );
        System.out.println("where ==========="+whereClause.get("where").toString());
        System.out.println("map ============= " + JSON.toJSONString(map));
        list= daoUtils.findByHQL(stringBuffer.toString(), map, page);

        return list;
    }

    @Override
    public int queryLiveMasterByQueHqlCount(String live_master_name, String operator_id, Integer status) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT count(*) FROM LiveMaster where 1=1 ");
        Map<String,Object> whereClause = getWhereClause(live_master_name,operator_id,status);
        Map<String,Object> map = (  Map<String,Object>) whereClause.get("param");
        stringBuffer.append(whereClause.get("where"));

        return daoUtils.getTotalByHQL(stringBuffer.toString(),map);
    }


    public Map<String,Object> getWhereClause(String live_master_name, String operator_id, Integer status){
        Map<String,Object> returnMap = new HashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<String, Object>();

        if(Utils.isNotNull(live_master_name)){
            stringBuffer.append(" AND liveMasterName like :liveMasterName  ");
            paramMap.put("liveMasterName",'%'+ live_master_name+'%');
        }

        if(Utils.isNotNull(operator_id)){
            stringBuffer.append(" AND liveMasterUpdateId = :liveMasterUpdateId  ");
            paramMap.put("liveMasterUpdateId",operator_id);
        }

        if(Utils.isNotNull(status)){
            stringBuffer.append(" AND liveMasterStatus = :liveMasterStatus  ");
            paramMap.put("liveMasterStatus",status);
        }
        stringBuffer.append(" ORDER BY liveMasterUpdateTime DESC,liveMasterCreateTime DESC");

        returnMap.put("where",stringBuffer.toString());
        returnMap.put("param",paramMap);
        return returnMap;
    }

    @Override
    public int changeAllStatus2close(String id) {
        return repository.changeAllStatus2close(id);
    }

    @Override
    public LiveMaster getLiveMasterByCourseId(String courseId) {
        return repository.getLiveMasterByCourseId(courseId);
    }

    @Override
    public void syncOnline2Redis( String id, int online) {
        if(online == 0){
            liveRedisCmsService.live2RedisNotExist(id);
        }else{
            //上线状态，同步db2redis
            upgradeLiveDb2Redis(id);
        }
    }

    public void  upgradeLiveDb2Redis(String masterId){
        com.memory.entity.bean.LiveSlave liveSlave = new  com.memory.entity.bean.LiveSlave();
        String keyHash = liveRedisCmsService.getKey(masterId);
        LiveMaster master = getLiveMasterById(masterId);
        List<com.memory.entity.bean.LiveSlave> list = liveSlaveCmsService.queryLiveSlaveList(masterId);
        //重构数据
        List<Map<String,Object>> showList = liveSlave.refactorData(list);
        //同步db数据到redis中
        liveRedisCmsService.live2Redis(masterId,master.getLiveMasterName(),showList);

            //如果内存中有缓存记录，则同步更新内存
            if(LIVEMAP.containsKey(keyHash)){
                liveMemoryService.addLiveMemory(masterId);
            }
    }

    @Override
    public List<com.memory.entity.bean.LiveMaster> queryLiveMasterOptions() {
        return repository.queryLiveMasterOptions();
    }
}