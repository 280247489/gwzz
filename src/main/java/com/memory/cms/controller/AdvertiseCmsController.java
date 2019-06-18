package com.memory.cms.controller;

import com.memory.cms.service.AdvertiseCmsService;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Advertise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName AdvertiseCmsController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 13:39
 */
@RestController
@RequestMapping(value = "advertise/cms")
public class AdvertiseCmsController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AdvertiseCmsController.class);

    @Autowired
    private AdvertiseCmsService advertiseCmsService;

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 添加广告接口
     * URL:192.168.1.185:8081/gwzz/advertise/cms/add
     * @param aName 名称 String
     * @param aLogo 图片 file
     * @param aH5Type 外部:url、内部[文章article;课程course;商品goods;直播live] String
     * @param aType 类型 1.广告 2.首页弹层 Int
     * @param aH5Url 连接 String
     * @param createId 添加人ID String
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Message add(@RequestParam  String aName, @RequestParam MultipartFile aLogo, @RequestParam Integer aType,
                       @RequestParam String aH5Type, @RequestParam String aH5Url,@RequestParam  String createId){
        msg = Message.success();
        try {
            if (advertiseCmsService.checkAdvertiseName(aName,"")==null){
                advertiseCmsService.addAdvertise(aName, aLogo, aType, aH5Type, aH5Url, createId);
                msg.setMsg("添加成功");
                msg.setRecode(0);

            }else{
                msg.setMsg("该名称已存在");
                msg.setRecode(2);
            }

        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");

        }
        return msg;
    }

    /**
     * 查询广告内容列表
     * URL:192.168.1.185:8081/gwzz/advertise/cms/list
     * @param page
     * @param size
     * @param direction
     * @param sorts
     * @param aName 名称 String
     * @param aType  类型 1.广告 2.首页弹层 Int
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Message queryArticleList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(defaultValue = "desc") String direction,@RequestParam(defaultValue = "advertiseUpdateTime") String sorts,
                                    @RequestParam String aName, @RequestParam Integer aType) {
        msg = Message.success();
        try {
            Pageable pageable = PageRequest.of(page, size,direction.toLowerCase().equals("asc")? Sort.Direction.ASC:Sort.Direction.DESC,sorts);
            Page<Advertise> list = advertiseCmsService.findAdvertise(pageable,aName,aType);
            msg.setRecode(0);
            msg.setData(list);

        } catch (Exception e) {
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }


    /**
     * 根据Id查询广告信息
     * URL:192.168.1.185:8081/gwzz/advertise/cms/getById
     * @param id String 唯一标识
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Message getById(@RequestParam String id){
        msg = Message.success();
        try {
            Advertise advertise = (Advertise) daoUtils.getById("Advertise", id);
            if (advertise!=null){
                msg.setRecode(0);
                msg.setData(advertise);
            }else {
                msg.setRecode(2);
                msg.setMsg("无此信息");
            }
        }catch (Exception e) {
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }

        return msg;
    }

    /**
     * 根据Id修改信息
     * URL:192.168.1.185:8081/gwzz/advertise/cms/upd
     * @param id 唯一标识 String
     * @param aName 名称 String
     * @param aLogo 图片 File
     * @param aH5Type 外部:url、内部[文章article;课程course;商品goods;直播live] String
     * @param aH5Url 连接 String
     * @param createId 修改人 String
     * @return Advertise 对象
     */
    @RequestMapping(value = "upd", method = RequestMethod.POST)
    public Message upd (@RequestParam String id, @RequestParam String aName, @RequestParam MultipartFile aLogo,@RequestParam String aH5Type,
                        @RequestParam String aH5Url, @RequestParam String createId){
        msg = Message.success();
        try {
           Advertise advertise =  advertiseCmsService.checkAdvertiseName(aName,id);
           if (advertise==null){
               advertise = (Advertise) daoUtils.getById("Advertise", id);
               if (advertise!=null){
                   msg.setRecode(0);
                   msg.setMsg("修改成功");
                   msg.setData(advertiseCmsService.upd(advertise,aName,aLogo,aH5Type,aH5Url,createId));
               }else{
                   msg.setRecode(2);
                   msg.setMsg("无此信息");
               }

           }else{
               msg.setRecode(3);
               msg.setMsg("已存在该名称");
           }

        }catch (Exception e) {
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

    /**
     * 设置上下线
     * URL:192.168.1.185:8081/gwzz/advertise/cms/updOnline
     * @param id 唯一标识 String
     * @return Advertise 对象
     */
    @RequestMapping(value = "updOnline",method = RequestMethod.POST)
    public Message updOnline(@RequestParam String id){
        msg = Message.success();
        try {

            Advertise  advertise = (Advertise) daoUtils.getById("Advertise", id);
            if (advertise!=null){
                msg.setRecode(0);
                msg.setMsg("修改成功");
                msg.setData( advertiseCmsService.updOnine(advertise));
            }else{
                msg.setRecode(2);
                msg.setMsg("无此信息");
            }
        }catch (Exception e) {
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

    /**
     * 删除广告
     * URL:192.168.1.185:8081/gwzz/advertise/cms/del
     * @param id 唯一标识 String
     * @return
     */
    @RequestMapping(value = "del",method = RequestMethod.POST)
    public Message del(@RequestParam String id){
        msg = Message.success();
        try {
            Advertise  advertise = (Advertise) daoUtils.getById("Advertise", id);
            if (advertise!=null){
                advertiseCmsService.del(advertise);
                msg.setRecode(0);
                msg.setMsg("删除成功");
            }else{
                msg.setRecode(2);
                msg.setMsg("无此信息");
            }
        }catch (Exception e) {
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

}
