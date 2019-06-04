package com.memory.common.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 13:49
 * @Description:
 */
public class FileUploadUtil {

//    public static String uploadFile(byte[] file, String filePath, String fileName) throws Exception {
//        String path = filePath + fileName;
//        File targetFile = new File(filePath);
//        if(!targetFile.exists()){
//            targetFile.mkdirs();
//        }
//        FileOutputStream out = new FileOutputStream(path);
//        out.write(file);
//        out.flush();
//        out.close();
//        return fileName;
//    }
    /**
     * 单文件上传
     * @param file 文件
     * @param filePath 文件地址1
     * @param fileUrl 文件地址2
     * @param fileName 文件名称
     * @return
     */
    public static String uploadFile(MultipartFile file, String  filePath, String fileUrl, String fileName){

        String dbUrl = null;
        try {
            //设置存储位置
            File path = new File(ResourceUtils.getURL(filePath).getPath());
            File upload = new File(path.getAbsolutePath(),fileUrl);
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
            //如果没有创建对应文件夹
            if (!upload.exists()){
                upload.mkdirs();
            }
            //上传图片
            File dest = new File(upload.getAbsolutePath()+"/" + fileName + suffix);
            file.transferTo(dest);
            dbUrl=fileUrl + fileName + suffix;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dbUrl;
    }

    public static List<String> uploadFiles(String  filePath, String fileUrl, String fileName, HttpServletRequest request){
        String dbUrl = null;
        List<String> rerurnUrl = new ArrayList<String>();
        List<MultipartFile> files = ((MultipartHttpServletRequest)  request).getFiles("file");
        for (int i = 0; i<files.size();i++){
            MultipartFile file = files.get(i);
            File path = null;
            try {
                path = new File(ResourceUtils.getURL(filePath).getPath());

                File upload = new File(path.getAbsolutePath(),fileUrl);
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
                //如果没有创建对应文件夹
                if (!upload.exists()){upload.mkdirs();}
                //上传图片
                File dest = new File(upload.getAbsolutePath()+"/" +(i+1)+"_"+ fileName + suffix);

                file.transferTo(dest);
                dbUrl=fileUrl + (i+1) + "_" + fileName + suffix;
                rerurnUrl.add(dbUrl);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return rerurnUrl;
    }
}
