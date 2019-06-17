package com.memory.common.utils;

import com.memory.common.yml.MyFileConfig;
import com.memory.file.controller.FileController;
import com.memory.redis.CacheConstantConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * file相关 的util方法
 * @author INS6+
 * @date 2019/5/10 11:12
 */
@Component
public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    private static FileUtils fileUtils;

    @Autowired
    private MyFileConfig config;


    @PostConstruct
    public void init(){
        fileUtils = this;
    }


    public static String upload(MultipartFile file, String fileUploadedPath,String fileName) {
        try {

            log.info("上传的文件名为：" + fileName);
            File dest = new File(fileUploadedPath + "/" + fileName);
            System.out.println("上传路径==" +fileUploadedPath);
            System.out.println("上傳路徑檢測= " +dest.getParentFile() );
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String upload(MultipartFile file, String fileUploadedPath,String customPath,String fileName) {
        String returnFileName = "";
        try {
            File dest = new File(fileUploadedPath + "/" + customPath + "/" + fileName);
            returnFileName =  customPath + "/" + fileName;
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnFileName;
    }



 /*   public static String upload(MultipartFile file, String fileUploadedPath,String fileName,String UUID) {
        String returnFileName = "";
        try {

            log.info("上传的文件名为：" + fileName);
            fileUploadedPath = fileUploadedPath +"/" +UUID;
            File dest = new File(fileUploadedPath + "/" + fileName);
            System.out.println("上传路径==" +fileUploadedPath);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            returnFileName = UUID + "/" + fileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnFileName;
    }
*/

    public static List<Object> handleFileUpload(HttpServletRequest request, String fileUploadedPath) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        Map<String,Object> map = null;
        List<Object> record = new ArrayList<>();
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    String fileName =  file.getOriginalFilename();
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    fileName = Utils.getShortUUTimeStamp()  + suffixName;
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


    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static boolean  downLoadFromUrl(String urlStr,String fileName,String savePath) {
        boolean flag =false;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            System.out.println("文件保存路径====" + savePath);
            System.out.println("文件名称=====" + fileName);

            File file = new File(savePath+"/"+fileName);
            System.out.println("文件判斷路徑 == " + file.getParentFile());

            if(!file.getParentFile().exists()){
                //注意.mkdirs() 和 .mkdir() 的区别,后者只会创建一个层级的文件夹
                file.getParentFile().mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);

            if(fos!=null){
                fos.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }
            flag = true;
            log.info("Url:【"+urlStr+"】下载成功.存储文件【"+ savePath+"/"+fileName+"】");
            System.out.println("Url:【"+urlStr+"】下载成功.存储文件【"+ savePath+"/"+fileName+"】");
        }catch (Exception e){
            e.printStackTrace();
            log.error("Url:【"+urlStr+"】下载失败",e.getMessage());
        }
        return flag;
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


    /**
     * 获取音频文件名称
     * @param prefix
     * @param audioFile
     * @return
     */
    public static String getAudioFileName(String prefix, MultipartFile audioFile){
        String fileNameReal =  audioFile.getOriginalFilename();
        String suffix = fileNameReal.substring(fileNameReal.lastIndexOf("."));
        String dayStr = DateUtils.getDate("yyyyMMdd");
        String hoursStr = DateUtils.getDate("HHmmss");
        return   prefix + "_" + dayStr + "_" + hoursStr + suffix;
    }


    /**
     * 获取音频文件名称
     * @param prefix
     * @param realFileName
     * @return
     */
    public static String getAudioFileName(String prefix, String realFileName){
        String suffix = realFileName.substring(realFileName.lastIndexOf("."));
        String dayStr = DateUtils.getDate("yyyyMMdd");
        String hoursStr = DateUtils.getDate("HHmmss");
        return   prefix + "_" + dayStr + "_" + hoursStr + suffix;
    }


    /**
     * 获取图片文件名称
     * @param prefix
     * @return
     */
    public static String getImgFileName(String prefix){
        //String fileNameReal =  imgFile.getOriginalFilename();
        //String suffix = fileNameReal.substring(fileNameReal.lastIndexOf("."));
        String suffix = ".png";
        String dayStr = DateUtils.getDate("yyyyMMdd");
        String hoursStr = DateUtils.getDate("HHmmss");
        return   prefix + "_" + dayStr + "_" + hoursStr + suffix;
    }

    /**
     * 获取Cms上传路径
     * @return
     */
    public static String getLocalPath(){
            return fileUtils.config.getUpload_local_path() ;
    }


    public static String getCustomCmsPath(String dir,String UUID){
        String cmsPath = fileUtils.config.getCms_path();
        if(UUID!=null && !"".equals(UUID)){
            return cmsPath+ "/" + dir + "/"+UUID;
        }else {
            return cmsPath+ "/" + dir ;
        }
    }





    /**
     * 扫描指定路径下的文件
     * @param path
     * @return
     */
    public static ArrayList<File> getFiles(String path){
        ArrayList<File> fileArrayList = new ArrayList<File>();
        try{
            File file = new File(path);
            if(file.isDirectory()){
                File [] files = file.listFiles();
                for (File fileIndex : files) {
                    //如果这个文件是个目录则递归遍历
                    if(fileIndex.isDirectory()){
                        getFiles(fileIndex.getPath());
                    }else {
                        fileArrayList.add(fileIndex);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return fileArrayList;
    }


}
