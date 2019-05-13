package com.memory.cms.service.impl;

import com.memory.cms.entity.ArticleType;
import com.memory.cms.entity.CourseType;
import com.memory.cms.repository.ArticleTypeRepository;
import com.memory.cms.repository.CourseTypeRepository;
import com.memory.cms.service.ArticleTypeService;
import com.memory.cms.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/9 16:22
 */
@Service
public class CourseTypeServiceImpl implements CourseTypeService {

    @Autowired
    private CourseTypeRepository repository;

    @Override
    public List<CourseType> queryCourseTypeList() {
            return repository.findAll();
    }

    @Override
    public CourseType add(CourseType courseType) {
        return repository.save(courseType);
    }


    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
