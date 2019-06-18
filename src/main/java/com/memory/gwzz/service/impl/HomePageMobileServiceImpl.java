package com.memory.gwzz.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Album;
import com.memory.gwzz.model.Article;
import com.memory.gwzz.model.Banner;
import com.memory.gwzz.model.Course;
import com.memory.gwzz.model.LiveMaster;
import com.memory.gwzz.service.HomePageMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HomePageMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/13 16:20
 */
@Service(value = "homePageMobileService")
public class HomePageMobileServiceImpl implements HomePageMobileService {

    @Autowired
    private DaoUtils daoUtils;

    @Override
    public Map<String, Object> HomePageOne() {
        Map<String,Object> returnMap = new HashMap<>();
        //查询轮播
        StringBuffer sbBannerList = new StringBuffer(" SELECT NEW com.memory.gwzz.model.Banner( id,typeTable,typeTableId,bannerLogo,bannerSort,bannerOnline) " +
                "FROM Banner WHERE bannerOnline=1 ORDER BY bannerSort ASC ");
        List<Banner> bannerList = daoUtils.findByHQL(sbBannerList.toString(),null,null);
//        //查询直播
//        StringBuffer sbliveMaster = new StringBuffer(" SELECT NEW com.memory.gwzz.model.LiveMaster(id,liveMasterName,liveMasterDescribe,liveMasterStatus,liveMasterIsOnline,liveMasterStarttime) " +
//                "FROM LiveMaster WHERE liveMasterIsOnline = 1 AND liveMasterIsOnline = 1 ");
//        LiveMaster liveMaster = (LiveMaster) daoUtils.findObjectHQL(sbliveMaster.toString(),null);
        //查询最新两期课程
        StringBuffer sbCourse = new StringBuffer( "SELECT NEW com.memory.gwzz.model.Course( id, courseTitle, courseLogo, courseLabel,courseOnline,courseTotalComment,courseTotalView,courseReleaseTime)  " +
                "FROM Course WHERE courseOnline =1 ORDER BY courseReleaseTime DESC");
        DaoUtils.Page pageCourse = new DaoUtils.Page();
        pageCourse.setPageIndex(1);
        pageCourse.setLimit(2);
        List<Course> courseList = daoUtils.findByHQL(sbCourse.toString(),null,pageCourse);

        //查询最新5篇养生文章  文章类型Id：tYmvO0Ub1558922279863
        StringBuffer sbArticle = new StringBuffer( "SELECT NEW com.memory.gwzz.model.Article( id, typeId, articleTitle, articleLogo1, articleLogo2, articleLogo3, " +
                "articleLabel, articleOnline, articleTotalComment, articleTotalView, articleReleaseTime)  " +
                "FROM Article WHERE articleOnline = 1 AND typeId = 'tYmvO0Ub1558922279863' ORDER BY articleReleaseTime DESC");
        DaoUtils.Page pageArticle = new DaoUtils.Page();
        pageArticle.setPageIndex(1);
        pageArticle.setLimit(5);
        List<Article> articleList = daoUtils.findByHQL(sbArticle.toString(),null,pageArticle);

        //查询专辑
        StringBuffer sbAlbum = new StringBuffer( " FROM Album WHERE albumIsOnline = 1 AND albumIsHomePage = 1 ORDER BY albumSort ASC");
        List<Album> albumList = daoUtils.findByHQL(sbAlbum.toString(),null,null);


        returnMap.put("bannerList",bannerList);
//        returnMap.put("liveMaster",liveMaster);
        returnMap.put("courseList",courseList);
        returnMap.put("articleList",articleList);
        returnMap.put("albumList",albumList);

        return returnMap;
    }
    @Override
    public Map<String, Object> HomePageTwo() {
        Map<String,Object> returnMap = new HashMap<>();
        //查询直播
        StringBuffer sbliveMaster = new StringBuffer(" SELECT NEW com.memory.gwzz.model.LiveMaster(id,liveMasterName,liveMasterDescribe,liveMasterStatus,liveMasterIsOnline,liveMasterStarttime) " +
                "FROM LiveMaster WHERE liveMasterIsOnline = 1 AND liveMasterIsOnline = 1 ");
        LiveMaster liveMaster = (LiveMaster) daoUtils.findObjectHQL(sbliveMaster.toString(),null);

        returnMap.put("liveMaster",liveMaster);

        return returnMap;
    }
}
