package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.service.ArticleLikeMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ArticleLikeMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/25 14:20
 */
@RestController
@RequestMapping(value = "articleLikeMobile/mobile")
public class ArticleLikeMobileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleLikeMobileController.class);

    @Autowired
    private ArticleLikeMobileService articleLikeMobileService;

    /**
     * 添加文章点赞
     * URL：192.168.1.185：8081/articleLikeMobile/mobile/addLike
     * @param articleId
     * @param userId
     * @return
     */
    @RequestMapping(value = "addLike")
    public Message add(@RequestParam String articleId, @RequestParam String userId){
        msg = Message.success();
        try {
            msg.setRecode(0);
            msg.setData(articleLikeMobileService.like(articleId,userId));
        }catch (Exception e){
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }


}