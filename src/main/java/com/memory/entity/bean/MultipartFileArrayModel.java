package com.memory.entity.bean;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/15 13:04
 */

public class MultipartFileArrayModel {

   private ArrayList<MultipartFile> files = new ArrayList<MultipartFile>();

    public ArrayList<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<MultipartFile> files) {
        this.files = files;
    }
}
