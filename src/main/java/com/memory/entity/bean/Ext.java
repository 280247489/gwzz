package com.memory.entity.bean;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author INS6+
 * @date 2019/5/10 9:15
 */

public class Ext {
    //数据类型
    private int type;
    //文字
    private String words;
    //图片url
    private String imgUrl;
    //音频url
    private String audioUrl;
    //音频时长
    private int  times;
    //昵称
    private String name;
    //音频文件
    private MultipartFile audioFile;
    //图片文件
    private MultipartFile imgFile;

    private String courseId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(MultipartFile audioFile) {
        this.audioFile = audioFile;
    }

    public MultipartFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Ext{" +
                "type=" + type +
                ", words='" + words + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                ", times=" + times +
                ", name='" + name + '\'' +
                ", audioFile=" + audioFile +
                ", imgFile=" + imgFile +
                ", courseId='" + courseId + '\'' +
                '}';
    }

}
