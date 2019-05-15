package com.memory.entity.bean;

import java.util.Arrays;

/**
 * @author INS6+
 * @date 2019/5/14 16:10
 */

public class JoditData {

    private String baseurl;

    private String [] message;

    private String [] files;

    private Boolean [] isImages;

    private Integer code;

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public Boolean[] getIsImages() {
        return isImages;
    }

    public void setIsImages(Boolean[] isImages) {
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

    public JoditData(String baseurl, String[] message, String[] files, Boolean[] isImages, Integer code) {
        this.baseurl = baseurl;
        this.message = message;
        this.files = files;
        this.isImages = isImages;
        this.code = code;
    }

    @Override
    public String toString() {
        return "JoditData{" +
                "baseurl='" + baseurl + '\'' +
                ", message=" + Arrays.toString(message) +
                ", files=" + Arrays.toString(files) +
                ", isImages=" + Arrays.toString(isImages) +
                ", code=" + code +
                '}';
    }
}
