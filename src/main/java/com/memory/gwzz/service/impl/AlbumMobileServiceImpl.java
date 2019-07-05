package com.memory.gwzz.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Album;
import com.memory.entity.jpa.Course;
import com.memory.gwzz.service.AlbumMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName AlbumMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/18 14:59
 */
@Service(value = "albumMobileService")
public class AlbumMobileServiceImpl implements AlbumMobileService {

    @Autowired
    private DaoUtils daoUtils;



    @Override
    public Map<String,Object> fandAlbum(Integer start,Integer limit){
        Map<String,Object> returnMap = new HashMap<>();
        //查询专辑
        StringBuffer sbAlbum = new StringBuffer( " FROM Album WHERE albumIsOnline = 1  ORDER BY albumSort ASC");
        StringBuffer sbAlbumCount = new StringBuffer( "SELECT COUNT(*) FROM album WHERE album_is_online = 1  ");
        DaoUtils.Page page = new DaoUtils.Page();
        page.setPageIndex(start);
        page.setLimit(limit);
        List<Album> albumList = daoUtils.findByHQL(sbAlbum.toString(),null,page);
        Integer albumCount = daoUtils.getTotalBySQL(sbAlbumCount.toString(),null);

        returnMap.put("albumList",albumList);
        returnMap.put("albumCount",albumCount);

        return returnMap;
    }


    @Override
    public Map<String,Object> fandById(String id){
        Map<String,Object> returnMap = new HashMap<>();
        Album album = (Album) daoUtils.getById("Album",id);
        returnMap.put("album",album);
        return returnMap;
    }

    @Override
    public Map<String,Object> fandCourseByAlbunmId(String albumId,Integer start){
        Map<String,Object> returnMap = new HashMap<>();
        Album album = (Album) daoUtils.getById("Album",albumId);
        Integer limit = album.getAlbumCourseLimit();
        if (album!=null){
            StringBuffer sbCourse = new StringBuffer( "SELECT NEW com.memory.gwzz.model.Course( id, courseNumber,courseTitle, courseLogo, courseLabel,courseOnline,courseTotalComment,courseTotalView,courseReleaseTime) " +
                    " FROM Course WHERE  albumId=:albumId AND courseOnline=1 ORDER BY courseReleaseTime DESC");
            StringBuffer stringBuffer = new StringBuffer("SELECT course_number FROM course where album_id=:albumId AND course_online=1 ORDER BY course_number ASC");
            Map<String,Object> map = new HashMap<>();
            map.put("albumId", albumId);
            DaoUtils.Page page = new DaoUtils.Page();
            page.setPageIndex(start);
            page.setLimit(limit);
            List<Course> courseList = daoUtils.findByHQL(sbCourse.toString(),map,page);
            Object objectList = daoUtils.findBySQL(stringBuffer.toString(),map,null,null);
            returnMap.put("anthology",objectList);
            returnMap.put("courseList",courseList);
        }
        returnMap.put("album",album);

        return returnMap;
    }

}
