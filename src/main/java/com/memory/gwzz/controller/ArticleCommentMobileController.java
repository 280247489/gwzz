package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.ArticleMobileRepository;
import com.memory.gwzz.service.ArticleCommentMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


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
    private ArticleMobileRepository articleMobileRepository;

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 添加文章评论接口
     * URL：192.168.1.185:8081/gwzz/articleComment/mobile/add
     * @param articleId String 文章ID
     * @param userId String 用户ID
     * @param commentType String 评论类型（0文章评论，1回复评论）
     * @param commentParentId String 文章回复的上级Id
     * @param content  评论内容
     * @param content_replace
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Message add(@RequestParam String articleId, @RequestParam String userId,@RequestParam Integer commentType, @RequestParam String commentParentId,
                       @RequestParam("content") String content ,@RequestParam("content_replace") String content_replace){
        try {
            msg = Message.success();
            Article article = articleMobileRepository.findByIdAndArticleOnline(articleId,1);
            if (article!=null){
                User user = (User) daoUtils.getById("User",userId);
                if (user!=null){
                    msg.setRecode(0);
                    Map<String,Object> returnMap = articleCommentMobileService.add(articleId,user,commentType,commentParentId,content,content_replace);
                    msg.setData(returnMap);
                }else {
                    msg.setRecode(2);
                    msg.setMsg("无此用户信息");
                }
            }else {
                msg.setRecode(2);
                msg.setMsg("无此文章");
            }
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 查询文章一级评论
     * URL:192.168.1.185:8081/gwzz/articleComment/mobile/listArticleCommentOne
     * @param articleId String 文章Id
     * @param uid String 用户Id
     * @param start int 第几页
     * @param limit int 每页条数
     * @return
     */
    @RequestMapping(value = "listArticleCommentOne",method = RequestMethod.POST)
    public Message listArtComByAid(@RequestParam String articleId,@RequestParam String uid,@RequestParam Integer start,@RequestParam Integer limit){
        try {
            msg = Message.success();
            Article article = articleMobileRepository.findByIdAndArticleOnline(articleId,1);
            if (article!=null){
                Map<String,Object> returnMap = articleCommentMobileService.listArtComByAid(articleId, uid, start, limit);
                msg.setRecode(0);
                msg.setMsg("成功");
                msg.setData(returnMap);
            }else{
                msg.setRecode(2);
                msg.setMsg("无此文章");
            }
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 查询2级评论
     * URL:192.168.1.185:8081/gwzz/articleComment/mobile/listArticleCommentTwo
     * @param articleId String 文章Id
     * @param commentId String 评论Id
     * @param uid String 用户Id
     * @param start int 第几页
     * @param limit int 每页条数
     * @return
     */
    @RequestMapping(value = "listArticleCommentTwo",method = RequestMethod.POST)
    public Message listArtComByRid(@RequestParam String articleId,@RequestParam String commentId,@RequestParam String uid,@RequestParam Integer start,@RequestParam Integer limit){
        try {
            msg = Message.success();
            Article article = articleMobileRepository.findByIdAndArticleOnline(articleId,1);
            if (article==null){
                msg.setRecode(2);
                msg.setMsg("无此文章");
            }else{
                Map<String,Object> map = articleCommentMobileService.listArtComByRid(commentId, uid, start, limit);
                if (map.values()==null){
                    msg.setRecode(3);
                    msg.setMsg("无此评论");
                }else{
                    msg.setRecode(0);
                    msg.setMsg("成功");
                    msg.setData(map);
                }

            }
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }

}
