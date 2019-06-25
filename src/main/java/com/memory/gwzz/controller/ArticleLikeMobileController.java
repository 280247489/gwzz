package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.ArticleMobileRepository;
import com.memory.gwzz.repository.UserMobileRepository;
import com.memory.gwzz.service.ArticleLikeMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ArticleLikeMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/25 14:20
 */
@RestController
@RequestMapping(value = "articleLike/mobile")
public class ArticleLikeMobileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleLikeMobileController.class);

    @Autowired
    private ArticleLikeMobileService articleLikeMobileService;

    @Autowired
    private ArticleMobileRepository articleMobileRepository;

    @Autowired
    private UserMobileRepository userMobileRepository;

    /**
     * 添加文章点赞
     * URL：192.168.1.185:8081/gwzz/articleLike/mobile/addLike
     * @param articleId String 文章唯一标识ID
     * @param userId String 用户唯一标识ID
     * @return like 对象
     */
    @RequestMapping(value = "addLike",method = RequestMethod.POST)
    public Message add(@RequestParam String articleId, @RequestParam String userId){
        try {
            msg = Message.success();
            Article article = articleMobileRepository.findByIdAndArticleOnline(articleId,1);
            if (article != null){
                msg.setRecode(0);
                msg.setData(articleLikeMobileService.like(articleId,userId));
            }else {
                msg.setRecode(1);
                msg.setMsg("该课程不存在");
            }
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 查询我的点赞文章
     * URL：192.168.1.185:8081/gwzz/articleLike/mobile/listArticleLikeByUserId
     * @param userId String 用户Id
     * @param start int 第几页
     * @param limit int 每页条数
     * @return
     */
    @RequestMapping(value = "listArticleLikeByUserId",method = RequestMethod.POST)
    public Message listArticleLikeByUserId(@RequestParam String userId, @RequestParam Integer start,@RequestParam Integer limit){
        try {
            msg = Message.success();
            User user = userMobileRepository.findByIdAndUserNologinAndUserCancel(userId,0,0);
            if (user != null){
                msg.setRecode(0);
                msg.setData(articleLikeMobileService.ListArticleLikeByUserId(userId, start, limit));
            }else {
                msg.setRecode(1);
                msg.setMsg("该用户不存在");
            }
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }


}
