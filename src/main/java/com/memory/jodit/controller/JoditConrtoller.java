package com.memory.jodit.controller;

import com.memory.common.utils.DateUtils;
import com.memory.common.utils.FileUtils;
import com.memory.common.utils.Utils;
import com.memory.entity.bean.JoditImg;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/14 14:55
 */
@RestController
public class JoditConrtoller {

    private static final String uploadFilePath = "G:/upload";


    public JoditImg uploadImg(HttpServletRequest request){
        JoditImg joditImg = new JoditImg();
        try{
            String course_logo = "";

            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            if(files == null){

            }else{
                MultipartFile uploadFile = files.get(0);

                String id = Utils.generateUUID();
                String prefix = "";
                String suffix = "";
                String dayStr = DateUtils.getDate("yyyyMMdd");
                String hoursStr = DateUtils.getDate("HHmmss");

                String fileUploadedPath = "",fileName="";


                if(!uploadFile.isEmpty()){
                    prefix = "title";
                    //图片默认转成png格式
                    suffix = ".png";
                    fileName = prefix + "_" + dayStr + "_" + hoursStr + suffix;

                    fileUploadedPath = uploadFilePath + "/" + id;
                    //上传标题图
                    FileUtils.upload(uploadFile,fileUploadedPath,fileName);
                    //  course_logo = fileUploadedPath + "/" +fileName;
                    course_logo = id + "/" +fileName;

                    System.out.println("course_logo === "+course_logo);
                }


            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return joditImg;
    }


}
