package com.memory.cms.service.impl;

import com.memory.cms.repository.CourseMemoryLoadRepository;
import com.memory.cms.service.CourseMemoryLoadService;
import com.memory.entity.jpa.CourseMemoryLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/28 14:54
 */
@Service
public class CourseMemoryLoadServiceImpl implements CourseMemoryLoadService {

    @Autowired
    private CourseMemoryLoadRepository repository;

    @Override
    public CourseMemoryLoad getCourseMemoryLoadById(String courseId) {
        if(repository.findById(courseId).hashCode() != 0){
            return repository.findById(courseId).get();
        }else{
            return null;
        }
    }

    @Override
    public CourseMemoryLoad updateCourseMemoryLoadById(CourseMemoryLoad courseMemoryLoad) {
        return repository.save(courseMemoryLoad);
    }

    @Override
    public CourseMemoryLoad addCourseMemoryLoad(CourseMemoryLoad courseMemoryLoad) {
        return repository.save(courseMemoryLoad);
    }

    @Override
    public List<CourseMemoryLoad> queryAllCourseMemoryLoadByLoadStatus(int load_status) {
        return repository.queryCourseMemoryLoadList(load_status);
    }
}
