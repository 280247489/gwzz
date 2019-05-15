package com.memory.entity.bean;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author INS6+
 * @date 2019/5/14 16:10
 */

public class JoditData {

    private String baseurl;

    private ArrayList<String> message = new ArrayList<String>();

    private ArrayList<String> files = new ArrayList<String>();

    private ArrayList<Boolean> isImages = new ArrayList<Boolean>();

    private Integer code;

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }

    public ArrayList<Boolean> getIsImages() {
        return isImages;
    }

    public void setIsImages(ArrayList<Boolean> isImages) {
        this.isImages = isImages;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public JoditData() {
    }

    public JoditData(String baseurl, ArrayList<String> message, ArrayList<String> files, ArrayList<Boolean> isImages, Integer code) {
        this.baseurl = baseurl;
        this.message = message;
        this.files = files;
        this.isImages = isImages;
        this.code = code;
    }
}


