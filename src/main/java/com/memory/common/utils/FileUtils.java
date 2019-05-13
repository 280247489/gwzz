package com.memory.common.utils;

import com.memory.file.controller.FileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/10 11:12
 */

public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    private static final String loadPath = "G:/upload/";

    private static final String resourcesUrl = "http://192.168.1.118:8081/upload";



    public static String upload(MultipartFile file, String fileUploadedPath,String fileName) {
        try {
       /*     if (file.isEmpty()) {
                result = ResultUtil.error(1,"文件为空");
                result.setMsg("文件为空");
            }*/
            // 获取文件名
         //   String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
           // String suffixName = fileName.substring(fileName.lastIndexOf("."));

            //fileName = Utils.generateUUID()  + suffixName;
            //log.info("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
            //  String filePath = "G:/upload/";
          //  String path = loadPath + "/" + type + "/"  + uploadDateTime + "/" + fileName;
            File dest = new File(fileUploadedPath + "/" + fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入

            //map.put("text","上传成功");
            //map.put("filePath",resourcesUrl + "/" + type  + "/" + uploadDateTime + "/" + fileName);

            //result = ResultUtil.success(map);
            //result.setMsg("文件为空");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Object> handleFileUpload(HttpServletRequest request, String fileUploadedPath) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        Map<String,Object> map = null;
        List<Object> record = new ArrayList<>();
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            //  String filePath = "G:/upload/";
            if (!file.isEmpty()) {
                try {
                    String fileName =  file.getOriginalFilename();
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    fileName = Utils.generateUUID()  + suffixName;
                    String paths = fileUploadedPath + "/" + fileName;
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(paths)));//设置文件路径及名字
                    stream.write(bytes);// 写入
                    stream.close();
                    map = new HashMap<>();
                    map.put("text","上传成功");
                    map.put("filePath",fileUploadedPath+ "/" + fileName);
                    record.add(map);
                } catch (Exception e) {
                    stream = null;
                    ResultUtil.error(-1,"第 " + i + " 个文件上传失败 ==> "
                            + e.getMessage());

                }
                ResultUtil.success(record);
            } else {
                 ResultUtil.error(-1,"第 " + i
                        + " 个文件上传失败因为文件为空");
            }
        }

        return record;
    }


    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "dalaoyang.jpeg";// 文件名
        if (fileName != null) {
            //设置文件路径
            File file = new File("/Users/dalaoyang/Documents/dalaoyang.jpeg");
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }

}
