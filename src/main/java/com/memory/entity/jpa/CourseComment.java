package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/6/19 17:58
 */

@Entity
@Table(name = "course_comment", schema = "gwzz_db", catalog = "")
public class CourseComment {
    private String id;
    private String courseId;
    private String userId;
    private String userLogo;
    private String userName;
    private int commentType;
    private String commentRootId;
    private String commentParentId;
    private String commentParentUserName;
    private String commentContent;
    private String commentParentContent;
    private String commentContentReplace;
    private Date commentCreateTime;
    private int commentTotalLike;

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
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_logo")
    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "comment_type")
    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    @Basic
    @Column(name = "comment_root_id")
    public String getCommentRootId() {
        return commentRootId;
    }

    public void setCommentRootId(String commentRootId) {
        this.commentRootId = commentRootId;
    }

    @Basic
    @Column(name = "comment_parent_id")
    public String getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(String commentParentId) {
        this.commentParentId = commentParentId;
    }

    @Basic
    @Column(name = "comment_parent_user_name")
    public String getCommentParentUserName() {
        return commentParentUserName;
    }

    public void setCommentParentUserName(String commentParentUserName) {
        this.commentParentUserName = commentParentUserName;
    }

    @Basic
    @Column(name = "comment_content")
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Basic
    @Column(name = "comment_parent_content")
    public String getCommentParentContent() {
        return commentParentContent;
    }

    public void setCommentParentContent(String commentParentContent) {
        this.commentParentContent = commentParentContent;
    }

    @Basic
    @Column(name = "comment_content_replace")
    public String getCommentContentReplace() {
        return commentContentReplace;
    }

    public void setCommentContentReplace(String commentContentReplace) {
        this.commentContentReplace = commentContentReplace;
    }

    @Basic
    @Column(name = "comment_create_time")
    public Date getCommentCreateTime() {
        return commentCreateTime;
    }

    public void setCommentCreateTime(Date commentCreateTime) {
        this.commentCreateTime = commentCreateTime;
    }

    @Basic
    @Column(name = "comment_total_like")
    public int getCommentTotalLike() {
        return commentTotalLike;
    }

    public void setCommentTotalLike(int commentTotalLike) {
        this.commentTotalLike = commentTotalLike;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseComment that = (CourseComment) o;
        return commentType == that.commentType &&
                commentTotalLike == that.commentTotalLike &&
                Objects.equals(id, that.id) &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(userLogo, that.userLogo) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(commentRootId, that.commentRootId) &&
                Objects.equals(commentParentId, that.commentParentId) &&
                Objects.equals(commentParentUserName, that.commentParentUserName) &&
                Objects.equals(commentContent, that.commentContent) &&
                Objects.equals(commentParentContent, that.commentParentContent) &&
                Objects.equals(commentContentReplace, that.commentContentReplace) &&
                Objects.equals(commentCreateTime, that.commentCreateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, userId, userLogo, userName, commentType, commentRootId, commentParentId, commentParentUserName, commentContent, commentParentContent, commentContentReplace, commentCreateTime, commentTotalLike);
    }
}
