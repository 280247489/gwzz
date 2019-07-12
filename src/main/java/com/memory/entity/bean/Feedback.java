package com.memory.entity.bean;

import java.util.Date;

/**
 * @author INS6+
 * @date 2019/7/12 15:51
 */

public class Feedback {
    private String id;
    private String userId;
    private String feedbackType;
    private String feedbackContent;
    private String feedbackName;
    private String feedbackContactUs;
    private String feedbackImg;
    private Date feedbackCreateTime;
    private String userName;

    public Feedback(String id, String userId, String feedbackType, String feedbackContent, String feedbackName, String feedbackContactUs, String feedbackImg, Date feedbackCreateTime, String userName) {
        this.id = id;
        this.userId = userId;
        this.feedbackType = feedbackType;
        this.feedbackContent = feedbackContent;
        this.feedbackName = feedbackName;
        this.feedbackContactUs = feedbackContactUs;
        this.feedbackImg = feedbackImg;
        this.feedbackCreateTime = feedbackCreateTime;
        this.userName = userName;
    }

    public Feedback() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getFeedbackName() {
        return feedbackName;
    }

    public void setFeedbackName(String feedbackName) {
        this.feedbackName = feedbackName;
    }

    public String getFeedbackContactUs() {
        return feedbackContactUs;
    }

    public void setFeedbackContactUs(String feedbackContactUs) {
        this.feedbackContactUs = feedbackContactUs;
    }

    public String getFeedbackImg() {
        return feedbackImg;
    }

    public void setFeedbackImg(String feedbackImg) {
        this.feedbackImg = feedbackImg;
    }

    public Date getFeedbackCreateTime() {
        return feedbackCreateTime;
    }

    public void setFeedbackCreateTime(Date feedbackCreateTime) {
        this.feedbackCreateTime = feedbackCreateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", feedbackType='" + feedbackType + '\'' +
                ", feedbackContent='" + feedbackContent + '\'' +
                ", feedbackName='" + feedbackName + '\'' +
                ", feedbackContactUs='" + feedbackContactUs + '\'' +
                ", feedbackImg='" + feedbackImg + '\'' +
                ", feedbackCreateTime=" + feedbackCreateTime +
                ", userName='" + userName + '\'' +
                '}';
    }
}
