package com.memory.jodit.controller;

import com.memory.common.utils.DateUtils;
import com.memory.common.utils.FileUtils;
import com.memory.common.utils.Result;
import com.memory.common.utils.Utils;
import com.memory.entity.bean.JoditData;
import com.memory.entity.bean.JoditImg;
import com.memory.entity.bean.MultipartFileArrayModel;
import com.memory.entity.bean.MultipartFileModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author INS6+
 * @date 2019/5/14 14:55
 */
@RestController
@RequestMapping(value = "jodit")
public class JoditConrtoller {

    private static final String uploadFilePath = "G:/upload/jodit";
    private static final String baseUrl = "http://192.168.1.119:8091/file/jodit/";

   private static final String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.PNG|.png)$";


    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public JoditImg  uploadImg(HttpServletRequest request,MultipartFileArrayModel model){
        JoditImg joditImg = new JoditImg();
        try{
            System.out.println(model);
            System.out.println(model.getFiles());
            String course_logo = "";
            List<MultipartFile> list =  model.getFiles();
            ArrayList<String> fileStr =new ArrayList<String>();
            ArrayList<String> message = new ArrayList<String>();
            ArrayList<Boolean> isImages = new ArrayList<Boolean>();

            int i =0;
            for (MultipartFile multipartFile : list) {

                MultipartFile uploadFile = multipartFile;

                String prefix = "";
                String suffix = "";
                String dayStr = DateUtils.getDate("yyyyMMdd");
                String hoursStr = DateUtils.getDate("HHmmss");
                String fileUploadedPath = "",fileName="";

                String imgName = uploadFile.getOriginalFilename();
                System.out.println(imgName);
                System.out.println(uploadFile.getName());
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(imgName);
                if(matcher.find()){
                    isImages.add(true);
                }else{
                    isImages.add(false);
                }

                if(!uploadFile.isEmpty()){
                  //  prefix = "title";
                    //图片默认转成png格式
                    suffix = ".png";
                    //fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;
                    fileName = Utils.generateUUID() + suffix;

                //    fileStr[i] = fileName;
                    fileUploadedPath = uploadFilePath + "/" + dayStr + "/";
                    //上传标题图
                    FileUtils.upload(uploadFile,fileUploadedPath,fileName);
                    //  course_logo = fileUploadedPath + "/" +fileName;
                    course_logo =  dayStr + "/" +fileName;

                    fileStr.add(course_logo);
                    System.out.println("course_logo === "+course_logo);

                    joditImg.setTime(DateUtils.getCurrentDate());
                    //joditImg.setData();

                    joditImg.toString();
                }
                i++;
            }

            JoditData joditData = new JoditData();
            joditData.setBaseurl(baseUrl);
            joditData.setMessage(message);
            joditData.setIsImages(isImages);
            joditData.setFiles(fileStr);

            joditData.setCode(220);
            joditImg.setSuccess(true);
            joditImg.setData(joditData);

        }catch (Exception e){
            e.printStackTrace();
        }

        return joditImg;
    }




}
