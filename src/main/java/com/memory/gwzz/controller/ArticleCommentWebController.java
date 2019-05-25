package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.service.ArticleCommentWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ArticleCommentWebController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/24 16:39
 */
@RestController
@RequestMapping(value = "articleComment/web")
public class ArticleCommentWebController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(CourseExtWebController.class);

    @Autowired
    private ArticleCommentWebService articleCommentWebService;

    /**
     * 添加文章评论接口
     *  URL：192.168.1.185：8081/gwzz/articleComment/web/add
     * @param articleId 文章ID
     * @param userId 用户ID
     * @param userLogo 评论人头像
     * @param userName 评论人昵称
     * @param commentType 评论类型（0文章评论，1回复评论）
     * @param commentParentId 文章评论的根ID
     * @param commentParentUserName 评论父级用户昵称
     * @param commentContent 评论内容
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Message add(@RequestParam String articleId, @RequestParam String userId, @RequestParam String userLogo, @RequestParam String userName,
                       @RequestParam Integer commentType, @RequestParam String commentParentId, @RequestParam String commentParentUserName,
                       @RequestParam String commentContent){
        msg = Message.success();
        try {
            msg.setRecode(0);
            msg.setData(articleCommentWebService.add(articleId,userId,userLogo,userName,commentType,commentParentId,commentParentUserName,commentContent));
        }catch (Exception e){
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }
}
