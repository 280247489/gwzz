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
    private String albumId;
    private int courseNumber;
    private String courseTitle;
    private String courseLogo;
    private String courseContent;
    private String courseAudioUrl;
    private String courseVideoUrl;
    private String courseLabel;
    private String courseKeyWords;
    private int courseOnline;
    private int courseTotalView;
    private int courseTotalShare;
    private int courseTotalLike;
    private int courseTotalComment;
    private Date courseReleaseTime;
    private Date courseCreateTime;
    private String courseCreateId;
    private Date courseUpdateTime;
    private String courseUpdateId;
    private int courseRecommend;
    private String courseDescribe;
    private int courseLiveStatus;

    private String masterId;
    private String masterTitle;
    private String courseId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }


    public int getCourseOnline() {
        return courseOnline;
    }

    public void setCourseOnline(int courseOnline) {
        this.courseOnline = courseOnline;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
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

    public int getCourseTotalView() {
        return courseTotalView;
    }

    public void setCourseTotalView(int courseTotalView) {
        this.courseTotalView = courseTotalView;
    }

    public int getCourseTotalShare() {
        return courseTotalShare;
    }

    public void setCourseTotalShare(int courseTotalShare) {
        this.courseTotalShare = courseTotalShare;
    }

    public int getCourseTotalLike() {
        return courseTotalLike;
    }

    public void setCourseTotalLike(int courseTotalLike) {
        this.courseTotalLike = courseTotalLike;
    }

    public int getCourseTotalComment() {
        return courseTotalComment;
    }

    public void setCourseTotalComment(int courseTotalComment) {
        this.courseTotalComment = courseTotalComment;
    }

    public Date getCourseReleaseTime() {
        return courseReleaseTime;
    }

    public void setCourseReleaseTime(Date courseReleaseTime) {
        this.courseReleaseTime = courseReleaseTime;
    }

    public Date getCourseCreateTime() {
        return courseCreateTime;
    }

    public void setCourseCreateTime(Date courseCreateTime) {
        this.courseCreateTime = courseCreateTime;
    }

    public String getCourseCreateId() {
        return courseCreateId;
    }

    public void setCourseCreateId(String courseCreateId) {
        this.courseCreateId = courseCreateId;
    }

    public Date getCourseUpdateTime() {
        return courseUpdateTime;
    }

    public void setCourseUpdateTime(Date courseUpdateTime) {
        this.courseUpdateTime = courseUpdateTime;
    }

    public String getCourseUpdateId() {
        return courseUpdateId;
    }

    public void setCourseUpdateId(String courseUpdateId) {
        this.courseUpdateId = courseUpdateId;
    }

    public int getCourseRecommend() {
        return courseRecommend;
    }

    public void setCourseRecommend(int courseRecommend) {
        this.courseRecommend = courseRecommend;
    }

    public String getCourseDescribe() {
        return courseDescribe;
    }

    public void setCourseDescribe(String courseDescribe) {
        this.courseDescribe = courseDescribe;
    }

    public int getCourseLiveStatus() {
        return courseLiveStatus;
    }

    public void setCourseLiveStatus(int courseLiveStatus) {
        this.courseLiveStatus = courseLiveStatus;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getMasterTitle() {
        return masterTitle;
    }

    public void setMasterTitle(String masterTitle) {
        this.masterTitle = masterTitle;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Course() {
    }


    public Course(String id, String courseTitle, int courseOnline) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.courseOnline = courseOnline;
    }

    public Course(String id, String courseTypeId, String albumId, int courseNumber, String courseTitle, String courseLogo, String courseContent, String courseAudioUrl, String courseVideoUrl, String courseLabel, String courseKeyWords, int courseOnline, int courseTotalView, int courseTotalShare, int courseTotalLike, int courseTotalComment, Date courseReleaseTime, Date courseCreateTime, String courseCreateId, Date courseUpdateTime, String courseUpdateId, int courseRecommend, String courseDescribe, int courseLiveStatus, String masterId, String masterTitle, String courseId) {
        this.id = id;
        this.courseTypeId = courseTypeId;
        this.albumId = albumId;
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
        this.courseLogo = courseLogo;
        this.courseContent = courseContent;
        this.courseAudioUrl = courseAudioUrl;
        this.courseVideoUrl = courseVideoUrl;
        this.courseLabel = courseLabel;
        this.courseKeyWords = courseKeyWords;
        this.courseOnline = courseOnline;
        this.courseTotalView = courseTotalView;
        this.courseTotalShare = courseTotalShare;
        this.courseTotalLike = courseTotalLike;
        this.courseTotalComment = courseTotalComment;
        this.courseReleaseTime = courseReleaseTime;
        this.courseCreateTime = courseCreateTime;
        this.courseCreateId = courseCreateId;
        this.courseUpdateTime = courseUpdateTime;
        this.courseUpdateId = courseUpdateId;
        this.courseRecommend = courseRecommend;
        this.courseDescribe = courseDescribe;
        this.courseLiveStatus = courseLiveStatus;
        this.masterId = masterId;
        this.masterTitle = masterTitle;
        this.courseId = courseId;
    }
}
