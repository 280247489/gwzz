package com.memory.gwzz.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.gwzz.model.Course;
import com.memory.gwzz.service.CourseMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/22 9:16
 */
@Service(value = "courseMobileService")
public class CourseMobileServiceImpl  implements CourseMobileService {

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 根据详情查询播放列表
     * @param albumId
     * @return
     */
    public List<Course> getCourseById(String albumId){
        StringBuffer sbCourse = new StringBuffer( " SELECT NEW com.memory.gwzz.model.Course( id, courseNumber,courseTitle, courseLogo, courseLabel,courseOnline,courseTotalComment,courseTotalView,courseReleaseTime) " +
                "FROM Course WHERE  albumId=:albumId AND courseOnline=1 ORDER BY courseReleaseTime DESC");
        Map<String,Object> map = new HashMap<>();
        map.put("albumId", albumId);
        List<Course> courseList = daoUtils.findByHQL(sbCourse.toString(),map,null);
        return courseList;
    }
}
