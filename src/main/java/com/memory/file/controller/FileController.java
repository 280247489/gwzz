package com.memory.file.controller;

import com.memory.common.utils.DateUtils;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.common.utils.Utils;
import io.netty.handler.codec.EncoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.File;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/9 16:54
 */

@RestController
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    private static final String loadPath = "G:/upload/";

    private static final String resourcesUrl = "http://192.168.1.118:8081/upload";


//    @RequestMapping(value = "/file/upload",)
    public Result upload(@RequestParam("file") MultipartFile file,@RequestParam("type") String type) {
        Result result = new Result();
        Map<String,Object> map = new HashMap<>();
        String uploadDateTime = DateUtils.getDate("yyyyMMdd");
        try {
            if (!file.isEmpty()) {
                result = ResultUtil.error(1,"文件为空");
                result.setMsg("文件为空");
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            fileName = Utils.getShortUUTimeStamp()  + suffixName;
            log.info("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
          //  String filePath = "G:/upload/";
            String path = loadPath + "/" + type + "/"  + uploadDateTime + "/" + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入

            map.put("text","上传成功");
            map.put("filePath",resourcesUrl + "/" + type  + "/" + uploadDateTime + "/" + fileName);

            result = ResultUtil.success(map);
            //result.setMsg("文件为空");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //@PostMapping("/file/batch")
    public Result handleFileUpload(HttpServletRequest request,@RequestParam("type") String type) {
        Result result = new Result();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        Map<String,Object> map = null;
        String uploadDateTime = DateUtils.getDate("yyyyMMdd");
        List<Object> record = new ArrayList<>();
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
          //  String filePath = "G:/upload/";
            if (!file.isEmpty()) {
                try {
                    String fileName =  file.getOriginalFilename();
                    System.out.println("第 " +i+" 个文件，名称：" + fileName);
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    fileName = Utils.getShortUUTimeStamp()  + suffixName;
                    String path = loadPath + "/" + type + "/"  + uploadDateTime + "/" + fileName;
                    byte[] bytes = file.getBytes();
                    File dest = new File(path);
                    // 检测是否存在目录
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();// 新建文件夹
                    }
                    stream = new BufferedOutputStream(new FileOutputStream(
                            path));//设置文件路径及名字
                    stream.write(bytes);// 写入
                    stream.close();

                    map = new HashMap<>();
                    map.put("text","上传成功");
                    map.put("filePath",resourcesUrl + "/" + type  + "/" + uploadDateTime + "/" + fileName);
                    record.add(map);
                    result = ResultUtil.success(record);

                } catch (Exception e) {
                    stream = null;
                    result = ResultUtil.error(-1,"第 " + i + " 个文件上传失败 ==> "
                            + e.getMessage());

                }

            } else {
                result = ResultUtil.error(-1,"第 " + i
                        + " 个文件上传失败因为文件为空");
            }
        }

        return result;
    }

   // @GetMapping("/download")
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
