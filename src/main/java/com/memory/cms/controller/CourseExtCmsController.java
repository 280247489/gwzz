package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.cms.service.CourseCmsService;
import com.memory.cms.service.CourseMemoryService;
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





    @RequestMapping(value ="add", method = RequestMethod.POST)
    @ResponseBody
    public Result add( ExtModel extModel){
        Result result = new Result();
        List<CourseExt> list = new ArrayList<CourseExt>();
        CourseExt courseExt = null;
        try{

            String fileUrl= config.getUpload_local_path();
            List<Ext> extList = extModel.getExtList();
            String course_audio_url="";
            String  course_img_url = "";
            String prefix = "";
            String suffix = "";
            String dayStr = DateUtils.getDate("yyyyMMdd");
            String hoursStr = DateUtils.getDate("HHmmss");
            String fileUploadedPath = "";
            String fileName="";

            for(int i = 0; i<extList.size();i++){

                courseExt = new CourseExt();
                Ext ext = extList.get(i);
                String courseUUid = ext.getCourseId();
                int type = ext.getType();
                courseExt.setId(courseUUid+"_"+(i+1));
                courseExt.setCourseId(ext.getCourseId());
                courseExt.setCourseExtNickname(ext.getName());
                courseExt.setCourseExtType(ext.getType());
                courseExt.setCourseExtSort(i+1);
                if(ext.getName() ==null || "".equals(ext.getName())){
                }
                //文字
                if(type == 1){

                    courseExt.setCourseExtWords(ext.getWords());
                    courseExt.setCourseExtLogo("");
                    courseExt.setCourseExtImgUrl("");
                    courseExt.setCourseExtAudio("");

                //语音
                }else if(type ==2){
                    courseExt.setCourseExtAudioTimes(ext.getTimes());
                    //传的是文件流，进行上传
                    if(ext.getAudioFile() != null){
                        MultipartFile audioFile = ext.getAudioFile();
                        if(!audioFile.isEmpty()){
                            prefix = i+1+"";
                            //语音
                            suffix = ".mp3";
                            fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

                            fileUploadedPath = fileUrl + "/" + courseUUid;
                            //上传语音
                            FileUtils.upload(audioFile,fileUploadedPath,fileName);

                           //course_audio_url = fileUploadedPath + "/" +fileName;
                            course_audio_url = courseUUid + "/" +fileName;

                            courseExt.setCourseExtAudio(course_audio_url);
                        }


                    }else {
                        courseExt.setCourseExtAudio(ext.getAudioUrl());
                    }

                    courseExt.setCourseExtLogo("");
                    courseExt.setCourseExtWords("");
                    courseExt.setCourseExtImgUrl("");

                //图片
                }else if(type ==3){
                    //传的是文件流，进行上传
                    if(ext.getImgFile() != null){
                        MultipartFile imgFiles = ext.getImgFile();
                        if(!imgFiles.isEmpty()){
                            prefix = i+1+"";
                            //图片默认转成png
                            suffix = ".png";
                            fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;
                            fileUploadedPath = fileUrl + "/" + courseUUid;
                            //上传图片
                            FileUtils.upload(imgFiles,fileUploadedPath,fileName);

                            //course_img_url = fileUploadedPath + "/" +fileName;
                            course_img_url = courseUUid + "/" +fileName;
                            courseExt.setCourseExtImgUrl(course_img_url);
                        }
                    }else {
                        courseExt.setCourseExtImgUrl(ext.getImgUrl());
                    }

                    courseExt.setCourseExtLogo("");
                    courseExt.setCourseExtWords("");
                    courseExt.setCourseExtAudio("");
                }

                courseExt.setCourseExtCreateTime(new Date());
               list.add(courseExt);
            }
            List<CourseExt> extListSave = courseExtCmsService.saveAll(list);


            String courseId = extList.get(0).getCourseId();
            Course course = courseCmsService.getCourseById(courseId);

            if(extListSave!=null){

                String keyHash =SHARECOURSECONTENT +courseId;
                redisUtil.hset(keyHash,"course",course.getCourseTitle());
                redisUtil.hset(keyHash,"courseExt",JSON.toJSONString(overMethod(extListSave)));

                //Map<java.lang.Object, java.lang.Object> map2 = courseRedisCmsService.setHashAndIncr(courseId,mapper);

                courseMemoryService.addMemory(courseId);

               // System.out.println(JSON.toJSONString(map2));
            }

            result = ResultUtil.success(extListSave);

        }catch (Exception e){
            e.printStackTrace();
            log.error("courseExt/cms/add  err =",e.getMessage());
        }
        return result;
    }



    @RequestMapping(value ="update", method = RequestMethod.POST)
    @ResponseBody
    public Result /*List<Ext> */ update( ExtModel extModel){
        Result result = new Result();
        List<CourseExt> list = new ArrayList<CourseExt>();
        CourseExt courseExt = null;
        try{
           String fileUrl= config.getUpload_local_path();
            List<Ext> extList = extModel.getExtList();
            String course_audio_url="";
            String  course_img_url = "";
            String prefix = "";
            String suffix = "";
            String dayStr = DateUtils.getDate("yyyyMMdd");
            String hoursStr = DateUtils.getDate("HHmmss");
            String fileUploadedPath = "";
            String fileName="";

            for(int i = 0; i<extList.size();i++){

                courseExt = new CourseExt();
                Ext ext = extList.get(i);
                String courseUUid = ext.getCourseId();
                int type = ext.getType();
                courseExt.setId(courseUUid+"_"+(i+1));
                courseExt.setCourseId(ext.getCourseId());
                courseExt.setCourseExtNickname(ext.getName());
                courseExt.setCourseExtType(ext.getType());
                courseExt.setCourseExtSort(i+1);
                //文字
                if(type == 1){

                    courseExt.setCourseExtWords(ext.getWords());
                    courseExt.setCourseExtLogo("");
                    courseExt.setCourseExtImgUrl("");
                    courseExt.setCourseExtAudio("");

                    //语音
                }else if(type ==2){
                    courseExt.setCourseExtAudioTimes(ext.getTimes());
                    //传的是文件流，进行上传
                    if(ext.getAudioFile() != null){
                        MultipartFile audioFile = ext.getAudioFile();
                        if(!audioFile.isEmpty()){
                            prefix = i+1+"";
                            //语音
                            suffix = ".mp3";
                            fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

                            fileUploadedPath = fileUrl + "/" + courseUUid;
                            //上传语音
                            FileUtils.upload(audioFile,fileUploadedPath,fileName);
                           // course_audio_url = fileUploadedPath + "/" +fileName;
                            course_audio_url = courseUUid + "/" +fileName;
                            courseExt.setCourseExtAudio(course_audio_url);
                        }


                    }else {
                        courseExt.setCourseExtAudio(ext.getAudioUrl());
                    }

                    courseExt.setCourseExtLogo("");
                    courseExt.setCourseExtWords("");
                    courseExt.setCourseExtImgUrl("");

                    //图片
                }else if(type ==3){
                    //传的是文件流，进行上传
                    if(ext.getImgFile() != null){
                        MultipartFile imgFiles = ext.getImgFile();
                        if(!imgFiles.isEmpty()){
                            prefix = i+1+"";
                            //图片默认转成png
                            suffix = ".png";
                            fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;
                            fileUploadedPath = fileUrl + "/" + courseUUid;
                            //上传图片
                            FileUtils.upload(imgFiles,fileUploadedPath,fileName);
                            course_img_url = courseUUid + "/" +fileName;
                            courseExt.setCourseExtImgUrl(course_img_url);
                        }
                    }else {
                        courseExt.setCourseExtImgUrl(ext.getImgUrl());
                    }

                    courseExt.setCourseExtLogo("");
                    courseExt.setCourseExtWords("");
                    courseExt.setCourseExtAudio("");
                }

                courseExt.setCourseExtCreateTime(new Date());
                list.add(courseExt);
            }

            List<CourseExt> removeList = courseExtCmsService.queryCourseExtByCourseId(list.get(0).getCourseId());
            //删除原数据并保存新数据(事物方法)
            List<CourseExt> extListSave =  courseExtCmsService.deleteAndSave(removeList,list);


            if(extListSave != null){


                String courseId = extList.get(0).getCourseId();
                Course course = courseCmsService.getCourseById(courseId);

                String keyHash =SHARECOURSECONTENT +courseId;
                redisUtil.hset(keyHash,"course",course.getCourseTitle());
                redisUtil.hset(keyHash,"courseExt",JSON.toJSONString(overMethod(extListSave)));

                //从Redis中删除,并重新添加
          //      courseRedisCmsService.delAndHashSet(courseId,mapper);

                courseMemoryService.addMemory(courseId);
            }

            result = ResultUtil.success(extListSave);

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


    public static void main(String[] args) {



        System.out.println(Utils.getShortUUTimeStamp());

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
