package com.memory.gwzz.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.gwzz.model.Course;
import com.memory.gwzz.redis.service.CourseRedisMobileService;
import com.memory.gwzz.service.CourseMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private CourseRedisMobileService courseRedisMobileService;

    /**
     * 根据详情查询播放列表
     * @param albumId
     * @return
     */
    public List<Course> getCourseById(String albumId){
        StringBuffer sbCourse = new StringBuffer( " SELECT NEW com.memory.gwzz.model.Course( id, courseNumber,courseTitle, courseLogo, courseLabel,courseOnline,courseTotalComment,courseTotalView,courseReleaseTime) " +
                "FROM Course WHERE  albumId=:albumId AND courseOnline=1 ORDER BY courseNumber DESC");
//        StringBuffer sbCourse = new StringBuffer( " FROM Course WHERE  albumId=:albumId AND courseOnline=1 ORDER BY courseReleaseTime DESC");
        Map<String,Object> map = new HashMap<>();
        map.put("albumId", albumId);
        List<Course> courseList = daoUtils.findByHQL(sbCourse.toString(),map,null);
        //重写课程阅读量
        for (int j = 0;j<courseList.size();j++){
            String courseId = courseList.get(j).getId();
            courseList.get(j).setCourseTotalView(courseRedisMobileService.getCourseView(courseId));
        }
        return courseList;
    }

    @Override
    public Map<String,Object> fandCourseByKey(String albumId,Integer start,Integer limit ,String key){
        Map<String,Object> returnMap = new HashMap<>();
            StringBuffer sbCourse = new StringBuffer( "SELECT  id, album_id,course_number,course_title, course_logo, course_label,course_key_words,course_online,course_total_comment,course_total_view,course_release_time " +
                    " FROM course WHERE course_online=1  " );
            StringBuffer sbCount = new StringBuffer("SELECT COUNT(*) FROM course WHERE  course_online=1");
//            StringBuffer stringBuffer = new StringBuffer("SELECT course_number FROM course where album_id=:albumId AND course_online=1 ORDER BY course_number ASC");
            Map<String,Object> map = new HashMap<>();
            if (!"".equals(albumId)&& albumId!=null){
                sbCourse.append( "AND album_id =:albumId ");
                sbCount.append( " AND album_id =:albumId ");
                map.put("albumId", albumId);
            }
            if (!"".equals(key)&& key!=null){
                sbCourse.append(" AND CONCAT(course_title,course_label,course_key_words) LIKE :key");
                sbCount.append(" AND CONCAT(course_title,course_label,course_key_words) LIKE :key");
                map.put("key", "%"+key+"%");
            }
            sbCourse.append(" ORDER BY course_release_time DESC");

            DaoUtils.Page page = new DaoUtils.Page();
            page.setPageIndex(start);
            page.setLimit(limit);
            List<Object[]> courseList = daoUtils.findBySQL(sbCourse.toString(),map,page,null);
            Integer count = daoUtils.getTotalBySQL(sbCount.toString(),map);
            List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
            for (int i = 0; i < courseList.size(); i++) {
                String courseId = (String) courseList.get(i)[0];
                Integer courseTotalView = courseRedisMobileService.getCourseView(courseId);
                Map<String, Object> objMap=new HashMap<String, Object>();
                objMap.put("id", courseId);
                objMap.put("albumId", courseList.get(i)[1]);
                objMap.put("courseNumber", courseList.get(i)[2]);
                objMap.put("courseTitle", courseList.get(i)[3]);
                objMap.put("courseLogo", courseList.get(i)[4]);
                objMap.put("courseLabel", courseList.get(i)[5]);
                objMap.put("courseKeyWords", courseList.get(i)[6]);
                objMap.put("courseOnline", courseList.get(i)[7]);
                objMap.put("courseTotalComment", courseList.get(i)[8]);
                objMap.put("courseTotalView", courseTotalView);
                objMap.put("courseReleaseTime", courseList.get(i)[10]);

                returnList.add(objMap);
            }
//            Object objectList = daoUtils.findBySQL(stringBuffer.toString(),map,null,null);
//            returnMap.put("anthology",objectList);
            returnMap.put("courseList",returnList);
            returnMap.put("count",count);

        return returnMap;
    }
}
