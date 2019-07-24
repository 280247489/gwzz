package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.Article;
import com.memory.entity.jpa.ArticleLike;
import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.ArticleMobileRepository;
import com.memory.gwzz.repository.UserMobileRepository;
import com.memory.gwzz.service.ArticleLikeMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @Value(value = "${fileUrl}")
    private String fileUrl;



    /**
     * 添加文章点赞
     * URL：192.168.1.185:8081/gwzz/articleLike/mobile/addLike
     * @param articleId String 文章唯一标识ID
     * @param userId String 用户唯一标识ID
     * @return
     */
    @RequestMapping(value = "addLike",method = RequestMethod.POST)
    public Message add(@RequestParam String articleId, @RequestParam String userId){
        try {
            msg = Message.success();
            Article article = articleMobileRepository.findByIdAndArticleOnline(articleId,1);
            if (article != null){
                Integer articleLike = articleLikeMobileService.like(articleId,userId);
                    msg.setRecode(articleLike);
            }else {
                msg.setRecode(2);
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
                Map<String,Object> returnMap = articleLikeMobileService.ListArticleLikeByUserId(userId, start, limit);
                returnMap.put("fileUrl",fileUrl);
                msg.setRecode(0);
                msg.setData(returnMap);
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
