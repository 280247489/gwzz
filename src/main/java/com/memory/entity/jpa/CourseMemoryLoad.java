package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/28 17:03
 */

@Entity
@Table(name = "course_memory_load", schema = "gwzz_db", catalog = "")
public class CourseMemoryLoad {
    private String courseId;
    private String content;
    private String operator;
    private int loadStatus;
    private Date createTime;
    private Date updateTime;
    private String courseRedisKey;

    @Id
    @Column(name = "course_id")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "operator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "load_status")
    public int getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(int loadStatus) {
        this.loadStatus = loadStatus;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "course_redis_key")
    public String getCourseRedisKey() {
        return courseRedisKey;
    }

    public void setCourseRedisKey(String courseRedisKey) {
        this.courseRedisKey = courseRedisKey;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseMemoryLoad that = (CourseMemoryLoad) o;
        return loadStatus == that.loadStatus &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(content, that.content) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(courseRedisKey, that.courseRedisKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, content, operator, loadStatus, createTime, updateTime, courseRedisKey);
    }
}
