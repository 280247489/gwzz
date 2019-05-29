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
    private String courseTitle;
    private int courseOnline;



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




    public Course() {
    }


    public Course(String id, String courseTitle, int courseOnline) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.courseOnline = courseOnline;
    }
}
