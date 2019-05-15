package com.memory.cms.service.impl;

import com.memory.entity.jpa.Course;
import com.memory.cms.repository.CourseCmsRepository;
import com.memory.cms.service.CourseCmsService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/8 10:56
 */
@Service
public class CourseCmsServiceImpl implements CourseCmsService {

    @Autowired
    private CourseCmsRepository repository;


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
    public Page<Painter> queryCourseByQue(Pageable pageable,String course_title,String course_update_id,Integer course_online,String sort_status,String course_type_id) {
        Specification specification =new Specification<Painter>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if(!"".equals(course_title)){
                    list.add(cb.like(root.get("courseTitle"),"%" + course_title + "%"));
                }

                if(!"".equals(course_update_id)){
                    list.add(cb.equal(root.get("courseUpdateId"),course_update_id));
                }

                if(course_online != null){
                    list.add(cb.equal(root.get("courseOnline"),course_online));
                }

                if(!"".equals(course_type_id)){
                    list.add(cb.equal(root.get("courseTypeId"),course_type_id));
                }

                Predicate[] p = new Predicate[list.size()];
                query.where(cb.and(list.toArray(p)));

               if(sort_status != null && !"".equals(sort_status) && sort_status.equals("asc")){

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
}

