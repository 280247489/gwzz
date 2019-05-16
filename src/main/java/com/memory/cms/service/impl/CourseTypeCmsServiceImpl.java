package com.memory.cms.service.impl;

import com.memory.cms.repository.CourseTypeCmsRepository;
import com.memory.cms.service.CourseTypeCmsService;
import com.memory.entity.jpa.CourseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:22
 */
@Service
public class CourseTypeCmsServiceImpl implements CourseTypeCmsService {

    @Autowired
    private CourseTypeCmsRepository repository;

    @Override
    public List<CourseType> queryCourseTypeList() {
            return repository.queryAllCourseTypeListByTypeCreateTimeDesc();
    }

    @Override
    public CourseType add(CourseType courseType) {
        return repository.save(courseType);
    }


    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<CourseType> queryCourseTypeList(Integer isUse) {
        return repository.queryCourseTypeList(isUse);
    }
}
