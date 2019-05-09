package com.memory.cms.controller;
import com.memory.cms.entity.Course;
import com.memory.common.utils.PageResult;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author INS6+
 * 课程管理
 * @date 2019/5/8 11:19
 */
@RestController
//@RequestMapping(value = "/course")
public class CourseController {

    @Autowired
    private com.memory.cms.service.CourseService courseService;




    /**
     * 变更上线下线状态
     * @param online
     * @param id
     * @return
     */
    @RequestMapping(value = "/course_online"/*, method = RequestMethod.POST*/)
    public Result setArticleOnline(@RequestParam("online") Integer online, @RequestParam("id") Integer id){
        Result result =new Result();
        try{

            Course course = courseService.getCourseById(id);
            if(course != null){
               int status =  courseService.updateCourseOnlineById(online,id);
                String str ="上线";
                if(online == 0){
                    str = "下线";
                }
                result.setCode(0);
                result.setMsg("变更"+str+"状态成功！");
            }else{
                result.setCode(0);
                result.setMsg("非法请求.当前课程不存在！");
            }

        }catch (Exception e){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
        }

        return result;
    }

    /**
     * 获取课程列表（分页）
     * @param page
     * @param size
     * @param article_title
     * @param article_update_id
     * @param article_online
     * @param sort_status
     * @return
     */
    @RequestMapping(value = "/course_list"/*, method = RequestMethod.POST*/)
    public Result queryCourseList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam("article_title") String article_title, @RequestParam("article_update_id") String article_update_id,
                                   @RequestParam("article_online") Integer article_online, @RequestParam("sort_status") String sort_status,@RequestParam("type_id") String type_id){
        Result result = new Result();
        PageResult pageResult = new PageResult();
        try{
            Pageable pageable = PageRequest.of(page, size);
            org.springframework.data.domain.Page pageer= courseService.queryCourseByQue(pageable,article_title,article_update_id,article_online,sort_status,type_id);

            pageResult.setPageNumber(pageable.getPageNumber() + 1);
            pageResult.setOffset(pageable.getOffset());
            pageResult.setPageSize(pageable.getPageSize());
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
     * 获取课程详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/course_detail"/*, method = RequestMethod.POST*/)
    public Result getCourseDetail(@RequestParam("id") Integer id){
        Result result = new Result();
        try {
            if(id == null){
                result.setCode(1);
                result.setMsg("参数错误!");
            }else{
                Course course = courseService.getCourseById(id);
                result.setCode(0);
                result.setMsg("获取详情成功");
                result.setData(course);
            }

        }catch (Exception e){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
        }
        return result;
    }

    @RequestMapping(value = "/course_add"/*, method = RequestMethod.POST*/)
    public Result addCourse(@RequestParam("type_id") String type_id,@RequestParam("article_title") String article_title,
                             @RequestParam("article_logo") String article_logo, @RequestParam("article_content") String article_content,
                             @RequestParam("article_audio_url") String article_audio_url,@RequestParam("article_video_url") String article_video_url,
                             @RequestParam("article_label") String article_label,@RequestParam("article_key_words") String article_key_words,
                             @RequestParam("article_online") Integer article_online, @RequestParam("article_create_id") String article_create_id,
                             @RequestParam("article_update_id") String article_update_id){
           Result result = new Result();
            try {

                Course course = init(type_id,article_title,
                        article_logo,article_content,
                        article_audio_url,article_video_url,
                        article_label,article_key_words,
                        article_online,article_create_id,
                        article_update_id
                        );

                course = courseService.add(course);

                if(course != null){
                    result.setCode(0);
                    result.setMsg("添加文章成功");
                    result.setData(course);
                }else {
                    result.setCode(1);
                    result.setMsg("添加文章失败");
                    result.setData("");
                }

            }catch (Exception e ){
                e.printStackTrace();
                result = ResultUtil.error(-1,"系统异常");
            }

        return result;
    }
    @RequestMapping(value = "/course_update"/*, method = RequestMethod.POST*/)
    public Result updateCourse(@RequestParam("type_id") String type_id,@RequestParam("article_title") String article_title,
                            @RequestParam("article_logo") String article_logo, @RequestParam("article_content") String article_content,
                            @RequestParam("article_audio_url") String article_audio_url,@RequestParam("article_video_url") String article_video_url,
                            @RequestParam("article_label") String article_label,@RequestParam("article_key_words") String article_key_words,
                            @RequestParam("article_online") Integer article_online, @RequestParam("article_create_id") String article_create_id,
                            @RequestParam("article_update_id") String article_update_id){
        Result result = new Result();
        try {

            Course course = init(type_id,article_title,
                    article_logo,article_content,
                    article_audio_url,article_video_url,
                    article_label,article_key_words,
                    article_online,article_create_id,
                    article_update_id
            );

            course = courseService.update(course);

            if(course != null){
                result.setCode(0);
                result.setMsg("更新文章成功");
                result.setData(course);
            }else {
                result.setCode(1);
                result.setMsg("更新文章失败");
                result.setData("");
            }

        }catch (Exception e ){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
        }

        return result;
    }



    private  Course init(String type_id,String article_title,
                              String article_logo,String article_content,
                              String article_audio_url,String article_video_url,
                              String article_label,String article_key_words,
                              Integer article_online,String article_create_id,
                              String article_update_id){

                Course course = new Course();
                course.setTypeId(type_id);
                course.setArticleTitle(article_title);
                course.setArticleLogo(article_logo);
                course.setArticleContent(article_content);
                course.setArticleAudioUrl(article_audio_url);
                course.setArticleVideoUrl(article_video_url);
                course.setArticleLabel(article_label);
                course.setArticleKeyWords(article_key_words);
                course.setArticleOnline(article_online);
                course.setArticleTotalView(0);
                course.setArticleTotalShare(0);
                course.setArticleTotalLike(0);
                course.setArticleCreateTime(new Date());
                course.setArticleCreateId(article_create_id);
                course.setArticleUpdateTime(new  Date());
                course.setArticleUpdateId(article_update_id);

        return course;
    }






}
