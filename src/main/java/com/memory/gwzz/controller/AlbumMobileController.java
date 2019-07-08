package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Album;
import com.memory.gwzz.service.AlbumMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName AlbumMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/18 15:13
 */
@RestController
@RequestMapping(value = "album/mobile")
public class AlbumMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(AlbumMobileController.class);
    @Autowired
    private AlbumMobileService albumMobileService;

    @Autowired
    private DaoUtils daoUtils;


    /**
     * 查询课程列表
     * URL:192.168.1.185:8081/gwzz/album/mobile/findAlbum
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "findAlbum",method = RequestMethod.POST)
    public Message findAlbum(@RequestParam Integer start, @RequestParam Integer limit){
        try {
            msg = Message.success();
            Map<String,Object> returnMap = albumMobileService.fandAlbum(start,limit);
            msg.setMsg("成功");
            msg.setRecode(0);
            msg.setData(returnMap);
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 查询专辑详情
     * URL:192.168.1.185:8081/gwzz/album/mobile/findById
     * @param id
     * @return
     */
    @RequestMapping(value = "findById",method = RequestMethod.POST)
    public Message findById(@RequestParam String id){
        try {
            msg = Message.success();
            Album album = (Album) daoUtils.getById("Album",id);
            if (album!=null){
                Map<String,Object> returnMap =albumMobileService.fandById(id);
                msg.setMsg("成功");
                msg.setRecode(0);
                msg.setData(returnMap);
            }else {
                msg.setMsg("无此数据");
                msg.setRecode(2);
            }

        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 查找专辑中课程列表
     * URL:192.168.1.185:8081/gwzz/album/mobile/fandCourseByAlbunmId
     * @param id
     * @param start
     * @return
     */
    @RequestMapping(value = "fandCourseByAlbunmId",method = RequestMethod.POST)
    public Message fandCourseByAlbunmId(@RequestParam String id,@RequestParam Integer start){
        try {
            msg = Message.success();
            Map<String,Object> returnMap = albumMobileService.fandCourseByAlbunmId(id, start);
            msg.setMsg("成功");
            msg.setRecode(0);
            msg.setData(returnMap);
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }
}
