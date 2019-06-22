package com.memory.gwzz.controller;

import com.alibaba.fastjson.JSON;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.LiveMaster;
import com.memory.gwzz.model.LiveSlave;
import com.memory.gwzz.service.LiveMobileService;
import com.memory.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.memory.redis.CacheConstantConfig.*;

/**
 * @ClassName LiveMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/21 16:33
 */
@RestController
@RequestMapping("live/mobile")
public class LiveMobileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(LiveMobileController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private LiveMobileService liveMobileService;

    /**
     * 查询直播详情
     * URL:192.168.1.185:8081/gwzz/live/mobile/getLiveById
     * @param id 直播ID
     * @param openId  微信openId 或 appId
     * @param terminal 终端  0 ：app 或  1 ：h5
     * @param os 操作系统 0：ios 或 1：android
     * @return
     */
    @RequestMapping(value = "getLiveById",method = RequestMethod.POST)
    public Message getLiveById(String id, String openId,Integer terminal,Integer os){
        try {
            msg = Message.success();
            String keyCourseView = COURSEVIEW + id;
            String keyCourseViewOs ="";
            String keyCourseViewComment = COURSECOMMENT + id;
            String keyCourseViewId= COURSEVIEWID +id;

            // ios
            if(os == 0){
                //app
                if(terminal == 0){
                    keyCourseViewOs = COURSEVIEWIOSAPP + id;
                    //h5
                }else {
                    keyCourseViewOs = COURSEVIEWIOSH5 +id;
                }
                // android
            }else {
                //app
                if(terminal == 0){
                    keyCourseViewOs = COURSEVIEWANDROIDAPP + id;
                    //h5
                }else {
                    keyCourseViewOs = COURSEVIEWANDROIDH5 +id;
                }
            }

            //内存
            if(COURSEMAP.containsKey(keyCourseViewComment)){
                total2Redis(openId, keyCourseView, keyCourseViewOs, keyCourseViewId);
                System.out.println("内存==============================="+keyCourseViewComment);
                msg.setData(COURSEMAP.get(keyCourseViewComment));
            }else {
                Object object = redisUtil.hget(keyCourseViewComment,"slave");
                //判断redis中是否含有此次课程数据
                //有课程数据
                if(object != null){
                    if(!"notExist".equals(object)){
                        Map<String,Object> map = new HashMap<>();
                        map.put("master",redisUtil.hget(keyCourseViewComment,"master"));
                        map.put("slave", JSON.parse(object.toString()));
                        total2Redis(openId, keyCourseView, keyCourseViewOs, keyCourseViewId);
                        System.out.println("redis==============================="+keyCourseViewComment);
                        msg.setData(map);
                    }else {
                        msg.setRecode(1);
                        msg.setMsg("notExist");
                    }
                    //redis没有课程数据
                }else {
                    LiveMaster master = (LiveMaster) daoUtils.getById("LiveMaster",id);
                    if(master!=null){
                        //上线
                        if(master.getLiveMasterIsOnline() ==1){
                            System.out.println("redis没有课程数据，数据库中有课程数据，数据库的课程是上线状态,同步db2redis=============================="+keyCourseViewComment);
                            List<LiveSlave> list = liveMobileService.queryLiveSlaveList(id);
                            com.memory.gwzz.model.LiveSlave liveSlave = new    com.memory.gwzz.model.LiveSlave();
                            List<Map<String,Object>> showList = liveSlave.refactorData(list);

                            redisUtil.hset(keyCourseViewComment,"master",master.getLiveMasterName());
                            redisUtil.hset(keyCourseViewComment,"slave",JSON.toJSONString(showList));

                            Map<String,Object> map = new HashMap<>();
                            map.put("master",master.getLiveMasterName());
                            map.put("slave",showList);
                            total2Redis(openId, keyCourseView, keyCourseViewOs, keyCourseViewId);
                            msg.setData(map);
                            //下线
                        }else {
                            System.out.println("redis没有课程数据，数据库中有数据，但是课程是下线状态=============================="+keyCourseViewComment);
                            redisUtil.hset(keyCourseViewComment,"master","notExist");
                            redisUtil.hset(keyCourseViewComment,"slave",JSON.toJSONString("notExist"));
                            msg.setRecode(1);
                            msg.setMsg("notExist");
                        }
                    }else {
                        System.out.println("redis没有课程数据,数据库也没有数据=============================="+keyCourseViewComment);
                        msg.setRecode(1);
                        msg.setMsg("notExist");
                    }
                }
            }
        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }

    private void total2Redis( String openId, String keyCourseView, String keyCourseViewOs, String keyCourseViewId) {
        redisUtil.incr(keyCourseView,1);
        redisUtil.incr(keyCourseViewOs,1);
        redisUtil.hincr(keyCourseViewId,openId,1);
    }
}
