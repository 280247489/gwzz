package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.User;
import com.memory.gwzz.service.ArticleCommentMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName ArticleCommentMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/24 16:39
 */
@RestController
@RequestMapping(value = "articleComment/mobile")
public class ArticleCommentMobileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleCommentMobileController.class);

    @Autowired
    private ArticleCommentMobileService articleCommentMobileService;

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 添加文章评论接口
     *  URL：192.168.1.185:8081/gwzz/articleComment/mobile/add
     * @param articleId 文章ID
     * @param userId 用户ID
     * @param commentType 评论类型（0文章评论，1回复评论）
     * @param commentParentId 文章评论的根ID
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Message add(@RequestParam String articleId, @RequestParam String userId,@RequestParam Integer commentType, @RequestParam String commentParentId,
                       @RequestParam("content") String content ,@RequestParam("content_replace") String content_replace){
        try {
            msg = Message.success();
            User user = (User) daoUtils.getById("User",userId);
            if (user!=null){
                msg.setRecode(0);
                msg.setData(articleCommentMobileService.add(articleId,user,commentType,commentParentId,content,content_replace));
            }else {
                msg.setRecode(2);
                msg.setMsg("无此用户信息");
            }


        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }
}
