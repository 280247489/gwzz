package com.memory.entity.bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/30 10:16
 */
public class CourseComment {
    private String id;
    private String userName;
    private String tel;
    private String articleTitle;
    private String commentContent;
    private int like;
    private long commentSum;
    private Date createTime;
    private String commentRootId;
    private int commentType;
    private String commentParentId;
    private String commentParentUserName;
    private String commentContentReplace;
    private String courseId;


    public CourseComment() {
    }


    public CourseComment(String id, String userName, String tel, String articleTitle, String commentContent, int like, long commentSum, Date createTime, String commentRootId, int commentType, String commentParentId, String commentParentUserName,String commentContentReplace,String courseId) {
        this.id = id;
        this.userName = userName;
        this.tel = tel;
        this.articleTitle = articleTitle;
        this.commentContent = commentContent;
        this.like = like;
        this.commentSum = commentSum;
        this.createTime = createTime;
        this.commentRootId = commentRootId;
        this.commentType = commentType;
        this.commentParentId = commentParentId;
        this.commentParentUserName = commentParentUserName;
        this.commentContentReplace=commentContentReplace;
        this.courseId =courseId;
    }

    @Override
    public String toString() {
        return "CourseComment{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", tel='" + tel + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", like=" + like +
                ", commentSum=" + commentSum +
                ", createTime=" + createTime +
                ", commentRootId='" + commentRootId + '\'' +
                ", commentType=" + commentType +
                ", commentParentId='" + commentParentId + '\'' +
                ", commentParentUserName='" + commentParentUserName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public long getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(long commentSum) {
        this.commentSum = commentSum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCommentRootId() {
        return commentRootId;
    }

    public void setCommentRootId(String commentRootId) {
        this.commentRootId = commentRootId;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(String commentParentId) {
        this.commentParentId = commentParentId;
    }

    public String getCommentParentUserName() {
        return commentParentUserName;
    }

    public void setCommentParentUserName(String commentParentUserName) {
        this.commentParentUserName = commentParentUserName;
    }

    public String getCommentContentReplace() {
        return commentContentReplace;
    }

    public void setCommentContentReplace(String commentContentReplace) {
        this.commentContentReplace = commentContentReplace;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
