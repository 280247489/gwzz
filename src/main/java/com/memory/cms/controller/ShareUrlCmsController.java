package com.memory.cms.controller;

import com.memory.cms.service.ShareUrlCmsService;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.ShareUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ShareUrlCmsController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 20:27
 */
@RestController
@RequestMapping(value = "shareUrl/cms")
public class ShareUrlCmsController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ShareUrlCmsController.class);
    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private ShareUrlCmsService shareUrlCmsService;

    /**
     * 添加分享
     * URL:192.168.1.185:8081/gwzz/shareUrl/cms/addShareUrl
     * @param urlName String 名称
     * @param url String
     * @param cid String 创建人
     * @return
     */
    @RequestMapping(value = "addShareUrl",method = RequestMethod.POST)
    public Message addShareUrl(@RequestParam String urlName, @RequestParam String url, @RequestParam String cid){
        msg = Message.success();
        try {
            shareUrlCmsService.add(urlName,url,cid);
            msg.setRecode(0);
            msg.setMsg("添加成功");

        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

    /**
     * 查询分享
     * URL:192.168.1.185:8081/gwzz/shareUrl/cms/listShareUrl
     * @return list
     */
    @RequestMapping(value = "listShareUrl",method = RequestMethod.POST)
    public Message listShareUrl(){
        msg = Message.success();
        try {
            msg.setData(shareUrlCmsService.list());
            msg.setRecode(0);

        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }
        return msg;
    }

    /**
     * 根据Id查询分享记录
     * URL:192.168.1.185:8081/gwzz/shareUrl/cms/getById
     * @param id
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Message getById(@RequestParam String id){
        msg = Message.success();
        try {
            ShareUrl shareUrl = (ShareUrl) daoUtils.getById("ShareUrl", id);
                msg.setRecode(0);
                msg.setData(shareUrl);
        }catch (Exception e) {
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            log.error("异常信息");
        }

        return msg;
    }

    /**
     * 修改分享记录
     * URL:192.168.1.185:8081/gwzz/shareUrl/cms/updShareUrl
     * @param id String
     * @param urlName String 名称
     * @param url String 地址
     * @param cid String 修改人
     * @return
     */
    @RequestMapping(value = "updShareUrl",method = RequestMethod.POST)
    public Message updShareUrl(@RequestParam String id,@RequestParam String urlName, @RequestParam String url, @RequestParam String cid){
        msg = Message.success();
        try {
            ShareUrl shareUrl = (ShareUrl) daoUtils.getById("ShareUrl", id);
            if (shareUrl!=null){
                shareUrlCmsService.upd(shareUrl,urlName,url,cid);
                msg.setRecode(0);
                msg.setMsg("添加成功");
            }else {
                msg.setRecode(2);
                msg.setMsg("无此记录");
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
     * 删除
     * URL:192.168.1.185:8081/gwzz/shareUrl/cms/del
     * @param id String
     * @return
     */
    @RequestMapping(value = "del",method = RequestMethod.POST)
    public Message del(@RequestParam String id){
        msg = Message.success();
        try {
            ShareUrl shareUrl = (ShareUrl) daoUtils.getById("ShareUrl", id);
            if (shareUrl!=null){
                shareUrlCmsService.del(shareUrl);
                msg.setRecode(0);
                msg.setMsg("删除成功");
            }else{
                msg.setRecode(3);
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
