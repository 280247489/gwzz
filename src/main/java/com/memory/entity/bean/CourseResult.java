package com.memory.entity.bean;

/**
 * @author INS6+
 * @date 2019/5/22 10:15
 */

public class CourseResult {

    private String course;
    private CourseExt courseExt;

    public CourseResult(){

    }

    public CourseResult(String course, CourseExt courseExt) {
        this.course = course;
        this.courseExt = courseExt;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public CourseExt getCourseExt() {
        return courseExt;
    }

    public void setCourseExt(CourseExt courseExt) {
        this.courseExt = courseExt;
    }

    @Override
    public String toString() {
        return "CourseResult{" +
                "course='" + course + '\'' +
                ", courseExt=" + courseExt +
                '}';
    }
}
