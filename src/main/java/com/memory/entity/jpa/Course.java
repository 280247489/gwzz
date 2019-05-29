package com.memory.entity.jpa;

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

@Entity
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
    private int courseTotalView;
    private int courseTotalShare;
    private int courseTotalLike;
    private Date courseCreateTime;
    private String courseCreateId;
    private Date courseUpdateTime;
    private String courseUpdateId;
    private int courseRecommend;
    private String courseDescribe;
    private int courseLiveStatus;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course_type_id")
    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    @Basic
    @Column(name = "course_title")
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @Basic
    @Column(name = "course_logo")
    public String getCourseLogo() {
        return courseLogo;
    }

    public void setCourseLogo(String courseLogo) {
        this.courseLogo = courseLogo;
    }

    @Basic
    @Column(name = "course_content")
    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    @Basic
    @Column(name = "course_audio_url")
    public String getCourseAudioUrl() {
        return courseAudioUrl;
    }

    public void setCourseAudioUrl(String courseAudioUrl) {
        this.courseAudioUrl = courseAudioUrl;
    }

    @Basic
    @Column(name = "course_video_url")
    public String getCourseVideoUrl() {
        return courseVideoUrl;
    }

    public void setCourseVideoUrl(String courseVideoUrl) {
        this.courseVideoUrl = courseVideoUrl;
    }

    @Basic
    @Column(name = "course_label")
    public String getCourseLabel() {
        return courseLabel;
    }

    public void setCourseLabel(String courseLabel) {
        this.courseLabel = courseLabel;
    }

    @Basic
    @Column(name = "course_key_words")
    public String getCourseKeyWords() {
        return courseKeyWords;
    }

    public void setCourseKeyWords(String courseKeyWords) {
        this.courseKeyWords = courseKeyWords;
    }

    @Basic
    @Column(name = "course_online")
    public int getCourseOnline() {
        return courseOnline;
    }

    public void setCourseOnline(int courseOnline) {
        this.courseOnline = courseOnline;
    }

    @Basic
    @Column(name = "course_total_view")
    public int getCourseTotalView() {
        return courseTotalView;
    }

    public void setCourseTotalView(int courseTotalView) {
        this.courseTotalView = courseTotalView;
    }

    @Basic
    @Column(name = "course_total_share")
    public int getCourseTotalShare() {
        return courseTotalShare;
    }

    public void setCourseTotalShare(int courseTotalShare) {
        this.courseTotalShare = courseTotalShare;
    }

    @Basic
    @Column(name = "course_total_like")
    public int getCourseTotalLike() {
        return courseTotalLike;
    }

    public void setCourseTotalLike(int courseTotalLike) {
        this.courseTotalLike = courseTotalLike;
    }

    @Basic
    @Column(name = "course_create_time")
    public Date getCourseCreateTime() {
        return courseCreateTime;
    }

    public void setCourseCreateTime(Date courseCreateTime) {
        this.courseCreateTime = courseCreateTime;
    }

    @Basic
    @Column(name = "course_create_id")
    public String getCourseCreateId() {
        return courseCreateId;
    }

    public void setCourseCreateId(String courseCreateId) {
        this.courseCreateId = courseCreateId;
    }

    @Basic
    @Column(name = "course_update_time")
    public Date getCourseUpdateTime() {
        return courseUpdateTime;
    }

    public void setCourseUpdateTime(Date courseUpdateTime) {
        this.courseUpdateTime = courseUpdateTime;
    }

    @Basic
    @Column(name = "course_update_id")
    public String getCourseUpdateId() {
        return courseUpdateId;
    }

    public void setCourseUpdateId(String courseUpdateId) {
        this.courseUpdateId = courseUpdateId;
    }

    @Basic
    @Column(name = "course_recommend")
    public int getCourseRecommend() {
        return courseRecommend;
    }

    public void setCourseRecommend(int courseRecommend) {
        this.courseRecommend = courseRecommend;
    }

    @Basic
    @Column(name = "course_describe")
    public String getCourseDescribe() {
        return courseDescribe;
    }

    public void setCourseDescribe(String courseDescribe) {
        this.courseDescribe = courseDescribe;
    }

    @Basic
    @Column(name = "course_live_status")
    public int getCourseLiveStatus() {
        return courseLiveStatus;
    }

    public void setCourseLiveStatus(int courseLiveStatus) {
        this.courseLiveStatus = courseLiveStatus;
    }


    public Course(String id, String courseTypeId, String courseTitle, String courseLogo, String courseContent, String courseAudioUrl, String courseVideoUrl, String courseLabel, String courseKeyWords, int courseOnline, int courseTotalView, int courseTotalShare, int courseTotalLike, Date courseCreateTime, String courseCreateId, Date courseUpdateTime, String courseUpdateId, int courseRecommend, String courseDescribe, int courseLiveStatus) {
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
        this.courseTotalView = courseTotalView;
        this.courseTotalShare = courseTotalShare;
        this.courseTotalLike = courseTotalLike;
        this.courseCreateTime = courseCreateTime;
        this.courseCreateId = courseCreateId;
        this.courseUpdateTime = courseUpdateTime;
        this.courseUpdateId = courseUpdateId;
        this.courseRecommend = courseRecommend;
        this.courseDescribe = courseDescribe;
        this.courseLiveStatus = courseLiveStatus;
    }

    public Course() {
    }


    public Course(String id, String courseTitle, int courseOnline) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.courseOnline = courseOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseOnline == course.courseOnline &&
                courseTotalView == course.courseTotalView &&
                courseTotalShare == course.courseTotalShare &&
                courseTotalLike == course.courseTotalLike &&
                courseRecommend == course.courseRecommend &&
                courseLiveStatus == course.courseLiveStatus &&
                Objects.equals(id, course.id) &&
                Objects.equals(courseTypeId, course.courseTypeId) &&
                Objects.equals(courseTitle, course.courseTitle) &&
                Objects.equals(courseLogo, course.courseLogo) &&
                Objects.equals(courseContent, course.courseContent) &&
                Objects.equals(courseAudioUrl, course.courseAudioUrl) &&
                Objects.equals(courseVideoUrl, course.courseVideoUrl) &&
                Objects.equals(courseLabel, course.courseLabel) &&
                Objects.equals(courseKeyWords, course.courseKeyWords) &&
                Objects.equals(courseCreateTime, course.courseCreateTime) &&
                Objects.equals(courseCreateId, course.courseCreateId) &&
                Objects.equals(courseUpdateTime, course.courseUpdateTime) &&
                Objects.equals(courseUpdateId, course.courseUpdateId) &&
                Objects.equals(courseDescribe, course.courseDescribe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseTypeId, courseTitle, courseLogo, courseContent, courseAudioUrl, courseVideoUrl, courseLabel, courseKeyWords, courseOnline, courseTotalView, courseTotalShare, courseTotalLike, courseCreateTime, courseCreateId, courseUpdateTime, courseUpdateId, courseRecommend, courseDescribe, courseLiveStatus);
    }
}
