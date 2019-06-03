package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.CourseExt;
import com.memory.cms.repository.CourseExtCmsRepository;
import com.memory.cms.service.CourseExtCmsService;
import com.memory.redis.config.RedisUtil;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.memory.redis.CacheConstantConfig.SHARECOURSECONTENT;

/**
 * @author INS6+
 * @date 2019/5/9 9:32
 */
@Service
public class CourseExtCmsServiceImpl implements CourseExtCmsService {

    @Autowired
    private DaoUtils daoUtils;



    @Autowired
    private CourseExtCmsRepository repository;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public CourseExt getCourseExtById(String id) {
        if(repository.findById(id).hashCode() != 0){
            return repository.findById(id).get();
        }else {
            return null;
        }
    }

    @Override
    public CourseExt save(CourseExt ext) {

        return repository.save(ext);
    }

    @Override
    public CourseExt update(CourseExt ext) {

        return repository.save(ext);
    }

    @Override
    public void deleteCourseExtByCourseId(String article_id) {
        repository.deleteCourseExtByCourseId(article_id);
    }

    @Override
    public List<CourseExt> queryCourseExtByCourseId(String course_id) {
        return repository.queryCourseExtByCourseIdOrderByCourseExtSortAsc(course_id);
    }


    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<CourseExt> saveAll(List<CourseExt> list) {

     return   repository.saveAll(list);
    }

    @Override
    @Transactional
    public  List<CourseExt> deleteAndSave(List<CourseExt> removeList, List<CourseExt> updateList) {
        List<CourseExt> list = null;
        try{
            repository.deleteAll(removeList);
            list = repository.saveAll(updateList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<com.memory.entity.bean.CourseExt> queryCourseExtList(String courseId) {
        return  repository.queryCourseExtList(courseId);
    }

    @Override
    @Transactional
    public int setCourseExtStaticPathByCourseIdAndCourseExtSort(String course_id, String sort,String img_url,String audio_url) {

        StringBuffer stringBuffer = new StringBuffer();
        Map<String,Object> params = new HashMap<String,Object>();
        stringBuffer.append("UPDATE CourseExt c  ");

        if(img_url != null){
            stringBuffer.append("  SET c.courseExtImgUrl = :courseExtImgUrl");
            params.put("courseExtImgUrl",img_url);
        }else{
            stringBuffer.append("  SET c.courseExtAudio = :courseExtAudio");
            params.put("courseExtAudio",audio_url);
        }
            stringBuffer.append(" WHERE c.courseId = :courseId AND c.courseExtSort = :courseExtSort ");
            params.put("courseId",course_id);
            //params.put("courseExtSort",sort);
            params.put("courseExtSort",Integer.valueOf(sort));

            System.out.println("hql = " + stringBuffer);
            System.out.println("map = " + JSON.toJSONString(params));

          return   daoUtils.excuteHQL(stringBuffer.toString(),params);

    }

    @Override
    public void updateCourseExtDb2Redis(String courseId,String course_title){
        String keyHash = SHARECOURSECONTENT + courseId;
        //查询数据库db
        List<CourseExt> list = queryCourseExtByCourseId(courseId);
        //添加到redis中
        redisUtil.hset(keyHash, "course", course_title);
        redisUtil.hset(keyHash, "courseExt", JSON.toJSONString(overMethod(list)));
    }


    public List<Map<String,Object>> overMethod(List<CourseExt> extListSave){

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < extListSave.size(); i++) {
            CourseExt courseExt1 = extListSave.get(i);
            Map<String, Object> mapObj = new HashMap<>();
            mapObj.put("courseExtNickname", courseExt1.getCourseExtNickname());
            mapObj.put("courseExtSort", courseExt1.getCourseExtSort());
            mapObj.put("courseExtType", courseExt1.getCourseExtType());
            if(courseExt1.getCourseExtType()==1){
                mapObj.put("courseExtWords", courseExt1.getCourseExtWords());
            }else if(courseExt1.getCourseExtType()==2){
                mapObj.put("courseExtAudio", courseExt1.getCourseExtAudio());
                mapObj.put("courseExtAudioTimes", courseExt1.getCourseExtAudioTimes());
            }else if(courseExt1.getCourseExtType()==3){
                mapObj.put("courseExtImgUrl", courseExt1.getCourseExtImgUrl());
            }
            resultList.add(mapObj);
        }
        return  resultList;
    }


}
