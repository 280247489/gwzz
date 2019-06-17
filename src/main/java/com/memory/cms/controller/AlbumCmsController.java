package com.memory.cms.controller;

import com.memory.cms.service.AlbumCmsService;
import com.memory.common.utils.*;
import com.memory.entity.jpa.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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

    @RequestMapping("add")
    public Result add(@ModelAttribute com.memory.entity.bean.Album album){
        Result result = new Result();
        try {
            String uuid = Utils.getShortUUTimeStamp();
            String album_logo = "";
            if(album.getLogoFile() != null){
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
            String uuid = album.getId();
            Album albumInit = albumCmsService.getAlbumById(uuid);
            if(album == null){
                return ResultUtil.error(-1,"非法专辑id");
            }
            albumInit.setAlbumName(album.getAlbum_name());
            if(album.getLogoFile() != null){
                albumInit.setAlbumLogo(uploadImg(uuid,album.getLogoFile()));
            }else if(album.getAlbum_logo() != null){
                albumInit.setAlbumLogo(album.getAlbum_logo());
            }

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
            if(album == null){
                return ResultUtil.error(-1,"非法专辑id");
            }
            albumInit.setAlbumIsOnline(album.getAlbum_is_online());
            albumInit.setAlbumUpdateId(album.getOperator_id());
            albumInit.setAlbumUpdateTime(new Date());
            albumInit= albumCmsService.update(albumInit);
            if(albumInit!=null){
                result = ResultUtil.success(albumInit);
            }else {
                result = ResultUtil.error(-1,"系统异常!");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/online",e.getMessage());
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
            int totalElements = albumCmsService.queryAlbumByQueHqlCount();
            PageResult pageResult = PageResult.getPageResult(page, size, list, totalElements);
            result = ResultUtil.success(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            log.error("album/cms/online",e.getMessage());
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



}
