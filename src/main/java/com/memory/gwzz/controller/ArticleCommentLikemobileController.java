package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.ArticleCommentLike;
import com.memory.gwzz.service.ArticleCommentLikeMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ArticleCommentLikemobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 16:22
 */
@RestController
@RequestMapping(value = "articleCommentLike/mobile")
public class ArticleCommentLikemobileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleCommentLikemobileController.class);

    @Autowired
    private ArticleCommentLikeMobileService articleCommentLikeMobileService;

    /**
     * 添加评论点赞
     * URL：192.168.1.185:8081/gwzz/articleCommentLike/mobile/addLike
     * @param articleId String 文章id
     * @param articleCommentId String 评论Id
     * @param userId String 用户Id
     * @return
     */
    @RequestMapping(value = "addLike")
    public Message add(@RequestParam String articleId, @RequestParam String articleCommentId, @RequestParam String userId){
        try {
            msg = Message.success();
            Integer articleCommentLike = articleCommentLikeMobileService.like(articleId,articleCommentId,userId);
            if (articleCommentLike==1){
                msg.setRecode(1);
            }else{
                msg.setRecode(0);
            }
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }


}
