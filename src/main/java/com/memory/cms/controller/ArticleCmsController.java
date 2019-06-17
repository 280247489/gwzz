package com.memory.cms.controller;
import com.memory.cms.service.ArticleCmsService;
import com.memory.common.utils.*;
import com.memory.common.yml.MyFileConfig;
import com.memory.entity.jpa.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author INS6+
 * 文章管理
 * @date 2019/5/8 11:19
 */
@RestController
@RequestMapping(value = "article/cms")
public class ArticleCmsController {

    private static final Logger log = LoggerFactory.getLogger(ArticleCmsController.class);


    @Autowired
    private ArticleCmsService articleService;

    /**
     * 变更上线下线状态
     *
     * @param online
     * @param id
     * @return
     */
    @RequestMapping(value = "online", method = RequestMethod.POST)
    public Result setArticleOnline(@RequestParam("online") Integer online, @RequestParam("id") String id) {
        Result result = new Result();
        try {

            Article article = articleService.getArticleById(id);
            if (article != null) {
                int status = articleService.updateArticleOnlineById(online, id);
                String str = "上线";
                if (online == 0) {
                    str = "下线";
                }
                result.setCode(0);
                result.setMsg("变更" + str + "状态成功！");
            } else {
                result.setCode(0);
                result.setMsg("非法请求.当前文章不存在！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.error(-1, "系统异常");
        }

        return result;
    }

    /**
     * 获取文章列表（分页）
     *
     * @param page
     * @param size
     * @param article_title
     * @param article_update_id
     * @param article_online
     * @param sort_status
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result queryArticleList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam("article_title") String article_title, @RequestParam("article_update_id") String article_update_id,
                                   @RequestParam("article_online") Integer article_online, @RequestParam("sort_status") String sort_status, @RequestParam("type_id") String type_id) {
        Result result = new Result();
        PageResult pageResult = new PageResult();
        try {
            Pageable pageable = PageRequest.of(page, size);
            org.springframework.data.domain.Page pageer = articleService.queryArticleByQue(pageable, article_title, article_update_id, article_online, sort_status, type_id);

            pageResult.setPageNumber(pageable.getPageNumber() + 1);
            pageResult.setPageSize(pageable.getPageSize());
            pageResult.setOffset(pageable.getOffset());
            pageResult.setTotalPages(pageer.getTotalPages());
            pageResult.setTotalElements(pageer.getTotalElements());
            pageResult.setData(pageer.getContent());


            result = ResultUtil.success(pageResult);

        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.error(-1, "系统异常");
        }
        return result;
    }

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result getArticleDetail(@RequestParam("id") String id) {
        Result result = new Result();
        try {
            if (id == null) {
                result.setCode(1);
                result.setMsg("参数错误!");
            } else {
                Article article = articleService.getArticleById(id);
                result.setCode(0);
                result.setMsg("获取详情成功");
                result.setData(article);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.error(-1, "系统异常");
        }
        return result;
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result addComment(@RequestParam("titleFile1") MultipartFile titleFile1, @RequestParam("type_id") String type_id, @RequestParam("article_title") String article_title,
                           /* @RequestParam("article_logo") String article_logo,*/ @RequestParam("article_content") String article_content,
                            @RequestParam("article_label") String article_label,
                            @RequestParam("article_key_words") String article_key_words,
                            @RequestParam("article_online") Integer article_online, @RequestParam("article_create_id") String article_create_id,
                            @RequestParam("article_update_id") String article_update_id, @RequestParam("article_describe") String article_describe,
                            @RequestParam("article_recommend") Integer article_recommend,@RequestParam("titleFile2") MultipartFile titleFile2,@RequestParam("titleFile3") MultipartFile titleFile3) {
        Result result = new Result();
        try {
            String uuid = Utils.getShortUUTimeStamp();
            String article_logo1= "";
            String article_logo2= "";
            String article_logo3= "";


            String prefix1 ="title1";
            article_logo1 = getLogo(titleFile1, uuid, article_logo1, prefix1);


            String prefix2 ="title2";
            article_logo2 = getLogo(titleFile2, uuid, article_logo2, prefix2);

            String prefix3 ="title3";
            article_logo3 = getLogo(titleFile3, uuid, article_logo3, prefix3);


            Article article = init(type_id, article_title,
                    article_logo1,article_logo2,article_logo3, article_content,
                    article_label, article_key_words,
                    article_online, article_create_id,
                   uuid, article_recommend, article_describe, null, true
            );

            article = articleService.add(article);

            if (article != null) {
                result.setCode(0);
                result.setMsg("添加文章成功");
                result.setData(article);
            } else {
                result.setCode(1);
                result.setMsg("添加文章失败");
                result.setData("");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.error(-1, "系统异常");
            log.error("course/cms/add  err =", e.getMessage());
        }

        return result;
    }

    private String getLogo( MultipartFile titleFile, String uuid, String article_logo, String prefix) {
        if (!titleFile.isEmpty()) {
            String fileName = FileUtils.getImgFileName(prefix);
            String customCmsPath = FileUtils.getCustomCmsPath("live",uuid);
            //上传标题图
            article_logo =   FileUtils.upload(titleFile,FileUtils.getLocalPath(),customCmsPath,fileName);

        }
        return article_logo;
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result updateComment(@RequestParam(value = "titleFile1", required = false) MultipartFile titleFile1,  @RequestParam("id") String id, @RequestParam("type_id") String type_id,
                               @RequestParam("article_title") String article_title,
                               @RequestParam(value = "article_logo1", required = false) String article_logo1, @RequestParam(value = "article_logo2", required = false) String article_logo2,
                               @RequestParam(value = "article_logo3", required = false) String article_logo3,@RequestParam("article_content") String article_content,
                               @RequestParam("article_describe") String article_describe,
                               @RequestParam("article_label") String article_label, @RequestParam("article_key_words") String article_key_words,
                               @RequestParam("article_online") Integer article_online,
                               @RequestParam("article_update_id") String article_update_id,
                               @RequestParam("article_recommend") Integer article_recommend,@RequestParam("titleFile2") MultipartFile titleFile2,@RequestParam("titleFile3") MultipartFile titleFile3) {

        Result result = new Result();
        try {
            Article article = articleService.getArticleById(id);

            if (article == null) {
                result = ResultUtil.error(1, "非法文章！");
            } else {



                String prefix1 ="title1";
                String prefix2 ="title2";
                String prefix3 ="title3";
                article_logo1 = getLogo(titleFile1, id, article_logo1, prefix1);
                article_logo2 = getLogo(titleFile2, id, article_logo2, prefix2);
                article_logo3 = getLogo(titleFile3, id, article_logo3, prefix3);

                article.setArticleLogo1(article_logo1);
                article.setArticleLogo2(article_logo2);
                article.setArticleLogo3(article_logo3);

                article.setTypeId(type_id);
                article.setArticleTitle(article_title);
                article.setArticleContent(article_content);
                article.setArticleLabel(article_label);
                article.setArticleKeyWords(article_key_words);
                article.setArticleUpdateId(article_update_id);
                article.setArticleDescribe(article_describe);
                article.setArticleUpdateTime(new Date());
                article.setArticleOnline(article_online);
                article.setArticleRecommend(article_recommend);

            }
            article = articleService.update(article);

            if (article != null) {
                result.setCode(0);
                result.setMsg("更新文章成功");
                result.setData(article);
            } else {
                result.setCode(1);
                result.setMsg("更新文章失败");
                result.setData("");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = ResultUtil.error(-1, "系统异常");
            log.error("course/cms/update  err =", e.getMessage());
        }
        return result;
    }


    private Article init(String type_id, String article_title,
                         String article_logo1,String article_logo2,String article_logo3, String article_content,
                         String article_label, String article_key_words,
                         Integer article_online, String article_create_id,
                          String id, Integer article_recommend, String article_describe, String article_create_time, Boolean isSave) {

        Article article = new Article();
        if (id != null) {
            article.setId(id);
        }

        article.setTypeId(type_id);
        article.setArticleTitle(article_title);
        article.setArticleLogo1(article_logo1);
        article.setArticleLogo2(article_logo2);
        article.setArticleLogo3(article_logo3);
        article.setArticleContent(article_content);
        article.setArticleLabel(article_label);
        article.setArticleKeyWords(article_key_words);
        article.setArticleOnline(article_online);
        article.setArticleTotalView(0);
        article.setArticleTotalShare(0);
        article.setArticleTotalLike(0);
        article.setArticleRecommend(article_recommend);
        article.setArticleDescribe(article_describe);
        article.setArticleCreateId(article_create_id);
        if (isSave) {

            article.setArticleCreateTime(new Date());
            article.setArticleUpdateTime(new Date());
            article.setArticleUpdateId(article_create_id);
        } else {
            article.setArticleCreateTime(DateUtils.strToDate(article_create_time));
            article.setArticleUpdateTime(new Date());
            article.setArticleUpdateId(article_create_id);
        }

        article.setArticleAudioUrl("");
        article.setArticleVideoUrl("");


        return article;


    }

}
