package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.repository.CourseCmsRepository;
import com.memory.cms.service.CourseCmsService;
import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.util.*;

/**
 * @author INS6+
 * @date 2019/5/8 10:56
 */
@Service
public class CourseCmsServiceImpl implements CourseCmsService {

    @Autowired
    private CourseCmsRepository repository;

    @Autowired
    private DaoUtils daoUtils;


    @Override
    public List<Course> getCourseList() {
        return repository.findAll();
    }

    @Override
    public Course getCourseById(String id) {
        if(repository.findById(id).hashCode() != 0){
            return repository.findById(id).get();
        }else{
            return null;
        }

    }

    @Override
    public Course add(Course course) {

        return repository.save(course);
    }

    @Override
    public Course update(Course course) {

        return repository.save(course);
    }

    @Override
    public void delete(String id) {

         repository.deleteById(id);
    }


    @Override
    @Transactional
    public int updateCourseOnlineById(int online,String id) {

        return repository.updateCourseOnlineById(online,id);
    }


    @Override
    public Page<Painter> queryCourseByQue(Pageable pageable,String course_title,String course_update_id,Integer course_online,String sort_status,String course_type_id,String album_id) {
        Specification specification =new Specification<Painter>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if(Utils.isNotNull(course_title)){
                    list.add(cb.like(root.get("courseTitle"),"%" + course_title + "%"));
                }

                if(Utils.isNotNull(course_update_id) ){
                    list.add(cb.equal(root.get("courseUpdateId"),course_update_id));
                }

                if(Utils.isNotNull(course_online)){
                    list.add(cb.equal(root.get("courseOnline"),course_online));
                }

                if(Utils.isNotNull(course_type_id)){
                    list.add(cb.equal(root.get("courseTypeId"),course_type_id));
                }

                if(Utils.isNotNull(album_id)){
                    list.add(cb.equal(root.get("albumId"),album_id));
                }

                Predicate[] p = new Predicate[list.size()];
                query.where(cb.and(list.toArray(p)));

               if(Utils.isNotNull(sort_status) && sort_status.equals("asc")){

                    query.orderBy(cb.asc(root.get("courseUpdateTime")));
                }else{
                   //默认根据更新时间倒叙排列
                    query.orderBy(cb.desc(root.get("courseUpdateTime")));
                }
                return query.getRestriction();
            }
        };

        return repository.findAll(specification,pageable);
    }


    @Override
    public int updateCourseLiveStatus(int course_live_status,String id) {
        return  repository.updateCourseLiveStatus(course_live_status,id);
    }

    @Override
    public List<com.memory.entity.bean.Course> queryCourseOptions() {
        return repository.queryCourseOptions();
    }

    @Override
    public int updateCourseUpdateTimeById(Date update_time, String id) {
        return repository.updateCourseUpdateTimeById(update_time,id);
    }

    @Override
    public List<Course> queryAllOnlineCourse() {
        return repository.queryAllOnlineCourse();
    }

    @Override
    public List<com.memory.entity.bean.Course> queryCourseByQueHql(int pageIndex, int limit, String course_title, String course_update_id, Integer course_online, String sort_status, String type_id, String album_id) {
        StringBuffer stringBuffer = new StringBuffer();
        List<com.memory.entity.bean.Course> list = new ArrayList<com.memory.entity.bean.Course>();

        stringBuffer.append(" SELECT new com.memory.entity.bean.Course (c.id,c.courseTypeId,c.albumId,c.courseNumber,c.courseTitle,c.courseLogo,c.courseContent," +
                "c.courseAudioUrl,c.courseVideoUrl,c.courseLabel,c.courseKeyWords,c.courseOnline,c.courseTotalView,c.courseTotalShare," +
                "c.courseTotalLike,c.courseTotalComment,c.courseReleaseTime,c.courseCreateTime,c.courseCreateId,c.courseUpdateTime,c.courseUpdateId," +
                "c.courseRecommend,c.courseDescribe,c.courseLiveStatus" +
                ",l.id,l.liveMasterName,l.courseId ) FROM Course c LEFT JOIN LiveMaster l ON c.id = l.courseId where 1=1 ");
        DaoUtils.Page page = daoUtils.getPage(pageIndex, limit);

        Map<String,Object> whereClause = getQueryWhere(course_title,course_update_id,course_online,sort_status,type_id,album_id);
        stringBuffer.append(whereClause.get("where"));
        Map<String,Object> map = (  Map<String,Object>) whereClause.get("param");

        list =   daoUtils.findByHQL(stringBuffer.toString(),map,page);
        System.out.println("hql ============= " + stringBuffer.toString() );
        System.out.println("where ==========="+whereClause.get("where").toString());
        System.out.println("map ============= " + JSON.toJSONString(map));
        return list;
    }

    @Override
    public int queryCourseCountByQueHql(String course_title, String course_update_id, Integer course_online, String sort_status, String type_id, String album_id) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT count(*) FROM Course c LEFT JOIN LiveMaster l ON c.id = l.courseId where 1=1 ");
        Map<String,Object> whereClause =  getQueryWhere(course_title,course_update_id,course_online,sort_status,type_id,album_id);
        Map<String,Object> map = (  Map<String,Object>) whereClause.get("param");
        stringBuffer.append(whereClause.get("where"));

        return daoUtils.getTotalByHQL(stringBuffer.toString(),map);
    }

    public Map<String,Object> getQueryWhere(String course_title, String course_update_id, Integer course_online, String sort_status, String type_id, String album_id){
        Map<String,Object> returnMap = new HashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        if(Utils.isNotNull(course_title)){
            stringBuffer.append(" AND c.courseTitle like :courseTitle");
            paramMap.put("courseTitle",'%'+ course_title+'%');
        }

        if(Utils.isNotNull(course_update_id) ){
            stringBuffer.append(" AND c.courseUpdateId = :courseUpdateId");
            paramMap.put("courseUpdateId",course_update_id);
        }

        if(Utils.isNotNull(course_online)){
            stringBuffer.append(" AND c.courseOnline = :courseOnline");
            paramMap.put("courseOnline",course_online);
        }

        if(Utils.isNotNull(type_id)){
            stringBuffer.append(" AND c.courseTypeId = :courseTypeId");
            paramMap.put("courseTypeId",type_id);
        }

        if(Utils.isNotNull(album_id)){
            stringBuffer.append(" AND c.albumId = :albumId");
            paramMap.put("albumId",album_id);
        }

        if(Utils.isNotNull(sort_status)  && sort_status.equals("asc") ){
            stringBuffer.append(" ORDER BY c.courseUpdateTime ASC");
        }else {
            stringBuffer.append(" ORDER BY c.courseUpdateTime DESC");
        }


        returnMap.put("where",stringBuffer.toString());
        returnMap.put("param",paramMap);
        return returnMap;
    }

    @Override
    public Course queryCourseByCourseTitle(String courseTitle) {
        return repository.queryCourseByCourseTitle(courseTitle);
    }

    @Override
    public Course queryCourseByCourseNumber(Integer courseNumber) {
        return repository.queryCourseByCourseNumber(courseNumber);
    }

    @Override
    public Course queryCourseByCourseTitle(String courseTitle, String id) {
        return repository.queryCourseByCourseTitle(courseTitle, id);
    }

    @Override
    public Course queryCourseByCourseNumber(Integer courseNumber, String id) {
        return repository.queryCourseByCourseNumber(courseNumber, id);
    }

    @Override
    public int countCourseByAlbumId(String albumId) {
        return repository.countCourseByAlbumIdAndCourseOnline(albumId,1);
    }

    @Override
    public List<Course> queryCourseByAlbumIdAndCourseOnline(String albumId, Integer online) {
        return repository.queryCourseByAlbumIdAndCourseOnline(albumId, online);
    }
}

