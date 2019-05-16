package com.memory.entity.bean;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/15 13:04
 */

public class MultipartFileModel {

    private List<MultipartFile> files;

    public List<MultipartFile> getList() {
        return files;
    }

    public void setList(List<MultipartFile> files) {
        this.files = files;
    }
}
