package com.memory.cms.service.impl;

import com.memory.entity.jpa.CourseExt;
import com.memory.cms.repository.CourseExtCmsRepository;
import com.memory.cms.service.CourseExtCmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:32
 */
@Service
public class CourseExtCmsServiceImpl implements CourseExtCmsService {

    @Autowired
    private CourseExtCmsRepository repository;

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
        //return repository.queryCourseExtList(article_id);
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
}
