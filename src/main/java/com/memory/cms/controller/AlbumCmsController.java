package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.AlbumRedisCmsService;
import com.memory.cms.service.AlbumCmsService;
import com.memory.cms.service.CourseCmsService;
import com.memory.common.utils.*;
import com.memory.entity.jpa.Album;
import com.memory.entity.jpa.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author INS6+
 * @date 2019/6/13 17:30
 */
@RestController
@RequestMapping("album/cms")
public class AlbumCmsController {

    private static final Logger log = LoggerFactory.getLogger(AlbumCmsController.class);

    @Autowired
    private AlbumCmsService albumCmsService;

    @Autowired
    private CourseCmsService courseCmsService;

    @Autowired
    private AlbumRedisCmsService albumRedisCmsService;

    @RequestMapping("add")
    public Result add(@ModelAttribute com.memory.entity.bean.Album album){
        Result result = new Result();
        try {
            if(isExistAlbumName(album.getAlbum_name())){
                return ResultUtil.error(2,"专辑名称重复！");
            }

            String uuid = Utils.getShortUUTimeStamp();
            String album_logo = "";
            if(Utils.isNotNull(album.getLogoFile())){
                album_logo =uploadImg(uuid,album.getLogoFile());
            }



            Album albumInit = new Album();
            albumInit.setId(uuid);
            albumInit.setAlbumName(album.getAlbum_name());
            albumInit.setAlbumLogo(album_logo);
            albumInit.setAlbumIsOnline(0);
            albumInit.setAlbumCourseSum(0);
            albumInit.setAlbumTotalView(0);
            albumInit.setAlbumIsEnd(0);
            albumInit.setAlbumIsCharge(0);
            albumInit.setAlbumChargePrice(0);
            albumInit.setAlbumCreateTime(new Date());
            albumInit.setAlbumCreateId(album.getOperator_id());
            albumInit.setAlbumUpdateTime(new Date());
            albumInit.setAlbumUpdateId(album.getOperator_id());
            albumInit.setAlbumSynopsis(album.getAlbum_synopsis());
            albumInit.setAlbumCourseLimit(album.getAlbum_course_limit());

            albumInit = albumCmsService.add(albumInit);
            result = ResultUtil.success(albumInit);
        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/add",e.getMessage());
        }
        return result;
    }

    @RequestMapping("update")
    public Result update(@ModelAttribute com.memory.entity.bean.Album album){
        Result result = new Result();
        try {

            if(isExistAlbumName(album.getAlbum_name(),album.getId())){
                return ResultUtil.error(2,"专辑名称重复！");
            }

            String uuid = album.getId();
            Album albumInit = albumCmsService.getAlbumById(uuid);
            if(albumInit == null){
                return ResultUtil.error(-1,"非法专辑id");
            }
            albumInit.setAlbumName(album.getAlbum_name());
            if(Utils.isNotNull(album.getLogoFile())){
                String imgUrl = uploadImg(uuid,album.getLogoFile());
                System.out.println("imgUrl = " + imgUrl);
                albumInit.setAlbumLogo(imgUrl);
            }

            albumInit.setAlbumSynopsis(album.getAlbum_synopsis());
            albumInit.setAlbumCourseLimit(album.getAlbum_course_limit());
            albumInit.setAlbumUpdateId(album.getOperator_id());
            albumInit.setAlbumUpdateTime(new Date());

            albumInit= albumCmsService.update(albumInit);

            result = ResultUtil.success(albumInit);
        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/update",e.getMessage());
        }
        return result;
    }

    @RequestMapping("online")
    public Result online(@ModelAttribute com.memory.entity.bean.Album album){
        Result result = new Result();
        try {
            String uuid = album.getId();
            Album albumInit = albumCmsService.getAlbumById(uuid);
            if(albumInit == null){
                return ResultUtil.error(-1,"非法专辑id");
            }

            if(albumInit.getAlbumCourseSum() >0){
                albumInit.setAlbumIsOnline(album.getAlbum_is_online());
                albumInit.setAlbumUpdateId(album.getOperator_id());
                albumInit.setAlbumUpdateTime(new Date());
                albumInit= albumCmsService.update(albumInit);
                if(albumInit!=null){
                    result = ResultUtil.success(albumInit);
                }else {
                    result = ResultUtil.error(-1,"系统异常!");
                }
            }else{
                result = ResultUtil.error(-1,"专辑下没有课程，请添加课程!");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/online",e.getMessage());
        }
        return result;
    }


    @RequestMapping("isEnd")
    public Result isEnd(@ModelAttribute com.memory.entity.bean.Album album){
        Result result = new Result();
        try {
            String uuid = album.getId();
            Album albumInit = albumCmsService.getAlbumById(uuid);
            if(albumInit == null){
                return ResultUtil.error(-1,"非法专辑id");
            }
            albumInit.setAlbumIsEnd(album.getAlbum_is_end());
            albumInit.setAlbumUpdateId(album.getOperator_id());
            albumInit.setAlbumUpdateTime(new Date());
            albumInit = albumCmsService.update(albumInit);
            if(albumInit!=null){
                result = ResultUtil.success(albumInit);
            }else {
                result = ResultUtil.error(-1,"系统异常!");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/isEnd",e.getMessage());
        }
        return result;
    }

    @RequestMapping("isHomePage")
    public Result isHomePage(@ModelAttribute com.memory.entity.bean.Album album){
        Result result = new Result();
        try {
            String uuid = album.getId();
            Album albumInit = albumCmsService.getAlbumById(uuid);
            if(albumInit == null){
                return ResultUtil.error(-1,"非法专辑id");
            }
            albumInit.setAlbumIsHomePage(album.getAlbum_is_home_page());
            albumInit.setAlbumUpdateId(album.getOperator_id());
            albumInit.setAlbumUpdateTime(new Date());
            albumInit = albumCmsService.update(albumInit);
            if(albumInit!=null){
                result = ResultUtil.success(albumInit);
            }else {
                result = ResultUtil.error(-1,"系统异常!");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/isHomePage",e.getMessage());
        }
        return result;
    }


    @RequestMapping("sort")
    public Result setSort(@ModelAttribute com.memory.entity.bean.Album album){
        Result result = new Result();
        try {
            String uuid = album.getId();
            Album albumInit = albumCmsService.getAlbumById(uuid);
            if(albumInit == null){
                return ResultUtil.error(-1,"非法专辑id");
            }

            albumInit.setAlbumSort(album.getAlbum_sort());
            albumInit.setAlbumUpdateId(album.getOperator_id());
            albumInit.setAlbumUpdateTime(new Date());
            albumInit = albumCmsService.update(albumInit);
            if(albumInit!=null){
                result = ResultUtil.success(albumInit);
            }else {
                result = ResultUtil.error(-1,"系统异常!");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/sort",e.getMessage());
        }
        return result;
    }



    @RequestMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        Result result = new Result();
        try {
            int pageIndex = page+1;
            int limit = size;
            List<Album> list = albumCmsService.queryAlbumByQueHql(pageIndex,limit);
            for (Album album : list) {

               Integer albumTotalView = albumRedisCmsService.getAlbumAllViewTotalByKey(album.getId());
               album.setAlbumTotalView(albumTotalView);

            }
            int totalElements = albumCmsService.queryAlbumByQueHqlCount();
            PageResult pageResult = PageResult.getPageResult(page, size, list, totalElements);
            result = ResultUtil.success(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/online",e.getMessage());
        }
        return result;
    }

    @RequestMapping("options")
    public Result options(){
        Result result = new Result();
        try {
            List<Object> returnList = new ArrayList<Object>();
            Map<String,Object> map;
            List<Album> list = albumCmsService.queryAllAlbum();
            for (Album album : list) {
                map  = new HashMap<String, Object>();
                map.put("codeType",album.getId());
                map.put("codeName",album.getAlbumName());
                returnList.add(map);
            }
            result = ResultUtil.success(returnList);
        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/options",e.getMessage());
        }
        return result;
    }


    private String uploadImg(String uuid,MultipartFile logoFile) {
        String imgUrl ="";
        if(logoFile != null ){
                String prefix = 1 + "";
                String fileName = FileUtils.getImgFileName(prefix);
                String customCmsPath = FileUtils.getCustomCmsPath("album",uuid);
                imgUrl =  FileUtils.upload(logoFile,FileUtils.getLocalPath(),customCmsPath,fileName);
        }

        return imgUrl;
    }

    private Boolean isExistAlbumName(String albumName){
        Boolean flag = false;
        Album album=albumCmsService.queryAlbumByAlbumName(albumName);
        if(Utils.isNotNull(album)){
            flag =true;
        }
        return flag;
    }

    @RequestMapping("test")
    public Result method(@RequestParam  String albumName,@RequestParam String id){
        Result result = new Result();
        try {

            result = ResultUtil.success(isExistAlbumName(albumName,id));
        }catch (Exception e){
            e.printStackTrace();
            log.error("test",e.getMessage());
        }
        return result;
    }


    private Boolean isExistAlbumName(String albumName,String id){
        Boolean flag = false;
        Album album=albumCmsService.queryAlbumByAlbumNameAndId(albumName,id);
        System.out.println(JSON.toJSONString(album));
        if(Utils.isNotNull(album)){
            flag =true;
        }
        return flag;
    }







}
