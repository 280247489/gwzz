package com.memory.gwzz.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Advertise;
import com.memory.entity.jpa.Album;
import com.memory.entity.jpa.ArticleLike;
import com.memory.entity.jpa.CourseLike;
import com.memory.gwzz.model.*;
import com.memory.gwzz.redis.service.ArticleRedisMobileService;
import com.memory.gwzz.repository.*;
import com.memory.gwzz.service.*;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.memory.redis.CacheConstantConfig.*;
import static com.memory.redis.CacheConstantConfig.COURSEMAP;

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

    @Autowired
    private ArticleMobileRepository articleMobileRepository;

    @Autowired
    private CourseMobileRepository courseMobileRepository;

    @Autowired
    private LiveMasterMobileRepository liveMasterMobileRepository;

    @Autowired
    private CourseMobileService courseMobileService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LiveMobileService liveMobileService;

    @Autowired
    private CourseLikeMobileService courseLikeMobileService;

    @Autowired
    private ArticleRedisMobileService articleRedisMobileService;


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
        StringBuffer sbCourse = new StringBuffer( "SELECT NEW com.memory.gwzz.model.Course( id, courseNumber,courseTitle, courseLogo, courseLabel,courseOnline,courseTotalComment,courseTotalView,courseReleaseTime)  " +
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
        pageArticle.setLimit(4);
        List<Article> articleList = daoUtils.findByHQL(sbArticle.toString(),null,pageArticle);
        //重写文章阅读量
        for (int i = 0;i<articleList.size();i++){
            String articleId = articleList.get(i).getId();
            articleList.get(i).setArticleTotalView(articleRedisMobileService.getArticleView(articleId));
        }


        //查询专辑
        StringBuffer sbAlbum = new StringBuffer( " FROM Album WHERE albumIsOnline = 1 AND albumIsHomePage = 1 ORDER BY albumSort ASC");
        List<Album> albumList = daoUtils.findByHQL(sbAlbum.toString(),null,null);

        StringBuffer sbNewCourse = new StringBuffer( "SELECT NEW com.memory.gwzz.model.Course( id, courseNumber,courseTitle, courseLogo, courseLabel,courseOnline,courseTotalComment,courseTotalView,courseReleaseTime)  " +
                "FROM Course WHERE courseOnline =1 ORDER BY courseReleaseTime DESC");
        DaoUtils.Page pageNewCourse = new DaoUtils.Page();
        pageNewCourse.setPageIndex(1);
        pageNewCourse.setLimit(1);
        List<Course> newCourseList = daoUtils.findByHQL(sbNewCourse.toString(),null,pageNewCourse);


        returnMap.put("bannerList",bannerList);
//        returnMap.put("liveMaster",liveMaster);
        returnMap.put("courseList",courseList);
        returnMap.put("articleList",articleList);
        returnMap.put("albumList",albumList);
        returnMap.put("newCourseList",newCourseList);

        return returnMap;
    }
    @Override
    public LiveMaster HomePageTwo() {
        //查询直播
        StringBuffer sbliveMaster = new StringBuffer(" SELECT NEW com.memory.gwzz.model.LiveMaster(id,liveMasterName,liveMasterDescribe,liveMasterStatus,liveMasterIsOnline,liveMasterStarttime) " +
                "FROM LiveMaster WHERE liveMasterIsOnline = 1 AND liveMasterStatus = 1 ");
        LiveMaster liveMaster = (LiveMaster) daoUtils.findObjectHQL(sbliveMaster.toString(),null);

        return liveMaster;
    }

    @Override
    public Map<String, Object> getAdvertiseById(com.memory.entity.jpa.Banner banner, String userId, String openId, Integer terminal, Integer os) {
        Map<String, Object> returnMap = new HashMap<>();
        String type = banner.getTypeTable();
        String typeId = banner.getTypeTableId();
        Integer isLike = 0;
        if ("Article".equals(type)) {
            com.memory.entity.jpa.Article article = articleMobileRepository.findByIdAndArticleOnline(typeId, 1);
            if (article != null) {
                String label = article.getArticleLabel();
                String[] labels = label.split(",");
                article.setArticleLabel(labels[0]);
                returnMap.put("article", article);
                isLike=articleRedisMobileService.isLike(article.getId(),userId);
                returnMap.put("isLike",isLike);
            } else {
                returnMap = null;
            }
        } else if ("Course".equals(type)) {
            com.memory.entity.jpa.Course course = courseMobileRepository.findByIdAndCourseOnline(typeId, 1);
            if (course != null) {
                String isLive = "noData";

                String albumId = course.getAlbumId();
                com.memory.entity.jpa.LiveMaster liveMaster = liveMasterMobileRepository.findByCourseIdAndLiveMasterIsOnline(typeId, 1);
                if (liveMaster != null) {
                    isLive = liveMaster.getId();
                }
                returnMap.put("isLike",courseLikeMobileService.isCourseLike(course.getId(),userId));
                returnMap.put("course", course);
                returnMap.put("isLive", isLive);
                returnMap.put("courselist", courseMobileService.getCourseById(albumId));
            } else {
                returnMap = null;
            }
        } else if ("Live".equals(type)) {
            com.memory.entity.jpa.LiveMaster liveMaster1 = liveMasterMobileRepository.findByCourseIdAndLiveMasterIsOnline(typeId, 1);
            if (liveMaster1 != null) {
                String keyCourseView = COURSEVIEW + typeId;
                String keyCourseViewOs = "";
                String keyCourseViewComment = COURSECOMMENT + typeId;
                String keyCourseViewId = COURSEVIEWID + typeId;
                if (os == 1) {
                    if (terminal == 1) {
                        keyCourseViewOs = COURSEVIEWANDROIDH5 + typeId;
                    } else {
                        keyCourseViewOs = COURSEVIEWANDROIDAPP + typeId;
                    }
                } else {
                    if (terminal == 0) {
                        keyCourseViewOs = COURSEVIEWIOSAPP + typeId;
                    } else {
                        keyCourseViewOs = COURSEVIEWIOSH5 + typeId;
                    }
                }
                if (COURSEMAP.containsKey(keyCourseViewComment)) {
                    this.total2Redis(openId, keyCourseView, keyCourseViewOs, keyCourseViewId);
                } else {
                    Object object = redisUtil.hget(keyCourseViewComment,"slave");
                    if (object!=null){
                        if(!"notExist".equals(object)){
                            returnMap.put("master",redisUtil.hget(keyCourseViewComment,"master"));
                            returnMap.put("slave", JSON.parse(object.toString()));
                        }else {
                            returnMap = null;
                        }
                    }else {
                        com.memory.entity.jpa.LiveMaster master = (com.memory.entity.jpa.LiveMaster) daoUtils.getById("LiveMaster",typeId);
                        if(master!=null){
                            if(master.getLiveMasterIsOnline() ==0){
                                redisUtil.hset(keyCourseViewComment,"master","notExist");
                                redisUtil.hset(keyCourseViewComment,"slave",JSON.toJSONString("notExist"));
                                returnMap = null;
                            }else {
                                List<LiveSlave> list = liveMobileService.queryLiveSlaveList(typeId);
                                LiveSlave liveSlave = new LiveSlave();
                                List<Map<String,Object>> showList = liveSlave.refactorData(list);
                                redisUtil.hset(keyCourseViewComment,"master",master.getLiveMasterName());
                                redisUtil.hset(keyCourseViewComment,"slave",JSON.toJSONString(showList));
                                total2Redis(openId, keyCourseView, keyCourseViewOs, keyCourseViewId);
                                returnMap.put("master",master.getLiveMasterName());
                                returnMap.put("slave",showList);
                            }
                        }else {
                            returnMap = null;
                        }
                    }
                }
            }else{
                returnMap = null;
            }
        } else if ("Goods".equals(type)) {
            returnMap = null;
        } else {
            returnMap = null;
        }
        return returnMap;
    }

    private void total2Redis(String openId, String keyCourseView, String keyCourseViewOs, String keyCourseViewId) {
        redisUtil.incr(keyCourseView, 1);
        redisUtil.incr(keyCourseViewOs, 1);
        redisUtil.hincr(keyCourseViewId, openId, 1);
    }
}
