package com.memory.cms.controller;

import com.alibaba.fastjson.JSON;
import com.memory.entity.jpa.CourseExt;
import com.memory.entity.bean.Ext;
import com.memory.entity.jpa.ExtModel;
import com.memory.cms.service.CourseExtCmsService;
import com.memory.common.utils.*;
import com.memory.gwzz.service.CourseExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author INS6+
 * @date 2019/5/11 14:18
 */
@RestController
@RequestMapping(value = "courseExt")
public class CourseExtCmsController {

    @Autowired
    private CourseExtCmsService courseExtCmsService;

    @Autowired
    private CourseExtService courseExtService;

    private static final String fileUrl = "G:/upload";




    @RequestMapping(value ="add")
    @ResponseBody
    public Result /*List<Ext> */ add( ExtModel extModel){
        Result result = new Result();
        List<CourseExt> list = new ArrayList<CourseExt>();
        CourseExt courseExt = null;
        try{

            List<Ext> extList = extModel.getExtList();
          //  System.out.println("json ======================================" + JSON.toJSONString(extList) );
            String course_audio_url="";
            String course_video_url="";
            String  course_img_url = "";
            String prefix = "";
            String suffix = "";
            String dayStr = DateUtils.getDate("yyyyMMdd");
            String hoursStr = DateUtils.getDate("HHmmss");
            String fileUploadedPath = "";
            String fileName="";

            for(int i = 0; i<extList.size();i++){
                //String uuid = Utils.generateUUID();

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
                    System.out.println("11111111111111111111111111111111111111111111111111111111111" );
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
                            course_audio_url = fileUploadedPath + "/" +fileName;
                            courseExt.setCourseExtAudio(course_audio_url);
                        }


                    }else {
                        ///upload/courseid/1_yyyyMMdd_HHmmss.mp3
                        ///http://baidu.com.mp3
                        courseExt.setCourseExtAudio(ext.getAudioUrl());
                    }

                    courseExt.setCourseExtLogo("");
                    courseExt.setCourseExtWords("");
                    courseExt.setCourseExtImgUrl("");

                //图片
                }else if(type ==3){
                    //传的是文件流，进行上传
                    if(ext.getImgFile() != null){
                     //   System.out.println("json now ext ======================================" + JSON.toJSONString(ext) );
                        MultipartFile imgFiles = ext.getImgFile();
                        if(!imgFiles.isEmpty()){
                            prefix = i+1+"";
                            //图片默认转成png
                            suffix = ".png";
                            fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;
                            fileUploadedPath = fileUrl + "/" + courseUUid;
                            //上传图片
                            FileUtils.upload(imgFiles,fileUploadedPath,fileName);
                            course_img_url = fileUploadedPath + "/" +fileName;
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
            if(extListSave!=null){
                courseExtService.setCourseExt(extListSave);
            }

            result = ResultUtil.success(extListSave);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    @RequestMapping(value ="update")
    @ResponseBody
    public Result /*List<Ext> */ update( ExtModel extModel){
        Result result = new Result();
        List<CourseExt> list = new ArrayList<CourseExt>();
        CourseExt courseExt = null;
        try{

            List<Ext> extList = extModel.getExtList();
            System.out.println("json =====================================" + JSON.toJSONString(extList) );
            String course_audio_url="";
            String course_video_url="";
            String  course_img_url = "";
            String prefix = "";
            String suffix = "";
            String dayStr = DateUtils.getDate("yyyyMMdd");
            String hoursStr = DateUtils.getDate("HHmmss");
            String fileUploadedPath = "";
            String fileName="";

            for(int i = 0; i<extList.size();i++){
                //String uuid = Utils.generateUUID();

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
                        MultipartFile audioFile = ext.getImgFile();
                        if(!audioFile.isEmpty()){
                            prefix = i+1+"";
                            //语音
                            suffix = ".mp3";
                            fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

                            fileUploadedPath = fileUrl + "/" + courseUUid;
                            //上传语音
                            FileUtils.upload(audioFile,fileUploadedPath,fileName);
                            course_audio_url = fileUploadedPath + "/" +fileName;
                            courseExt.setCourseExtAudio(course_audio_url);
                        }


                    }else {
                        ///upload/courseid/1_yyyyMMdd_HHmmss.mp3
                        ///http://baidu.com.mp3
                        courseExt.setCourseExtAudio(ext.getAudioUrl());
                    }

                    courseExt.setCourseExtLogo("");
                    courseExt.setCourseExtWords("");
                    courseExt.setCourseExtImgUrl("");

                    //图片
                }else if(type ==3){
                    //传的是文件流，进行上传
                    if(ext.getImgFile() != null){
                        System.out.println("json now ext ======================================" + JSON.toJSONString(ext) );
                        MultipartFile audioFile = ext.getImgFile();
                        if(!audioFile.isEmpty()){
                            prefix = i+1+"";
                            //图片默认转成png
                            suffix = ".png";
                            fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

                            fileUploadedPath = fileUrl + "/" + courseUUid;
                            //上传图片
                            FileUtils.upload(audioFile,fileUploadedPath,fileName);
                            course_img_url = fileUploadedPath + "/" +fileName;
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

            List<CourseExt> removeList = courseExtCmsService.queryCourseExtListByCourseId(list.get(0).getCourseId());
            //删除原数据并保存新数据(事物方法)
            List<CourseExt> extListSave =  courseExtCmsService.deleteAndSave(removeList,list);

            if(extListSave != null){
                //从Redis中删除,并重新添加
                courseExtService.delCourseExt(removeList.get(0).getCourseId());
                courseExtService.setCourseExt(extListSave);
            }

            //List<CourseExt> extListSave = courseExtCmsService.saveAll(list);
            result = ResultUtil.success(extListSave);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    @RequestMapping(value="list")
    public Result queryCourseExtListByCourseId(@RequestParam("courseId") String courseId){
        Result result = new Result();
        try {
            List<CourseExt> list =  courseExtCmsService.queryCourseExtListByCourseId(courseId);
            result = ResultUtil.success(list);

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }






}
