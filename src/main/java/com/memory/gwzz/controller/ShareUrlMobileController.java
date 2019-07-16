package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.ShareUrl;
import com.memory.gwzz.redis.service.ArticleRedisMobileService;
import com.memory.gwzz.repository.ArticleMobileRepository;
import com.memory.gwzz.service.ShareUrlMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ShareUrlMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 20:46
 */
@RestController
@RequestMapping(value = "shareUrl/mobile")
public class ShareUrlMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(ShareUrlMobileController.class);

    @Autowired
    private ShareUrlMobileService shareUrlMobileService;

    @Autowired
    private ArticleMobileRepository articleMobileRepository;

    @Autowired
    private ArticleRedisMobileService articleRedisMobileService;

    /**
     * 根据名称查询分享的url
     * URL:192.168.1.185:8081/gwzz/shareUrl/mobile/getShareUrlByName
     * @param name String
     * @return ShareUrl 对象
     */
    @RequestMapping(value = "getShareUrlByName",method = RequestMethod.POST)
    public Message getShareUrlByName(@RequestParam  String name){
        try {
            msg = Message.success();
            ShareUrl shareUrl = shareUrlMobileService.getShareUrlByName(name);
            msg.setRecode(0);
            msg.setData(shareUrl);
        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 文章分享
     * URL:192.168.1.185:8081/gwzz/shareUrl/mobile/shareArticle
     * @param articleId String 文章ID
     * @param userId String userId
     * @param os 类型 0 ios ，1 android
     * @return
     */
    @RequestMapping(value = "shareArticle",method = RequestMethod.POST)
    public Message shareArticle (@RequestParam String articleId,@RequestParam String userId,@RequestParam Integer os){
        try {
            msg = Message.success();
            Article article = articleMobileRepository.findByIdAndArticleOnline(articleId,1);
            if (article != null){
                //获取分享的前缀
                ShareUrl shareUrl = shareUrlMobileService.getShareUrlByName("线上");
                //添加该文章的分享次数
                articleRedisMobileService.articleShare(articleId, userId, os);
                String url = shareUrl.getUrl();
                msg.setRecode(0);
                msg.setData(url);
            }else {
                msg.setRecode(1);
                msg.setMsg("此文章不存在");
            }
        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
            logger.error("系统错误");
        }
        return msg;
    }
}
