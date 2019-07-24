package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.Article;
import com.memory.gwzz.redis.service.ArticleRedisMobileService;
import com.memory.gwzz.repository.ArticleMobileRepository;
import com.memory.gwzz.service.ArticleMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ArticleMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/18 13:22
 */
@RestController
@RequestMapping(value = "article/mobile")
public class ArticleMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(AdvertiseMobileController.class);
    @Autowired
    private ArticleMobileService articleMobileService;

    @Autowired
    private ArticleMobileRepository articleMobileRepository;

    @Autowired
    private ArticleRedisMobileService articleRedisMobileService;

    @Value(value = "${fileUrl}")
    private String fileUrl;

    /**
     * 查询文章列表
     * URL:192.168.1.185:8081/gwzz/article/mobile/findArticleByKey
     * @param start Int 第几页
     * @param limit int 每页条数
     * @param key String 查询关键字
     * @return Map
     */
    @RequestMapping(value = "findArticleByKey",method = RequestMethod.POST)
    public Message findArticleByKey(@RequestParam Integer start, @RequestParam Integer limit, @RequestParam String key){
        try {
//            Map<String,Object> returnMap = new HashMap<>();
//            returnMap.put("articleList",articleMobileService.findArticleByKey(start, limit, key));
//            returnMap.put("fileUrl",fileUrl);
            msg = Message.success();
            msg.setMsg("查询成功");
            msg.setData(articleMobileService.findArticleByKey(start, limit, key));
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 根据Id查询详情
     * URL:192.168.1.185:8081/gwzz/article/mobile/getById
     * @param articleId String 文章id
     * @param userId 用户Id
     * @param os 操作系统 0 ios， 1 android
     * @param terminal 类型 0 app， 1分享
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    public Message getById(@RequestParam  String articleId,@RequestParam String userId,@RequestParam Integer os,@RequestParam Integer terminal){
        try {
            msg = Message.success();
            Map<String,Object> returnMap = new HashMap<>();
            Article article = articleMobileRepository.findByIdAndArticleOnline(articleId,1);
            if (article!=null){
                //添加文章阅读量
                articleRedisMobileService.articleView(articleId, userId, os,terminal);
                String label = article.getArticleLabel();
                String[] labels = label.split(",");
                article.setArticleLabel(labels[0]);
                //重写文章阅读量、点赞量、分享量
                article.setArticleTotalView(articleRedisMobileService.getArticleView(articleId));
                article.setArticleTotalLike(articleRedisMobileService.getArticleLike(articleId));
                article.setArticleTotalShare(articleRedisMobileService.getArticleShare(articleId));
                //加载当前用户是否点赞
                returnMap.put("isLike",articleRedisMobileService.isLike(articleId, userId));

                returnMap.put("article",article);
                returnMap.put("fileUrl",fileUrl);
                msg.setMsg("查询成功");
                msg.setData(returnMap);
            }else {
                msg.setMsg("无此数据");
                msg.setRecode(1);
            }
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 根据关键字查询课程
     * URL:192.168.1.185:8081/gwzz/article/mobile/findArticleByKey
     * @param start
     * @param limit
     * @param userId
     * @param key
     * @return
     */
    @RequestMapping(value = "listArticleByKey",method = RequestMethod.POST)
    public Message listArticleByKey(@RequestParam Integer start, @RequestParam Integer limit, @RequestParam String userId, @RequestParam String key){
        try {
            msg = Message.success();
            //替换空格中英文符号
            String p = "(?i)[^a-zA-Z0-9\u4E00-\u9FA5]";
            String strKey = key.replaceAll(p, "");
            if (!"".equals(strKey)){
                articleRedisMobileService.searchArticle(userId,strKey);
            }
//            Map<String,Object> returnMap = new HashMap<>();
//            returnMap.put("articleList",articleMobileService.listArticleByKey(start, limit, strKey));
//            returnMap.put("fileUrl",fileUrl);
            Map<String,Object> returnMap = articleMobileService.listArticleByKey(start, limit, strKey);
            msg.setMsg("查询成功");
            msg.setData(returnMap);
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 根据Id查询详情分享
     * URL:192.168.1.185:8081/gwzz/article/mobile/shareArticleById
     * @param articleId String 文章id
     * @param os 操作系统 0 ios， 1 android
     * @return
     */
    @RequestMapping(value = "shareArticleById",method = RequestMethod.POST)
    public Message shareArticleById(@RequestParam  String articleId,@RequestParam Integer os){
        try {
            msg = Message.success();
            String userId = "wx";
            Integer terminal = 1;
            Map<String,Object> returnMap = new HashMap<>();
            Article article = articleMobileRepository.findByIdAndArticleOnline(articleId,1);
            if (article!=null){
                //添加文章阅读量
                articleRedisMobileService.articleView(articleId, userId, os,terminal);
                String label = article.getArticleLabel();
                String[] labels = label.split(",");
                article.setArticleLabel(labels[0]);
                //重写文章阅读量、点赞量、分享量
                article.setArticleTotalView(articleRedisMobileService.getArticleView(articleId));
                article.setArticleTotalLike(articleRedisMobileService.getArticleLike(articleId));
                article.setArticleTotalShare(articleRedisMobileService.getArticleShare(articleId));

                returnMap.put("article",article);
                returnMap.put("fileUrl",fileUrl);
                msg.setMsg("查询成功");
                msg.setData(returnMap);
            }else {
                msg.setMsg("无此数据");
                msg.setRecode(1);
            }
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

}
