package com.memory.entity.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/6/19 17:46
 */

@Entity
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
    @Column(name = "album_id")
    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    @Basic
    @Column(name = "course_number")
    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
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
    @Column(name = "course_total_comment")
    public int getCourseTotalComment() {
        return courseTotalComment;
    }

    public void setCourseTotalComment(int courseTotalComment) {
        this.courseTotalComment = courseTotalComment;
    }

    @Basic
    @Column(name = "course_release_time")
    public Date getCourseReleaseTime() {
        return courseReleaseTime;
    }

    public void setCourseReleaseTime(Date courseReleaseTime) {
        this.courseReleaseTime = courseReleaseTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseNumber == course.courseNumber &&
                courseOnline == course.courseOnline &&
                courseTotalView == course.courseTotalView &&
                courseTotalShare == course.courseTotalShare &&
                courseTotalLike == course.courseTotalLike &&
                courseTotalComment == course.courseTotalComment &&
                courseRecommend == course.courseRecommend &&
                courseLiveStatus == course.courseLiveStatus &&
                Objects.equals(id, course.id) &&
                Objects.equals(courseTypeId, course.courseTypeId) &&
                Objects.equals(albumId, course.albumId) &&
                Objects.equals(courseTitle, course.courseTitle) &&
                Objects.equals(courseLogo, course.courseLogo) &&
                Objects.equals(courseContent, course.courseContent) &&
                Objects.equals(courseAudioUrl, course.courseAudioUrl) &&
                Objects.equals(courseVideoUrl, course.courseVideoUrl) &&
                Objects.equals(courseLabel, course.courseLabel) &&
                Objects.equals(courseKeyWords, course.courseKeyWords) &&
                Objects.equals(courseReleaseTime, course.courseReleaseTime) &&
                Objects.equals(courseCreateTime, course.courseCreateTime) &&
                Objects.equals(courseCreateId, course.courseCreateId) &&
                Objects.equals(courseUpdateTime, course.courseUpdateTime) &&
                Objects.equals(courseUpdateId, course.courseUpdateId) &&
                Objects.equals(courseDescribe, course.courseDescribe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseTypeId, albumId, courseNumber, courseTitle, courseLogo, courseContent, courseAudioUrl, courseVideoUrl, courseLabel, courseKeyWords, courseOnline, courseTotalView, courseTotalShare, courseTotalLike, courseTotalComment, courseReleaseTime, courseCreateTime, courseCreateId, courseUpdateTime, courseUpdateId, courseRecommend, courseDescribe, courseLiveStatus);
    }
}
