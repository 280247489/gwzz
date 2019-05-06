package com.memory.common.utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 13:49
 * @Description:
 */
public class FileUploadUtil {

    public static String uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        String path = filePath + fileName;
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(path);
        out.write(file);
        out.flush();
        out.close();
        return fileName;
    }
}
