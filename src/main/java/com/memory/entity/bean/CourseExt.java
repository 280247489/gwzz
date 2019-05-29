package com.memory.entity.bean;

import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/11 15:16
 */

public class CourseExt {
    private String id;
    private String courseId;
    private String courseExtNickname;
    private String courseExtLogo;
    private int courseExtType;
    private String courseExtWords;
    private String courseExtImgUrl;
    private String courseExtAudio;
    private int courseExtAudioTimes;
    private int courseExtSort;
    private Date courseExtCreateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseExtNickname() {
        return courseExtNickname;
    }

    public void setCourseExtNickname(String courseExtNickname) {
        this.courseExtNickname = courseExtNickname;
    }

    public String getCourseExtLogo() {
        return courseExtLogo;
    }

    public void setCourseExtLogo(String courseExtLogo) {
        this.courseExtLogo = courseExtLogo;
    }

    public int getCourseExtType() {
        return courseExtType;
    }

    public void setCourseExtType(int courseExtType) {
        this.courseExtType = courseExtType;
    }

    public String getCourseExtWords() {
        return courseExtWords;
    }

    public void setCourseExtWords(String courseExtWords) {
        this.courseExtWords = courseExtWords;
    }

    public String getCourseExtImgUrl() {
        return courseExtImgUrl;
    }

    public void setCourseExtImgUrl(String courseExtImgUrl) {
        this.courseExtImgUrl = courseExtImgUrl;
    }

    public String getCourseExtAudio() {
        return courseExtAudio;
    }

    public void setCourseExtAudio(String courseExtAudio) {
        this.courseExtAudio = courseExtAudio;
    }

    public int getCourseExtAudioTimes() {
        return courseExtAudioTimes;
    }

    public void setCourseExtAudioTimes(int courseExtAudioTimes) {
        this.courseExtAudioTimes = courseExtAudioTimes;
    }

    public int getCourseExtSort() {
        return courseExtSort;
    }

    public void setCourseExtSort(int courseExtSort) {
        this.courseExtSort = courseExtSort;
    }

    public Date getCourseExtCreateTime() {
        return courseExtCreateTime;
    }

    public void setCourseExtCreateTime(Date courseExtCreateTime) {
        this.courseExtCreateTime = courseExtCreateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseExt courseExt = (CourseExt) o;
        return courseExtType == courseExt.courseExtType &&
                courseExtAudioTimes == courseExt.courseExtAudioTimes &&
                courseExtSort == courseExt.courseExtSort &&
                Objects.equals(id, courseExt.id) &&
                Objects.equals(courseId, courseExt.courseId) &&
                Objects.equals(courseExtNickname, courseExt.courseExtNickname) &&
                Objects.equals(courseExtLogo, courseExt.courseExtLogo) &&
                Objects.equals(courseExtWords, courseExt.courseExtWords) &&
                Objects.equals(courseExtImgUrl, courseExt.courseExtImgUrl) &&
                Objects.equals(courseExtAudio, courseExt.courseExtAudio) &&
                Objects.equals(courseExtCreateTime, courseExt.courseExtCreateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, courseExtNickname, courseExtLogo, courseExtType, courseExtWords, courseExtImgUrl, courseExtAudio, courseExtAudioTimes, courseExtSort, courseExtCreateTime);
    }

    public CourseExt(){

    };

    public CourseExt(String id, String courseId, String courseExtNickname, String courseExtLogo, int courseExtType, String courseExtWords, String courseExtImgUrl, String courseExtAudio, int courseExtAudioTimes, int courseExtSort, Date courseExtCreateTime) {
        this.id = id;
        this.courseId = courseId;
        this.courseExtNickname = courseExtNickname;
        this.courseExtLogo = courseExtLogo;
        this.courseExtType = courseExtType;
        this.courseExtWords = courseExtWords;
        this.courseExtImgUrl = courseExtImgUrl;
        this.courseExtAudio = courseExtAudio;
        this.courseExtAudioTimes = courseExtAudioTimes;
        this.courseExtSort = courseExtSort;
        this.courseExtCreateTime = courseExtCreateTime;
    }
}
