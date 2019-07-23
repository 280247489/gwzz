package com.memory.cms.controller;
import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.AlbumRedisCmsService;
import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.cms.redis.service.LiveRedisCmsService;
import com.memory.cms.service.*;
import com.memory.common.utils.*;
import com.memory.entity.jpa.Album;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.LiveMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


/**
 * @author INS6+
 * 课程管理
 * @date 2019/5/8 11:19
 */
@RestController
@RequestMapping(value = "course/cms")
public class CourseCmsController {
    private static final Logger log = LoggerFactory.getLogger(CourseCmsController.class);

    @Autowired
    private CourseCmsService courseService;

    @Autowired
    private LiveMemoryService liveMemoryService;

    @Autowired
    private LiveMasterCmsService liveMasterCmsService;

    @Autowired
    private AlbumCmsService albumCmsService;

    @Autowired
    private CourseRedisCmsService courseRedisCmsService;

    @Autowired
    private AlbumRedisCmsService albumRedisCmsService;

    @Autowired
    private LiveRedisCmsService liveRedisCmsService;

    /**
     * 变更上线下线状态
     * @param online
     * @param id
     * @return
     */
    @RequestMapping(value = "online", method = RequestMethod.POST)
    public Result setCourseOnline(@RequestParam("online") Integer online, @RequestParam("id") String id, @RequestParam("operator_id") String operator_id){
        Result result =new Result();
        try{

            Course course = courseService.getCourseById(id);
            if(Utils.isNotNull(course)){
                course.setCourseOnline(online);
                course.setCourseUpdateTime(new Date());
                course.setCourseUpdateId(operator_id);
                Course returnCourse = courseService.update(course);
                String str ="上线";
                LiveMaster master = liveMasterCmsService.getLiveMasterByCourseId(id);
               // LiveMaster master=  liveMasterCmsService.getLiveMasterById(id);
                //如果课程绑定了直播，同步更新直播数据
                if(Utils.isNotNull(master)){
                    if(online == 0){
                        str = "下线";
                        liveMemoryService.clear(id);

                        //将redis中的数据赋值为notExist状态.
                        //  String keyHash = SHARECOURSECONTENT + id;
                        //   redisUtil.hset(keyHash, "course", "notExist");
                        //   redisUtil.hset(keyHash, "courseExt", JSON.toJSONString("notExist"));

                    }else{
                        //上线状态，同步db2redis
                        //      courseExtCmsService.updateCourseExtDb2Redis(id,course.getCourseTitle());

                    }
                    master.setLiveMasterIsOnline(online);
                    master.setLiveMasterUpdateId(operator_id);
                    master.setLiveMasterUpdateTime(new Date());
                    LiveMaster returnLiveMaster =liveMasterCmsService.update(master);
                    if(Utils.isNotNull(returnLiveMaster)){
                        //根据上下线状态同步redis数据
                        liveMasterCmsService.syncOnline2Redis(master.getId(), online);
                    }

                }

                //变更上下线状态时同步专辑数量
                if(Utils.isNotNull(course.getAlbumId())){
                    Album album = albumCmsService.getAlbumById(course.getAlbumId());
                    if(Utils.isNotNull(album)){
                        int album_course_count = courseService.countCourseByAlbumId(course.getAlbumId());

                        album.setAlbumCourseSum(album_course_count);
                        albumCmsService.update(album);
                    }

                    //获取专辑总的阅读数 同时 同步 到redis中 (课程上下线状态发生改变，课程阅读数发生改变，需要同步)
                    albumRedisCmsService.getAlbumAllViewTotal(course.getAlbumId());
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
                                   @RequestParam("course_online") Integer course_online, @RequestParam("sort_status") String sort_status,@RequestParam("course_type_id") String course_type_id,@RequestParam(value = "album_id",required = false) String album_id){
        Result result = new Result();
        try{
            int pageIndex = page+1;
            int limit = size;
            List<com.memory.entity.bean.Course> list = courseService.queryCourseByQueHql(pageIndex,limit,course_title,course_update_id,course_online,sort_status,course_type_id,album_id);
            List<String> courseIds = new ArrayList<String >();
            for (com.memory.entity.bean.Course course : list) {
                courseIds.add(course.getId());
            }

            if(list.size()>0){

                //绑定了课程的直播列表
                List<LiveMaster> bindMasterList = liveMasterCmsService.queryLiveMasterByInCourseId(courseIds);
                //课程course实际阅读数列表
                List<Object> redisRealViewValues = courseRedisCmsService.getCourseRedisRealViewTotal(courseIds);
                //伪阅读数列表
                List<Object> redisManagerViewValues = courseRedisCmsService.getCourseManagerViewTotal(courseIds);
                //分享数列表
                List<Object> redisShareValues = courseRedisCmsService.getCourseRedisShareTotal(courseIds);
                //点赞数列表
                List<Object> redisLikeValues = courseRedisCmsService.getCourseRedisLikeTotal(courseIds);

                for(int i=0; i<list.size();i++){
                    com.memory.entity.bean.Course course = list.get(i);
                    Integer liveRealView = 0;
                    //找出绑定课程的直播，并从redis中获取直播live的阅读数
                    for (LiveMaster master : bindMasterList) {
                        if(course.getId().equals(master.getCourseId())){
                            liveRealView = liveRedisCmsService.getLiveRedisViewTotal(master.getId());
                        }
                    }

                    //实际阅读数（包含course 和live 的实际阅读数两部分）
                    Integer realView = Utils.getIntVal(redisRealViewValues.get(i));
                    course.setCourseTotalView((realView + liveRealView));

                    //伪阅读数(只有course 有，live不能设置伪阅读数）
                    Integer managerView = Utils.getIntVal(redisManagerViewValues.get(i));
                    course.setCourseTotalManagerView(managerView);

                    //分享数
                    Integer share = Utils.getIntVal(redisShareValues.get(i));
                    course.setCourseTotalShare(share);

                    //点赞数
                    Integer like = Utils.getIntVal(redisLikeValues.get(i));
                    course.setCourseTotalLike(like);

                }

                int totalElements = courseService.queryCourseCountByQueHql(course_title,course_update_id,course_online,sort_status,course_type_id,album_id);


                PageResult pageResult = PageResult.getPageResult(page, size, list, totalElements);
                result = ResultUtil.success(pageResult);
            }else{
                PageResult pageResult = PageResult.getPageResult(page, size, list, 0);
                result = ResultUtil.success(pageResult);
            }




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
                LiveMaster master = liveMasterCmsService.getLiveMasterByCourseId(id);
                Course course = courseService.getCourseById(id);
                result.setCode(0);
                result.setMsg("获取详情成功");
                Map<String,Object> returnMap = new HashMap<String, Object>();
                returnMap.put("live",master);
                returnMap.put("course",course);

                result.setData(returnMap);
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
                            @RequestParam("course_update_id") String course_update_id, @RequestParam("course_release_time") String course_release_time,@RequestParam("course_number") Integer course_number,@RequestParam("liveId") String liveId,@RequestParam("album_id") String album_id){
           Result result = new Result();
            try {

                if(isExistCourseTitle(course_title)){
                    return ResultUtil.error(2,"课程标题已存在!");
                }

                if(isExistCourseNumber(course_number)){
                    return ResultUtil.error(2,"课程期数已存在");
                }

                String course_logo = "";
                String course_video_url = "";
                String course_audio_url = "";

                String id = Utils.getShortUUTimeStamp();


                if(!titleFile.isEmpty()){
                    course_logo = getCourseLogo(titleFile, id);

                }

                if(!radioFile.isEmpty()){

                    course_audio_url = getAudioFileUrl(radioFile, id);

                }

                Course course = init(course_type_id,course_title,
                        course_logo,course_content,
                        course_audio_url,course_video_url,
                        course_label,course_key_words,
                        course_online,course_create_id,
                        course_update_id,id,course_recommend,course_describe,null,true,course_release_time,course_number,album_id
                        );

                course = courseService.add(course);

                //课程关联直播 / 直播关联课程
                if(Utils.isNotNull(liveId)){
                    LiveMaster master = liveMasterCmsService.getLiveMasterById(liveId);
                    master.setCourseId(id);
                    //设置关联状态已关联
                    master.setLiveMasterIsRelation(1);
                    liveMasterCmsService.update(master);

                    //获取专辑总的阅读数 同时 同步 到redis中 (课程关联直播，直播阅读数发生改变，需要同步)
                    albumRedisCmsService.getAlbumAllViewTotal(course.getAlbumId());

                }



                if(course != null){
                    //初始化course redis 后台管理阅读数
                    courseRedisCmsService.initCourseRedisViewTotal(course.getId());
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

    private String getAudioFileUrl(@RequestParam("radioFile") MultipartFile radioFile, String id) {
        String course_audio_url;
        String prefix =  "audio";
        String fileName = FileUtils.getAudioFileName(prefix,radioFile);
        String customCmsPath = FileUtils.getCustomCmsPath("course",id);
        course_audio_url =  FileUtils.upload(radioFile,FileUtils.getLocalPath(),customCmsPath,fileName);
        return course_audio_url;
    }

    private String getCourseLogo(@RequestParam("titleFile") MultipartFile titleFile, String id) {
        String course_logo;
        String prefix =   "title";
        String fileName = FileUtils.getImgFileName(prefix);
        String customCmsPath = FileUtils.getCustomCmsPath("course",id);
        course_logo =  FileUtils.upload(titleFile,FileUtils.getLocalPath(),customCmsPath,fileName);
        return course_logo;
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result updateCourse(@RequestParam(value = "titleFile" ,required = false) MultipartFile titleFile,@RequestParam(value = "radioFile" ,required = false) MultipartFile radioFile,@RequestParam("id") String id,@RequestParam("course_type_id") String course_type_id,@RequestParam("course_title") String course_title,
                            @RequestParam(value = "course_logo",required = false) String course_logo, @RequestParam("course_content") String course_content,
                            @RequestParam("course_describe") String course_describe,/* @RequestParam("course_live_status") Integer course_live_status,*/
                            @RequestParam(value = "course_audio_url" ,required = false) String course_audio_url,/* @RequestParam("course_video_url") String course_video_url,*/
                            @RequestParam("course_label") String course_label,@RequestParam("course_key_words") String course_key_words,
                            @RequestParam("course_online") Integer course_online, @RequestParam("course_create_id") String course_create_id,
                            @RequestParam("course_update_id") String course_update_id, @RequestParam("course_audio_times") String course_audio_times,
                               @RequestParam("course_recommend") Integer course_recommend, @RequestParam("course_release_time") String course_release_time,@RequestParam("course_number") Integer course_number,@RequestParam("liveId") String liveId,@RequestParam("album_id") String album_id){

        Result result = new Result();
        try {
            Boolean albumIsChange =false;
            if(isExistCourseTitle(course_title,id)){
                return ResultUtil.error(2,"课程标题已存在!");
            }

            if(isExistCourseNumber(course_number,id)){
                return ResultUtil.error(2,"课程期数已存在");
            }
            Course course = courseService.getCourseById(id);

            if(course == null){
              return  ResultUtil.error(-1,"非法课程！");
            }else{

                String old_albumId = course.getAlbumId();
                //判断所属专辑是否发生改变
                //专辑发生改变
                if(Utils.isNotNull(album_id) && !album_id.equals(old_albumId)){
                    albumIsChange = true;

                    Album old_album = albumCmsService.getAlbumById(old_albumId);
                    Album album= albumCmsService.getAlbumById(album_id);
                    int old_album_course_count = courseService.countCourseByAlbumId(old_albumId);
                    int album_course_count = courseService.countCourseByAlbumId(album_id);
                    old_album.setAlbumCourseSum(old_album_course_count - 1);
                    album.setAlbumCourseSum(album_course_count + 1);
                    albumCmsService.update(old_album);
                    albumCmsService.update(album);

                    //获取专辑总的阅读数 同时 同步 到redis中 (课程专辑发生改变，课程阅读数发生改变，需要同步)
                    albumRedisCmsService.getAlbumAllViewTotal(old_album.getId());
                    //获取专辑总的阅读数 同时 同步 到redis中 (课程专辑发生改变，课程阅读数发生改变，需要同步)
                    albumRedisCmsService.getAlbumAllViewTotal(album.getId());


                }



                //课程关联直播 / 直播关联课程
                if(Utils.isNotNull(liveId)){
                    LiveMaster master = liveMasterCmsService.getLiveMasterById(liveId);
                    //要过滤掉自身修改，如果id和master.getCourseId 不一致提示解绑
                    if(master ==null || (Utils.isNotNull(master.getCourseId()) && !id.equals(master.getCourseId()) )){
                        return ResultUtil.error(-1,"关联课程未解绑，不可修改!");
                    }
                    master.setCourseId(id);
                    //设置关联状态已关联
                    master.setLiveMasterIsRelation(1);
                    liveMasterCmsService.update(master);
                    //获取专辑总的阅读数 同时 同步 到redis中 (直播关联课程，直播的阅读数发生改变需要同步)
                    albumRedisCmsService.getAlbumAllViewTotal(course.getAlbumId());

                }



                if(titleFile!=null && !titleFile.isEmpty()){
                    course_logo = getCourseLogo(titleFile, id);
                    course.setCourseLogo(course_logo);
                }

                if(radioFile!=null && !radioFile.isEmpty()){
                    course_audio_url = getAudioFileUrl(radioFile, id);
                    course.setCourseAudioUrl(course_audio_url);
                }

                course.setCourseTypeId(course_type_id);
                course.setCourseTitle(course_title);
                course.setCourseContent(course_content);
                course.setCourseLabel(course_label);
                course.setCourseKeyWords(course_key_words);
                course.setCourseUpdateId(course_update_id);
                course.setCourseDescribe(course_describe);
                course.setCourseUpdateTime(new Date());
                course.setCourseOnline(course_online);
                course.setCourseRecommend(course_recommend);
                if(Utils.isNotNull(course_release_time)){
                    course.setCourseReleaseTime(DateUtils.strToDate(course_release_time));
                }

                course.setCourseNumber(course_number);

                if(course_online ==0){
                    liveMemoryService.clear(id);
                }

                if(albumIsChange){
                    course.setAlbumId(album_id);
                }

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
                        String course_update_id, String id, Integer course_recommend, String course_describe, String course_create_time, Boolean isSave,String course_release_time,int course_number,String album_id){

                Course course = new Course();
                if(id != null){
                    course.setId(id);
                }

                course.setCourseTypeId(course_type_id);
                course.setCourseTitle(course_title);
                course.setCourseLogo(course_logo);
                course.setCourseContent(course_content);
                course.setCourseAudioUrl(course_audio_url);
                course.setCourseVideoUrl(course_video_url);
                course.setCourseLabel(course_label);
                course.setCourseKeyWords(course_key_words);
                course.setCourseOnline(course_online);
                course.setCourseTotalView(0);
                course.setCourseTotalShare(0);
                course.setCourseTotalLike(0);
                course.setCourseTotalComment(0);
                course.setCourseRecommend(course_recommend);
                course.setCourseDescribe(course_describe);
                course.setCourseCreateId(course_create_id);
                course.setCourseReleaseTime(DateUtils.strToDate(course_release_time));
                course.setCourseNumber(course_number);

                if(isSave){
                    course.setCourseCreateTime(new Date());
                    //直播状态默认0，未直播
                    course.setCourseLiveStatus(0);
                }else{
                    course.setCourseCreateTime(DateUtils.strToDate(course_create_time));
                }

                course.setCourseUpdateTime(new  Date());
                course.setCourseUpdateId(course_create_id);
                course.setAlbumId(album_id);

                //album_id
                //更新专辑数量
                if(Utils.isNotNull(album_id)){
                    Album album = albumCmsService.getAlbumById(album_id);
                    if(Utils.isNotNull(album)){
                        album.setAlbumCourseSum(album.getAlbumCourseSum()+1);
                        albumCmsService.update(album);
                        //获取专辑总的阅读数 同时 同步 到redis中
                        albumRedisCmsService.getAlbumAllViewTotal(album_id);
                    }
                }


        return course;
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


    @RequestMapping("live_untied")
    public Result liveUntied(String course_id,String master_id,String operator_id){
        Result result = new Result();
        try {
            Course course = courseService.getCourseById(course_id);
            if(course == null){
                return ResultUtil.error(-1,"非法课程!");
            }

            if(!Utils.isNotNull(operator_id)){
                return ResultUtil.error(-1,"操作人不能为空!");
            }

            LiveMaster master = liveMasterCmsService.getLiveMasterByCourseId(course_id);
            if(Utils.isNotNull(master) && Utils.isNotNull(master.getCourseId()) && course_id.equals(master.getCourseId())){
                //获取专辑总的阅读数 同时 同步 到redis中 (课程解绑直播，直播阅读数发生改变，需要同步)
                albumRedisCmsService.getAlbumAllViewTotal(course.getAlbumId());

                master.setCourseId("");
                liveMasterCmsService.update(master);
                course.setCourseUpdateId(operator_id);
                course.setCourseUpdateTime(new Date());
                courseService.update(course);
                result = ResultUtil.success("解绑成功");
            }else {
                return ResultUtil.error(-1,"直播未关联该课程!");
            }


        }catch (Exception e){
            e.printStackTrace();
            log.error("live_untied",e.getMessage());
        }
        return result;
    }

    @RequestMapping("managerView")
    public Result managerView(@RequestParam String courseId,Integer changeNum){
        Result result = new Result();
        try {
            Course course = courseService.getCourseById(courseId);

            if(changeNum==null){
                changeNum = 0;
            }

            if(Utils.isNotNull(course)){
                courseRedisCmsService.setCourseRedisViewTotal(courseId,changeNum);

                result = ResultUtil.success(changeNum);
            }else {
                result = ResultUtil.error(-1,"非法课程id!");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("managerView",e.getMessage());
        }
        return result;
    }


    private Boolean isExistCourseTitle(String courseTitle){
        Boolean flag = false;
        Course course = courseService.queryCourseByCourseTitle(courseTitle);
        if(Utils.isNotNull(course)){
            flag = true;
        }
        return flag;
    }

    private Boolean isExistCourseNumber(Integer courseNumber){
        Boolean flag =false;

        Course course = courseService.queryCourseByCourseNumber(courseNumber);
        if(Utils.isNotNull(course)){
            flag = true;
        }
        return flag;
    }

    private Boolean isExistCourseTitle(String courseTitle,String id){
        Boolean flag = false;
        Course course = courseService.queryCourseByCourseTitle(courseTitle,id);
        if(Utils.isNotNull(course)){
            flag = true;
        }
        return flag;
    }

    private Boolean isExistCourseNumber(Integer courseNumber,String id){
        Boolean flag =false;

        Course course = courseService.queryCourseByCourseNumber(courseNumber,id);
        if(Utils.isNotNull(course)){
            flag = true;
        }
        return flag;
    }





}

