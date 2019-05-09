package com.memory.cms.service.impl;

import com.memory.cms.entity.CourseExt;
import com.memory.cms.repository.CourseExtRepository;
import com.memory.cms.service.CourseExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 9:32
 */
@Service
public class CourseExtServiceImpl implements CourseExtService {

    @Autowired
    private CourseExtRepository repository;

    @Override
    public CourseExt getCourseExtById(Integer id) {
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
    public void deleteCourseExtByArticleId(Integer article_id) {
        repository.deleteCourseExtByArticleId(article_id);
    }

    @Override
    public List<CourseExt> queryCourseExtListByArticleId(Integer article_id) {
        return repository.queryCourseExtByArticleId(article_id);
    }


    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
