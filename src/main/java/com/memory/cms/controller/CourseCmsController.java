package com.memory.cms.controller;
import com.alibaba.fastjson.JSON;
import com.memory.cms.service.CourseExtCmsService;
import com.memory.cms.service.CourseMemoryService;
import com.memory.common.yml.MyFileConfig;
import com.memory.entity.jpa.Course;
import com.memory.cms.service.CourseCmsService;
import com.memory.common.utils.*;
import com.memory.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static com.memory.redis.CacheConstantConfig.SHARECOURSECONTENT;

/**
 * @author INS6+
 * 课程管理
 * @date 2019/5/8 11:19
 */
@RestController
@RequestMapping(value = "course/cms")
public class CourseCmsController {

    //private static final String fileUrl = "G:/upload";

    //private static final String fileShowUrl = ""

    private static final Logger log = LoggerFactory.getLogger(CourseCmsController.class);


    @Autowired
    private CourseCmsService courseService;


    @Autowired
    private MyFileConfig config;

    @Autowired
    private CourseMemoryService courseMemoryService;

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private CourseExtCmsService courseExtCmsService;


    /**
     * 变更上线下线状态
     * @param online
     * @param id
     * @return
     */
    @RequestMapping(value = "online", method = RequestMethod.POST)
    public Result setCourseOnline(@RequestParam("online") Integer online, @RequestParam("id") String id){
        Result result =new Result();
        try{

            Course course = courseService.getCourseById(id);
            if(course != null){
               int status =  courseService.updateCourseOnlineById(online,id);
                String str ="上线";
                if(online == 0){
                    str = "下线";
                    courseMemoryService.clear(id);

                    //将redis中的数据赋值为notExist状态.
                    String keyHash = SHARECOURSECONTENT + id;
                    redisUtil.hset(keyHash, "course", "notExist");
                    redisUtil.hset(keyHash, "courseExt", JSON.toJSONString("notExist"));

                }else{
                    //上线状态，同步db2redis
                    courseExtCmsService.updateCourseExtDb2Redis(id,course.getCourseTitle());

                }
                result.setCode(0);
                result.setMsg("变更"+str+"状态成功！");
                result.setData(online);
            }else{
                result.setCode(0);
                result.setMsg("非法请求.当前课程不存在！");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("course/cms/online  err =",e.getMessage());
            result = ResultUtil.error(-1,"系统异常");
        }

        return result;
    }

    @RequestMapping(value = "liveStatus", method = RequestMethod.POST)
    public Result setCourseLiveStatus(@RequestParam("status") Integer course_live_status,String id){
        Result result =new Result();
        try {

           int count =  courseService.updateCourseLiveStatus(course_live_status,id);
           if(count > 0 ){
               Course course = courseService.getCourseById(id);
               result = ResultUtil.success(course);
           }else{
               result = ResultUtil.error(0,"更新失败");
           }


        }catch (Exception e){
            e.printStackTrace();
            log.error("course/cms/liveStatus  err =",e.getMessage());
        }
        return  result;
    }


    /**
     * 获取课程列表（分页）
     * @param page
     * @param size
     * @param course_title
     * @param course_update_id
     * @param course_online
     * @param sort_status
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result queryCourseList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam("course_title") String course_title, @RequestParam("course_update_id") String course_update_id,
                                   @RequestParam("course_online") Integer course_online, @RequestParam("sort_status") String sort_status,@RequestParam("course_type_id") String course_type_id){
        Result result = new Result();
        PageResult pageResult = new PageResult();
        try{
            Pageable pageable = PageRequest.of(page, size);
            org.springframework.data.domain.Page pageer= courseService.queryCourseByQue(pageable,course_title,course_update_id,course_online,sort_status,course_type_id);

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
            log.error("course/cms/list  err =",e.getMessage());
        }
        return result;
    }

    /**
     * 获取课程详情
     * @param id
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public Result getCourseDetail(@RequestParam("id") String id){
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
            log.error("course/cms/detail  err =",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result addCourse(@RequestParam("titleFile") MultipartFile titleFile,@RequestParam("radioFile") MultipartFile radioFile ,@RequestParam("course_type_id") String course_type_id, @RequestParam("course_title") String course_title,
                            @RequestParam("course_recommend") Integer course_recommend, @RequestParam("course_content") String course_content,
                            @RequestParam("course_describe") String course_describe,/* @RequestParam("course_live_status") Integer course_live_status,*/
                            @RequestParam("course_label") String course_label, @RequestParam("course_key_words") String course_key_words,
                            @RequestParam("course_online") Integer course_online, @RequestParam("course_create_id") String course_create_id,
                            @RequestParam("course_update_id") String course_update_id/*,@RequestMapping("")*/){
           Result result = new Result();
            try {

                String fileUrl = config.getUpload_local_path();
                String course_logo = "";
                String course_video_url = "";
                String course_audio_url = "";

                String id = Utils.getShortUUTimeStamp();
                String prefix = "";
                String suffix = "";
                String dayStr = DateUtils.getDate("yyyyMMdd");
                String hoursStr = DateUtils.getDate("HHmmss");

                String fileUploadedPath = "",fileName="";


                if(!titleFile.isEmpty()){
                    prefix = "title";
                    //图片默认转成png格式
                    suffix = ".png";
                    fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

                   // fileUploadedPath = fileUrl + "/" + id;
                    //上传标题图
                    course_logo=  FileUtils.upload(titleFile,fileUrl,fileName,id);
                  //  course_logo = fileUploadedPath + "/" +fileName;
                    //course_logo = id + "/" +fileName;

                }

                if(!radioFile.isEmpty()){
                    prefix = "radio";
                    suffix = ".mp3";
                    fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

                   // fileUploadedPath = fileUrl + "/" + id;
                    //上传MP3音频
                    course_audio_url =  FileUtils.upload(radioFile,fileUrl,fileName,id);

                   // course_audio_url = fileUploadedPath + "/" +fileName;
                    //course_audio_url = id + "/" +fileName;
                }


                String uuid = Utils.getShortUUTimeStamp();

                Course course = init(course_type_id,course_title,
                        course_logo,course_content,
                        course_audio_url,course_video_url,
                        course_label,course_key_words,
                        course_online,course_create_id,
                        course_update_id,uuid,course_recommend,course_describe,null,true
                        );

                course = courseService.add(course);

                if(course != null){
                    result.setCode(0);
                    result.setMsg("添加课程成功");
                    result.setData(course);
                }else {
                    result.setCode(1);
                    result.setMsg("添加课程失败");
                    result.setData("");
                }

            }catch (Exception e ){
                e.printStackTrace();
                result = ResultUtil.error(-1,"系统异常");
                log.error("course/cms/add  err =",e.getMessage());
            }

        return result;
    }



    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result updateCourse(@RequestParam(value = "titleFile" ,required = false) MultipartFile titleFile,@RequestParam(value = "radioFile" ,required = false) MultipartFile radioFile,@RequestParam("id") String id,@RequestParam("course_type_id") String course_type_id,@RequestParam("course_title") String course_title,
                            @RequestParam(value = "course_logo",required = false) String course_logo, @RequestParam("course_content") String course_content,
                            @RequestParam("course_describe") String course_describe,/* @RequestParam("course_live_status") Integer course_live_status,*/
                            @RequestParam(value = "course_audio_url" ,required = false) String course_audio_url,/* @RequestParam("course_video_url") String course_video_url,*/
                            @RequestParam("course_label") String course_label,@RequestParam("course_key_words") String course_key_words,
                            @RequestParam("course_online") Integer course_online, @RequestParam("course_create_id") String course_create_id,
                            @RequestParam("course_update_id") String course_update_id, @RequestParam("course_audio_times") String course_audio_times,
                               @RequestParam("course_recommend") Integer course_recommend){

        Result result = new Result();
        try {
            String fileUrl = config.getUpload_local_path();
            //String course_audio_url="";
            String course_video_url="";
            String prefix = "";
            String suffix = "";
            String dayStr = DateUtils.getDate("yyyyMMdd");
            String hoursStr = DateUtils.getDate("HHmmss");
            String fileUploadedPath = "";
            String fileName="";


            if(titleFile != null && !titleFile.isEmpty()){
                prefix = "title";
                //图片默认转成png格式
                suffix = ".png";
                fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

             //   fileUploadedPath = fileUrl + "/" + id;
                //上传标题图
                course_logo=  FileUtils.upload(titleFile,fileUrl,fileName,id);
               // course_logo = fileUploadedPath + "/" +fileName;
               // course_logo = id + "/" +fileName;

            }

            if(radioFile != null && !radioFile.isEmpty()){
                prefix = "radio";
                suffix = ".mp3";
                fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

               // fileUploadedPath = fileUrl + "/" + id;
                //上传MP3音频
                course_audio_url= FileUtils.upload(radioFile,fileUrl,fileName,id);

                //course_audio_url = fileUploadedPath + "/" +fileName;
               // course_audio_url = id + "/" +fileName;
            }

            Course course = courseService.getCourseById(id);

            if(course == null){
                result = ResultUtil.error(1,"课程检查不到，请刷新后再进行编辑！");
            }else{
                course.setCourseTypeId(course_type_id);
                course.setCourseTitle(course_title);
                course.setCourseLogo(course_logo);
                course.setCourseContent(course_content);
                course.setCourseAudioUrl(course_audio_url);
                course.setCourseLabel(course_label);
                course.setCourseKeyWords(course_key_words);

                course.setCourseUpdateId(course_update_id);
                course.setCourseDescribe(course_describe);
                course.setCourseUpdateTime(new Date());
                course.setCourseOnline(course_online);
                course.setCourseRecommend(course_recommend);
                //course.setCourseAudioTimes(course_audio_times);


            }

            if(course_online ==0){
                courseMemoryService.clear(id);
            }

       /*     Course course = init(course_type_id,course_title,
                    course_logo,course_content,
                    course_audio_url,course_video_url,
                    course_label,course_key_words,
                    course_online,course_create_id,
                    course_update_id,id,course_recommend,course_describe,course_create_time,false
            );*/

            course = courseService.update(course);

            if(course != null){
                result.setCode(0);
                result.setMsg("更新课程成功");
                result.setData(course);
            }else {
                result.setCode(1);
                result.setMsg("更新课程失败");
                result.setData("");
            }

        }catch (Exception e ){
            e.printStackTrace();
            result = ResultUtil.error(-1,"系统异常");
            log.error("course/cms/update  err =",e.getMessage());
        }

        return result;
    }


    private Course init(String course_type_id, String course_title,
                        String course_logo, String course_content,
                        String course_audio_url, String course_video_url,
                        String course_label, String course_key_words,
                        Integer course_online, String course_create_id,
                        String course_update_id, String id, Integer course_recommend, String course_describe, String course_create_time,Boolean isSave){

                Course course = new Course();
                if(id != null){
                    course.setId(id);
                }

                course.setCourseTypeId(course_type_id);
                course.setCourseTitle(course_title);
                course.setCourseLogo(course_logo);
                course.setCourseContent(course_content);
            /*    if(course_audio_url != null && !"".equals(course_audio_url)){
                    course.setCourseAudioUrl(course_audio_url);
                }*/
                course.setCourseAudioUrl(course_audio_url);
                course.setCourseVideoUrl(course_video_url);
                course.setCourseLabel(course_label);
                course.setCourseKeyWords(course_key_words);
                course.setCourseOnline(course_online);
                course.setCourseTotalView(0);
                course.setCourseTotalShare(0);
                course.setCourseTotalLike(0);
                course.setCourseRecommend(course_recommend);
                course.setCourseDescribe(course_describe);
                course.setCourseCreateId(course_create_id);
                if(isSave){

                    course.setCourseCreateTime(new Date());
                    //直播状态默认0，未直播
                    course.setCourseLiveStatus(0);
                }else{
                    course.setCourseCreateTime(DateUtils.strToDate(course_create_time));
                }

                course.setCourseUpdateTime(new  Date());
                course.setCourseUpdateId(course_create_id);

        return course;
    }


    public static String isImagesTrue(String posturl) throws IOException {
        URL url = new URL(posturl);
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        urlcon.setRequestMethod("POST");
        urlcon.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded");
        if (urlcon.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println(HttpURLConnection.HTTP_OK + posturl
                    + ":posted ok!");
            return "200";
        } else {
            System.out.println(urlcon.getResponseCode() + posturl
                    + ":Bad post...");
            return "404";
        }
    }

    @RequestMapping(value = "options", method = RequestMethod.POST)
    public Result queryCourseOptions(){
        Result result = new Result();
        try {
            result = ResultUtil.success(courseService.queryCourseOptions());

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    }

