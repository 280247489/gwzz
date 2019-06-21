package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Article;
import com.memory.gwzz.service.ArticleMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private DaoUtils daoUtils;

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
     * @return Article 对象
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    public Message getById(@RequestParam  String articleId){
        try {
            msg = Message.success();
            Article article = (Article) daoUtils.getById("Article",articleId);
            String label = article.getArticleLabel();
            String[] labels = label.split(",");
            article.setArticleLabel(labels[0]);
            msg.setMsg("查询成功");
            msg.setData(article);
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

}
