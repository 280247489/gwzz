package com.memory.cms.controller;

import com.memory.cms.service.BannerCmsService;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Banner;
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
 * @ClassName BannerCmsController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 18:37
 */
@RestController
@RequestMapping(value = "banner/cms")
public class BannerCmsController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BannerCmsController.class);

    @Autowired
    private BannerCmsService bannerCmsService;

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 添加轮播图
     * URL:192.168.1.185:8081/gwzz/banner/cms/add
     * @param bName 名称 String
     * @param bLogo 图片 File
     * @param typeTable String 关联表 【外链rul】
     * @param typeTableId String 关联ID
     * @param bannerSort int 排序
     * @param createId 创建人
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Message addBanner(@RequestParam String bName, @RequestParam MultipartFile bLogo, @RequestParam String typeTable,
                             @RequestParam String typeTableId, @RequestParam Integer bannerSort, @RequestParam String createId){
        msg = Message.success();
        try {
            if (bannerCmsService.checkBannerName(bName,"")==null){
                bannerCmsService.addBanner(bName, bLogo, typeTable, typeTableId, bannerSort, createId);
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
     * 查询轮播图
     * URL:192.168.1.185:8081/gwzz/banner/cms/list
     * @param page
     * @param size
     * @param direction
     * @param sorts
     * @param bName 名称 String
     * @return list
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Message queryArticleList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(defaultValue = "asc") String direction,@RequestParam(defaultValue = "bannerSort") String sorts,
                                    @RequestParam String bName) {
        msg = Message.success();
        try {
            Pageable pageable = PageRequest.of(page, size,direction.toLowerCase().equals("asc")? Sort.Direction.ASC:Sort.Direction.DESC,sorts);
            Page<Banner> list = bannerCmsService.findBanner(pageable,bName);
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
     * 根据id查询详情
     * URL:192.168.1.185:8081/gwzz/banner/cms/getById
     * @param id String 唯一标识
     * @return banner对象
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Message getById(@RequestParam String id){
        msg = Message.success();
        try {
            Banner banner = (Banner) daoUtils.getById("Banner", id);
            if (banner==null){
                msg.setRecode(2);
                msg.setMsg("无此信息");
            }else {
                msg.setRecode(0);
                msg.setData(banner);
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
     * 修改轮播图
     * URL:192.168.1.185:8081/gwzz/banner/cms/upd
     * @param id String 唯一标识
     * @param bName String 名称
     * @param bLogo File 图片
     * @param typeTable String 关联表 【外链rul】
     * @param typeTableId String 关联表Id
     * @param bannerSort int 排序
     * @param createId  String 创建人Id
     * @return
     */
    @RequestMapping(value = "upd", method = RequestMethod.POST)
    public Message upd (@RequestParam String id, @RequestParam String bName, @RequestParam MultipartFile bLogo,
                        @RequestParam String typeTable, @RequestParam String typeTableId,@RequestParam Integer bannerSort,
                        @RequestParam String createId){
        msg = Message.success();
        try {
            Banner banner =  bannerCmsService.checkBannerName(bName,id);
            if (banner==null){
                banner = (Banner) daoUtils.getById("Banner", id);
                if (banner!=null){
                    msg.setRecode(0);
                    msg.setMsg("修改成功");
                    msg.setData(bannerCmsService.upd(banner,bName,bLogo,typeTable,typeTableId,bannerSort,createId));
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
     * 设置状态
     * URL:192.168.1.185:8081/gwzz/banner/cms/updOnline
     * @param id String 唯一标识
     * @return
     */
    @RequestMapping(value = "updOnline",method = RequestMethod.POST)
    public Message updOnline(@RequestParam String id){
        msg = Message.success();
        try {
            Banner banner = (Banner) daoUtils.getById("Banner", id);
            if (banner==null){
                msg.setRecode(2);
                msg.setMsg("无此信息");
            }else{
                msg.setRecode(0);
                msg.setMsg("修改成功");
                msg.setData( bannerCmsService.updOnine(banner));
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
     * 删除
     * URL:192.168.1.185:8081/gwzz/banner/cms/del
     * @param id String 唯一标识
     * @return
     */
    @RequestMapping(value = "del",method = RequestMethod.POST)
    public Message del(@RequestParam String id){
        msg = Message.success();
        try {
            Banner banner = (Banner) daoUtils.getById("Banner", id);
            if (banner==null){
                msg.setRecode(2);
                msg.setMsg("无此信息");

            }else{
                bannerCmsService.del(banner);
                msg.setRecode(0);
                msg.setMsg("删除成功");
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
