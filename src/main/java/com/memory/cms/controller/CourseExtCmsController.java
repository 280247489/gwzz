package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.cms.service.CourseCmsService;
import com.memory.cms.service.CourseMemoryService;
import com.memory.common.async.DemoAsyncTask;
import com.memory.common.yml.MyFileConfig;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseExt;
import com.memory.entity.bean.Ext;
import com.memory.entity.bean.ExtModel;
import com.memory.cms.service.CourseExtCmsService;
import com.memory.common.utils.*;
import com.memory.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.memory.redis.CacheConstantConfig.SHARECOURSECONTENT;


/**
 * @author INS6+
 * @date 2019/5/11 14:18
 */
@RestController
@RequestMapping(value = "courseExt/cms")
public class CourseExtCmsController {

    private static final Logger log = LoggerFactory.getLogger(CourseExtCmsController.class);


    @Autowired
    private CourseExtCmsService courseExtCmsService;

    @Autowired
    private CourseCmsService courseCmsService;

    @Autowired
    private CourseRedisCmsService courseRedisCmsService;

    @Autowired
    private MyFileConfig config;

    @Autowired
    private CourseMemoryService courseMemoryService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DemoAsyncTask task;





    @RequestMapping(value ="add", method = RequestMethod.POST)
    @ResponseBody
    public Result add( ExtModel extModel){

        Result result = new Result();
        List<CourseExt> list = new ArrayList<CourseExt>();
        try{

            List<Ext> extList = dealCourseExtData(extModel, list);

            //保存数据到数据表中
            List<CourseExt> extListSave = courseExtCmsService.saveAll(list);
            if(extListSave!=null){

                //存储到redis 临时
                addCourseExt2Redis(extList, extListSave);

                //异步同步网络资源到服务器
               //asyncDownloadFromXiaoZhuShou(extListSave);
            }
            result = ResultUtil.success(extListSave);

        }catch (Exception e){
            e.printStackTrace();
            log.error("courseExt/cms/add  err =",e.getMessage());
        }
        System.out.println("add=======================================");
        return result;
    }




    @RequestMapping(value ="update", method = RequestMethod.POST)
    @ResponseBody
    public Result  update( ExtModel extModel){
        Result result = new Result();
        List<CourseExt> list = new ArrayList<CourseExt>();
        try{
            List<Ext> extList = dealCourseExtData(extModel, list);

            List<CourseExt> removeList = courseExtCmsService.queryCourseExtByCourseId(list.get(0).getCourseId());
            //删除原数据并保存新数据(事物方法)
            List<CourseExt> extListSave =  courseExtCmsService.deleteAndSave(removeList,list);
            if(extListSave != null){

                //存储到redis 临时
                addCourseExt2Redis(extList, extListSave);

                //异步同步网络资源到服务器
                //asyncDownloadFromXiaoZhuShou(extListSave);
            }
            result = ResultUtil.success(extListSave);
            System.out.println("update ===========================================");
        }catch (Exception e){
            e.printStackTrace();
            log.error("courseExt/cms/update  err =",e.getMessage());
        }
        return result;
    }



    @RequestMapping(value="list", method = RequestMethod.POST)
    public Result queryCourseExtListByCourseId(@RequestParam("courseId") String courseId){
        Result result = new Result();
        try {
            List<CourseExt> list =  courseExtCmsService.queryCourseExtByCourseId(courseId);
            result = ResultUtil.success(list);

        }catch (Exception e){
            e.printStackTrace();
            log.error("courseExt/cms/list  err =",e.getMessage());
        }

        return result;
    }



    private List<Ext> dealCourseExtData(ExtModel extModel, List<CourseExt> list) {
        CourseExt courseExt;

        List<Ext> extList = extModel.getExtList();
        String course_ext_logo ="";
        String course_ext_words="";
        String course_ext_img_url="";
        String course_ext_audio="";
        for(int i = 0; i<extList.size();i++){
            courseExt = new CourseExt();
            Ext ext = extList.get(i);
            String courseUUid = ext.getCourseId();
            int type = ext.getType();

            initCourseExt(courseExt, i, ext, courseUUid);
            //文字
            if(type == 1){
                course_ext_words = ext.getWords();
            //语音
            }else if(type ==2){
                courseExt.setCourseExtAudioTimes(ext.getTimes());
                //传的是文件流，进行上传
                if(ext.getAudioFile() != null){

                    //上传音频文件
                    course_ext_audio =  uploadAudio( i, ext, courseUUid);
                }else {
                    course_ext_audio =  ext.getAudioUrl();
                }
            //图片
            }else if(type ==3){
                //传的是文件流，进行上传
                if(ext.getImgFile() != null){

                    //上传图片文件
                    course_ext_img_url =  uploadCourseExtImg( i, ext, courseUUid);
                }else {
                    course_ext_img_url = ext.getImgUrl();
                }
            }
            courseExt.setCourseExtWords(course_ext_words);
            courseExt.setCourseExtLogo(course_ext_logo);
            courseExt.setCourseExtImgUrl(course_ext_img_url);
            courseExt.setCourseExtAudio(course_ext_audio);
            courseExt.setCourseExtCreateTime(new Date());
            list.add(courseExt);
        }
        return extList;
    }

    private void initCourseExt(CourseExt courseExt, int i, Ext ext, String courseUUid) {
        courseExt.setId(courseUUid + "_" + (i + 1));
        courseExt.setCourseId(ext.getCourseId());
        courseExt.setCourseExtNickname(ext.getName());
        courseExt.setCourseExtType(ext.getType());
        courseExt.setCourseExtSort(i + 1);
    }

    private void addCourseExt2Redis(List<Ext> extList, List<CourseExt> extListSave) {
        String courseId = extList.get(0).getCourseId();
        Course course = courseCmsService.getCourseById(courseId);
        String keyHash = SHARECOURSECONTENT + courseId;
        redisUtil.hset(keyHash, "course", course.getCourseTitle());
        redisUtil.hset(keyHash, "courseExt", JSON.toJSONString(overMethod(extListSave)));

        courseMemoryService.addMemory(courseId);
    }

    private String uploadCourseExtImg( int i, Ext ext, String courseUUid) {
        String fileUrl= config.getUpload_local_path();
        String course_img_url = "";
        String prefix;
        MultipartFile imgFiles = ext.getImgFile();
        if(!imgFiles.isEmpty()){
            prefix = i+1+"";
            //图片默认转成png
            String fileName = FileUtils.getCourseExtImgFileName(prefix);
            //上传图片
            course_img_url =  FileUtils.upload(imgFiles,fileUrl,fileName,courseUUid);

        }
        return course_img_url;
    }

    private String uploadAudio( int i, Ext ext, String courseUUid) {
        String fileUrl= config.getUpload_local_path();
        String course_audio_url = "";
        String prefix;
        //String fileUploadedPath;
        MultipartFile audioFile = ext.getAudioFile();
        if(!audioFile.isEmpty()){
            prefix = i+1+"";
            //语音

            String fileName = FileUtils.getCourseExtRadioFileName(prefix,audioFile);
            //上传语音
            course_audio_url =   FileUtils.upload(audioFile,fileUrl,fileName,courseUUid);
        }
        return course_audio_url;
    }

    private void asyncDownloadFromXiaoZhuShou(List<CourseExt> extListSave) throws Exception {
        for (CourseExt ext : extListSave) {
             int courseType = ext.getCourseExtType();
             int sort = ext.getCourseExtSort();
             String courseId = ext.getCourseId();
             String fileName ="";
             String showPath = "";
             //语音
             if(courseType == 2){
                 //网络音频资源下载路径
                 String radioUrl = ext.getCourseExtAudio();
                 if(radioUrl.indexOf("http") > -1){
                     String realFileName = ext.getCourseExtAudio();
                     realFileName = realFileName.substring(realFileName.lastIndexOf("/")+1);
                     fileName = FileUtils.getCourseExtRadioFileName(sort+"",realFileName);
                    //String urlStr,String fileName,String savePath
                     String savePath  = config.getUpload_local_path_xiaozhushou() + "/" + courseId;
                     //异步线程任务下载
                     task.doTask_fileDownload(radioUrl,fileName,savePath);
                     showPath = courseId + "/" + fileName;
                     //同步数据表
                     courseExtCmsService.setCourseExtStaticPathByCourseIdAndCourseExtSort(courseId,sort+"",null,showPath);
                 }
             }

             //图片
             if(courseType == 3){
                 //网络图片资源下载路径
                 String imgUrl = ext.getCourseExtImgUrl();
                 if(imgUrl.indexOf("http") >  -1){
                     fileName = FileUtils.getCourseExtImgFileName(sort+"");
                     String savePath  = config.getUpload_local_path_xiaozhushou() + "/" + ext.getCourseId();
                     //异步线程任务下载
                     task.doTask_fileDownload(imgUrl,fileName,savePath);
                     showPath = courseId + "/" + fileName;
                     //同步数据表
                     courseExtCmsService.setCourseExtStaticPathByCourseIdAndCourseExtSort(courseId,sort+"",showPath,null);
                 }

             }

        }
    }







    public List<Map<String,Object>> overMethod(List<CourseExt> extListSave){

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < extListSave.size(); i++) {
            CourseExt courseExt1 = extListSave.get(i);
            Map<String, Object> mapObj = new HashMap<>();
            mapObj.put("courseExtNickname", courseExt1.getCourseExtNickname());
            mapObj.put("courseExtSort", courseExt1.getCourseExtSort());
            mapObj.put("courseExtType", courseExt1.getCourseExtType());
            if(courseExt1.getCourseExtType()==1){
                mapObj.put("courseExtWords", courseExt1.getCourseExtWords());
            }else if(courseExt1.getCourseExtType()==2){
                mapObj.put("courseExtAudio", courseExt1.getCourseExtAudio());
                mapObj.put("courseExtAudioTimes", courseExt1.getCourseExtAudioTimes());
            }else if(courseExt1.getCourseExtType()==3){
                mapObj.put("courseExtImgUrl", courseExt1.getCourseExtImgUrl());
            }
            resultList.add(mapObj);
        }
        return  resultList;
    }









}
