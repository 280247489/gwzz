package com.memory.entity.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/7/3 13:53
 */

@Entity
public class Feedback {
    private String id;
    private String userId;
    private String feedbackType;
    private String feedbackContent;
    private String feedbackName;
    private String feedbackContactUs;
    private String feedbackImg;
    private Date feedbackCreateTime;

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
    @Column(name = "feedback_type")
    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    @Basic
    @Column(name = "feedback_content")
    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    @Basic
    @Column(name = "feedback_name")
    public String getFeedbackName() {
        return feedbackName;
    }

    public void setFeedbackName(String feedbackName) {
        this.feedbackName = feedbackName;
    }

    @Basic
    @Column(name = "feedback_contact_us")
    public String getFeedbackContactUs() {
        return feedbackContactUs;
    }

    public void setFeedbackContactUs(String feedbackContactUs) {
        this.feedbackContactUs = feedbackContactUs;
    }

    @Basic
    @Column(name = "feedback_img")
    public String getFeedbackImg() {
        return feedbackImg;
    }

    public void setFeedbackImg(String feedbackImg) {
        this.feedbackImg = feedbackImg;
    }

    @Basic
    @Column(name = "feedback_create_time")
    public Date getFeedbackCreateTime() {
        return feedbackCreateTime;
    }

    public void setFeedbackCreateTime(Date feedbackCreateTime) {
        this.feedbackCreateTime = feedbackCreateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) &&
                Objects.equals(userId, feedback.userId) &&
                Objects.equals(feedbackType, feedback.feedbackType) &&
                Objects.equals(feedbackContent, feedback.feedbackContent) &&
                Objects.equals(feedbackName, feedback.feedbackName) &&
                Objects.equals(feedbackContactUs, feedback.feedbackContactUs) &&
                Objects.equals(feedbackImg, feedback.feedbackImg) &&
                Objects.equals(feedbackCreateTime, feedback.feedbackCreateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, feedbackType, feedbackContent, feedbackName, feedbackContactUs, feedbackImg, feedbackCreateTime);
    }
}
