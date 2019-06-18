package com.memory.gwzz.model;

import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/11 15:16
 */

public class Course {
    private String id;
    private String courseTitle;
    private String courseLogo;
    private String courseLabel;
    private int courseOnline;
    private int courseTotalComment;
    private int courseTotalView;
    private Date courseReleaseTime;

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

    public String getCourseLogo() {
        return courseLogo;
    }

    public void setCourseLogo(String courseLogo) {
        this.courseLogo = courseLogo;
    }

    public String getCourseLabel() {
        return courseLabel;
    }

    public void setCourseLabel(String courseLabel) {
        this.courseLabel = courseLabel;
    }

    public int getCourseOnline() {
        return courseOnline;
    }

    public void setCourseOnline(int courseOnline) {
        this.courseOnline = courseOnline;
    }

    public int getCourseTotalComment() {
        return courseTotalComment;
    }

    public void setCourseTotalComment(int courseTotalComment) {
        this.courseTotalComment = courseTotalComment;
    }

    public int getCourseTotalView() {
        return courseTotalView;
    }

    public void setCourseTotalView(int courseTotalView) {
        this.courseTotalView = courseTotalView;
    }

    public Date getCourseReleaseTime() {
        return courseReleaseTime;
    }

    public void setCourseReleaseTime(Date courseReleaseTime) {
        this.courseReleaseTime = courseReleaseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseOnline == course.courseOnline &&
                courseTotalComment == course.courseTotalComment &&
                courseTotalView == course.courseTotalView &&
                Objects.equals(id, course.id) &&
                Objects.equals(courseTitle, course.courseTitle) &&
                Objects.equals(courseLogo, course.courseLogo) &&
                Objects.equals(courseLabel, course.courseLabel) &&
                Objects.equals(courseReleaseTime, course.courseReleaseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseTitle, courseLogo, courseLabel, courseOnline, courseTotalComment, courseTotalView, courseReleaseTime);
    }

    public Course() {
    }

    public Course(String id, String courseTitle, String courseLogo, String courseLabel, int courseOnline, int courseTotalComment, int courseTotalView, Date courseReleaseTime) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.courseLogo = courseLogo;
        this.courseLabel = courseLabel;
        this.courseOnline = courseOnline;
        this.courseTotalComment = courseTotalComment;
        this.courseTotalView = courseTotalView;
        this.courseReleaseTime = courseReleaseTime;
    }
}
