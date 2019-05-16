package com.memory.entity.bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/11 15:16
 */

public class Course {
    private String id;
    private String courseTypeId;
    private String courseTitle;
    private String courseLogo;
    private String courseContent;
    private String courseAudioUrl;
    private String courseVideoUrl;
    private String courseLabel;
    private String courseKeyWords;
    private int courseOnline;
   /* private int courseTotalView;
    private int courseTotalShare;
    private int courseTotalLike;
    private Date courseCreateTime;
    private String courseCreateId;
    private Date courseUpdateTime;*/
    private String courseUpdateId;
   // private int courseRecommend;
    private String courseDescribe;
  //  private int courseLiveStatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseLogo() {
        return courseLogo;
    }

    public void setCourseLogo(String courseLogo) {
        this.courseLogo = courseLogo;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getCourseAudioUrl() {
        return courseAudioUrl;
    }

    public void setCourseAudioUrl(String courseAudioUrl) {
        this.courseAudioUrl = courseAudioUrl;
    }

    public String getCourseVideoUrl() {
        return courseVideoUrl;
    }

    public void setCourseVideoUrl(String courseVideoUrl) {
        this.courseVideoUrl = courseVideoUrl;
    }

    public String getCourseLabel() {
        return courseLabel;
    }

    public void setCourseLabel(String courseLabel) {
        this.courseLabel = courseLabel;
    }

    public String getCourseKeyWords() {
        return courseKeyWords;
    }

    public void setCourseKeyWords(String courseKeyWords) {
        this.courseKeyWords = courseKeyWords;
    }

    public int getCourseOnline() {
        return courseOnline;
    }

    public void setCourseOnline(int courseOnline) {
        this.courseOnline = courseOnline;
    }

    public String getCourseUpdateId() {
        return courseUpdateId;
    }

    public void setCourseUpdateId(String courseUpdateId) {
        this.courseUpdateId = courseUpdateId;
    }

    public String getCourseDescribe() {
        return courseDescribe;
    }

    public void setCourseDescribe(String courseDescribe) {
        this.courseDescribe = courseDescribe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseOnline == course.courseOnline &&
                Objects.equals(id, course.id) &&
                Objects.equals(courseTypeId, course.courseTypeId) &&
                Objects.equals(courseTitle, course.courseTitle) &&
                Objects.equals(courseLogo, course.courseLogo) &&
                Objects.equals(courseContent, course.courseContent) &&
                Objects.equals(courseAudioUrl, course.courseAudioUrl) &&
                Objects.equals(courseVideoUrl, course.courseVideoUrl) &&
                Objects.equals(courseLabel, course.courseLabel) &&
                Objects.equals(courseKeyWords, course.courseKeyWords) &&
                Objects.equals(courseUpdateId, course.courseUpdateId) &&
                Objects.equals(courseDescribe, course.courseDescribe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseTypeId, courseTitle, courseLogo, courseContent, courseAudioUrl, courseVideoUrl, courseLabel, courseKeyWords, courseOnline,  courseUpdateId,  courseDescribe);
    }

    public Course(){

    }

    public Course(String id, String courseTypeId, String courseTitle, String courseLogo, String courseContent, String courseAudioUrl, String courseVideoUrl, String courseLabel, String courseKeyWords, int courseOnline, String courseUpdateId, String courseDescribe) {
        this.id = id;
        this.courseTypeId = courseTypeId;
        this.courseTitle = courseTitle;
        this.courseLogo = courseLogo;
        this.courseContent = courseContent;
        this.courseAudioUrl = courseAudioUrl;
        this.courseVideoUrl = courseVideoUrl;
        this.courseLabel = courseLabel;
        this.courseKeyWords = courseKeyWords;
        this.courseOnline = courseOnline;
        this.courseUpdateId = courseUpdateId;
        this.courseDescribe = courseDescribe;
    }
}
