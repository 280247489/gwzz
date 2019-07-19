package com.memory.gwzz.service.impl;

import com.memory.entity.jpa.Course;
import com.memory.gwzz.repository.CourseWebRepository;
import com.memory.gwzz.service.CourseWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author INS6+
 * @date 2019/6/3 13:47
 */
@Service
public class CourseWebServiceImpl implements CourseWebService {

    @Autowired
    private CourseWebRepository repository;


    @Override
    public Course getCourseById(String id) {
        if(repository.findById(id).hashCode() != 0){
            return repository.findById(id).get();
        }else{
            return null;
        }

    }
}
