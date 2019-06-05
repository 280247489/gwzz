package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName CourseCommentLike
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 16:35
 */
@Entity
@Table(name = "course_comment_like", schema = "gwzz_db", catalog = "")
public class CourseCommentLike {
    private String id;
    private String userId;
    private String commentId;
    private int commentLikeYn;
    private Date createTime;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "comment_id")
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "comment_like_yn")
    public int getCommentLikeYn() {
        return commentLikeYn;
    }

    public void setCommentLikeYn(int commentLikeYn) {
        this.commentLikeYn = commentLikeYn;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseCommentLike that = (CourseCommentLike) o;
        return commentLikeYn == that.commentLikeYn &&
                Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(commentId, that.commentId) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, commentId, commentLikeYn, createTime);
    }
}
