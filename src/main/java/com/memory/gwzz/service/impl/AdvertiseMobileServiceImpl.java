package com.memory.gwzz.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.*;
import com.memory.gwzz.controller.AdvertiseMobileController;
import com.memory.gwzz.controller.ArticleMobileController;
import com.memory.gwzz.redis.service.ArticleRedisMobileService;
import com.memory.gwzz.redis.service.LiveRedisMobileService;
import com.memory.gwzz.repository.*;
import com.memory.gwzz.service.*;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.memory.redis.CacheConstantConfig.*;

/**
 * @ClassName AdvertiseMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/31 9:08
 */
@Service("advertiseCommentMobileService")
public class AdvertiseMobileServiceImpl implements AdvertiseMobileService {

    @Autowired
    private AdvertiseMobileRepository advertiseMobileRepository;

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
    private DaoUtils daoUtils;

    @Autowired
    private LiveMobileService liveMobileService;

    @Autowired
    private LiveRedisMobileService liveRedisMobileService;

    @Autowired
    private CourseLikeMobileService courseLikeMobileService;

    @Autowired
    private ArticleRedisMobileService articleRedisMobileService;


    @Override
    public List<Advertise> getAdvertiseOnline() {
        return advertiseMobileRepository.findByAdvertiseOnlineAndAdvertiseType(1,1);
    }

    @Override
    public Map<String, Object> getAdvertiseById(Advertise advertise, String userId, String openId, Integer terminal, Integer os) {
        Map<String, Object> returnMap = new HashMap<>();
        String type = advertise.getAdvertiseH5Type();
        String typeId = advertise.getAdvertiseH5Url();
        Integer isLike = 0;
        if ( "Article".equals(type)) {
            Article article = articleMobileRepository.findByIdAndArticleOnline(typeId, 1);
            if (article != null) {
                String label = article.getArticleLabel();
                String[] labels = label.split(",");
                article.setArticleLabel(labels[0]);
                isLike=articleRedisMobileService.isLike(article.getId(),userId);
                returnMap.put("isLike",isLike);
                returnMap.put("article", article);
                returnMap.put("isLike",articleRedisMobileService.isLike(article.getId(),userId));
            } else {
                returnMap = null;
            }
        } else if ("Course".equals(type)) {
            Course course = courseMobileRepository.findByIdAndCourseOnline(typeId, 1);
            if (course != null) {
                String isLive = "noData";
                String albumId = course.getAlbumId();
                LiveMaster liveMaster = liveMasterMobileRepository.findByCourseIdAndLiveMasterIsOnline(typeId, 1);
                if (liveMaster != null) {
                    isLive = liveMaster.getId();
                }
                returnMap.put("course", course);
                returnMap.put("isLive", isLive);
                returnMap.put("isLike",courseLikeMobileService.isCourseLike(course.getId(),userId));
                returnMap.put("courseList", courseMobileService.getCourseById(albumId));
            } else {
                returnMap = null;
            }
        } else if ( "Live".equals(type)) {
            LiveMaster liveMaster1 = liveMasterMobileRepository.findByCourseIdAndLiveMasterIsOnline(typeId, 1);
            if (liveMaster1 != null) {
                String keyCourseViewComment = SHARELIVECONTENT + typeId;

                if (LIVEMAP.containsKey(keyCourseViewComment)) {
                    liveRedisMobileService.liveView(typeId,userId,terminal,os);
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
                        LiveMaster master = (LiveMaster) daoUtils.getById("LiveMaster",typeId);
                        if(master!=null){
                            if(master.getLiveMasterIsOnline() ==1){
                                List<com.memory.gwzz.model.LiveSlave> list = liveMobileService.queryLiveSlaveList(typeId);
                                com.memory.gwzz.model.LiveSlave liveSlave = new  com.memory.gwzz.model.LiveSlave();
                                List<Map<String,Object>> showList = liveSlave.refactorData(list);

                                redisUtil.hset(keyCourseViewComment,"master",master.getLiveMasterName());
                                redisUtil.hset(keyCourseViewComment,"slave",JSON.toJSONString(showList));
                                liveRedisMobileService.liveView(typeId,userId,terminal,os);
                                returnMap.put("master",master.getLiveMasterName());
                                returnMap.put("slave",showList);
                            }else {
                                redisUtil.hset(keyCourseViewComment,"master","notExist");
                                redisUtil.hset(keyCourseViewComment,"slave",JSON.toJSONString("notExist"));
                                returnMap = null;
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



}