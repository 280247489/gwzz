package com.memory.cms.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/11 15:16
 */

@Entity
@Table(name = "course_ext", schema = "gwzz_db", catalog = "")
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

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course_id")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "course_ext_nickname")
    public String getCourseExtNickname() {
        return courseExtNickname;
    }

    public void setCourseExtNickname(String courseExtNickname) {
        this.courseExtNickname = courseExtNickname;
    }

    @Basic
    @Column(name = "course_ext_logo")
    public String getCourseExtLogo() {
        return courseExtLogo;
    }

    public void setCourseExtLogo(String courseExtLogo) {
        this.courseExtLogo = courseExtLogo;
    }

    @Basic
    @Column(name = "course_ext_type")
    public int getCourseExtType() {
        return courseExtType;
    }

    public void setCourseExtType(int courseExtType) {
        this.courseExtType = courseExtType;
    }

    @Basic
    @Column(name = "course_ext_words")
    public String getCourseExtWords() {
        return courseExtWords;
    }

    public void setCourseExtWords(String courseExtWords) {
        this.courseExtWords = courseExtWords;
    }

    @Basic
    @Column(name = "course_ext_img_url")
    public String getCourseExtImgUrl() {
        return courseExtImgUrl;
    }

    public void setCourseExtImgUrl(String courseExtImgUrl) {
        this.courseExtImgUrl = courseExtImgUrl;
    }

    @Basic
    @Column(name = "course_ext_audio")
    public String getCourseExtAudio() {
        return courseExtAudio;
    }

    public void setCourseExtAudio(String courseExtAudio) {
        this.courseExtAudio = courseExtAudio;
    }

    @Basic
    @Column(name = "course_ext_audio_times")
    public int getCourseExtAudioTimes() {
        return courseExtAudioTimes;
    }

    public void setCourseExtAudioTimes(int courseExtAudioTimes) {
        this.courseExtAudioTimes = courseExtAudioTimes;
    }

    @Basic
    @Column(name = "course_ext_sort")
    public int getCourseExtSort() {
        return courseExtSort;
    }

    public void setCourseExtSort(int courseExtSort) {
        this.courseExtSort = courseExtSort;
    }

    @Basic
    @Column(name = "course_ext_create_time")
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
}
