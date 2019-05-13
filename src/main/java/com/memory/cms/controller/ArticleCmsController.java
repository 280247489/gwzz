package com.memory.cms.controller;
import com.memory.entity.jpa.Article;
import com.memory.cms.service.ArticleCmsService;
import com.memory.common.utils.PageResult;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author INS6+
 * 文章管理
 * @date 2019/5/8 11:19
 */
@RestController
public class ArticleCmsController {

    @Autowired
    private ArticleCmsService articleService;

    /**
     * 变更上线下线状态
     * @param online
     * @param id
     * @return
     */
    @RequestMapping(value = "/article_online"/*, method = RequestMethod.POST*/)
    public Result setArticleOnline(@RequestParam("online") Integer online, @RequestParam("id") Integer id){
        Result result =new Result();
        try{

            Article article = articleService.getArticleById(id);
            if(article != null){
               int status =  articleService.updateArticleOnlineById(online,id);
                String str ="上线";
                if(online == 0){
                    str = "下线";
                }
                result.setCode(0);
                result.setMsg("变更"+str+"状态成功！");
            }else{
                result.setCode(0);
                result.setMsg("非法请求.当前文章不存在！");
            }

        }catch (Exception e){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
        }

        return result;
    }

    /**
     * 获取文章列表（分页）
     * @param page
     * @param size
     * @param article_title
     * @param article_update_id
     * @param article_online
     * @param sort_status
     * @return
     */
    @RequestMapping(value = "/article_list"/*, method = RequestMethod.POST*/)
    public Result queryArticleList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam("article_title") String article_title, @RequestParam("article_update_id") String article_update_id,
                                   @RequestParam("article_online") Integer article_online, @RequestParam("sort_status") String sort_status,@RequestParam("type_id") String type_id){
        Result result = new Result();
        PageResult pageResult = new PageResult();
        try{
            Pageable pageable = PageRequest.of(page, size);
            org.springframework.data.domain.Page pageer= articleService.queryArticleByQue(pageable,article_title,article_update_id,article_online,sort_status,type_id);

            pageResult.setPageNumber(pageable.getPageNumber() + 1);
            pageResult.setPageSize(pageable.getPageSize());
            pageResult.setOffset(pageable.getOffset());
            pageResult.setTotalPages(pageer.getTotalPages());
            pageResult.setTotalElements(pageer.getTotalElements());
            pageResult.setData(pageer.getContent());


            result = ResultUtil.success(pageResult);

        }catch (Exception e){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
        }
        return result;
    }

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/article_detail"/*, method = RequestMethod.POST*/)
    public Result getArticleDetail(@RequestParam("id") Integer id){
        Result result = new Result();
        try {
            if(id == null){
                result.setCode(1);
                result.setMsg("参数错误!");
            }else{
                Article article = articleService.getArticleById(id);
                result.setCode(0);
                result.setMsg("获取详情成功");
                result.setData(article);
            }

        }catch (Exception e){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
        }
        return result;
    }


    public Result addArticle(@RequestParam("type_id") String type_id,@RequestParam("article_title") String article_title,
                             @RequestParam("article_logo") String article_logo, @RequestParam("article_content") String article_content,
                             @RequestParam("article_audio_url") String article_audio_url,@RequestParam("article_video_url") String article_video_url,
                             @RequestParam("article_label") String article_label,@RequestParam("article_key_words") String article_key_words,
                             @RequestParam("article_online") Integer article_online ){

        return null;
    }




}
