package com.memory.cms.repository;

import com.memory.entity.jpa.LiveMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/6/6 15:53
 */
public interface LiveMasterCmsRepository extends JpaRepository<LiveMaster,String> {


    //更新直播状态
    @Modifying
    @Transactional
    @Query("update LiveMaster m set m.liveMasterStatus =?1 where m.id =?2")
    int updateLiveMasterStatus(int status,String id);

    //更新上下线状态
    @Modifying
    @Transactional
    @Query("update LiveMaster m set m.liveMasterIsOnline =?1 where m.id =?2")
    int updateLiveMasterOnline(int online,String id);

    //更新合成音频状态
    @Modifying
    @Transactional
    @Query("update LiveMaster m set m.liveMasterIsSynthesisAudio =?1 where m.id =?2")
    int updateLiveMasterIsSynthesisAudio(int is_synthesis_audio,String id);

    //更新合成音频状态和音频地址
    @Modifying
    @Transactional
    @Query("update LiveMaster m set m.liveMasterIsSynthesisAudio =?1,m.liveMasterSynthesisAudioUrl =?2   where m.id =?3")
    int upgradeLiveMasterSynthesisAudio(int is_synthesis_audio ,String synthesis_audio_url,String id);

    //更新推送状态
    @Modifying
    @Transactional
    @Query("update LiveMaster m set m.liveMasterIsPush =?1 where m.id =?2")
    int updateLiveMasterIsPush(int is_push,String id);

    //更新课程关联状态

    @Modifying
    @Transactional
    @Query("update LiveMaster m set m.liveMasterIsRelation =?1 where m.id =?2")
    int updateLiveMasterIsRelation(int is_relation,String id);

    //更新课程关联状态和关联课程id
    @Modifying
    @Transactional
    @Query("update LiveMaster m set m.liveMasterIsRelation =?1,m.courseId =?2 where m.id =?3")
    int upgradeLiveMasterIsRelation(int is_relation,String course_id,String id);

    @Query(" from LiveMaster order by liveMasterUpdateTime desc")
    List<LiveMaster> queryLiveMasterOrderByLiveMasterUpdateTimeDesc();

    //把所有上线直播状态的记录变更为直播完毕
    @Modifying
    @Transactional
    @Query("update LiveMaster m set m.liveMasterStatus =2 where m.liveMasterStatus=1")
    int changeAllStatus2close();


    List<LiveMaster> queryLiveMasterByLiveMasterIsSynthesisAudio(int isSynthesisAudio);

    @Query("from LiveMaster l where l.liveMasterIsOnline=1 order by l.liveMasterCreateTime")
    List<LiveMaster> queryListMasterOptions();





}
