package com.memory.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 13:49
 * @Description:
 */
@Component
public class FileUploadUtil {

    @Value(value = "${filePath}")
    private String filePath;
    @Value(value = "${fileUrl}")
    private String fileUrl;


    /**
     * 图片上传
     * @param fileName
     * @param dirPath
     * @param file
     * @return
     */
    public String upload(String fileName, String dirPath, MultipartFile file){
        String db_path = "";
        try {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
            File dir = new File(filePath + dirPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File destFile = new File(dir.getPath() + File.separator + fileName + suffix);
            file.transferTo(destFile);
            db_path = dirPath + File.separator + fileName + suffix;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return db_path;
    }
    public String upload2PNG(String fileName, String dirPath, MultipartFile file){
        String db_path = "";
        try {
            File dir = new File(filePath + dirPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File destFile = new File(dir.getPath() + File.separator + fileName + ".png");
            file.transferTo(destFile);
            db_path = dirPath + File.separator + fileName + ".png";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return db_path;
    }
    public String upload2MP3(String fileName, String dirPath, MultipartFile file){
        String db_path = "";
        try {
            File dir = new File(filePath + dirPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File destFile = new File(dir.getPath() + File.separator + fileName + ".mp3");
            file.transferTo(destFile);
            db_path = dirPath + File.separator + fileName + ".mp3";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return db_path;
    }


}
