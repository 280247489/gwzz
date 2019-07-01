package com.memory.entity.jpa;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName LiveMaster
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/1 11:25
 */
@Entity
@Table(name = "live_master", schema = "gwzz_db", catalog = "")
public class LiveMaster {
    private String id;
    private String courseId;
    private String liveMasterName;
    private String liveMasterDescribe;
    private int liveMasterStatus;
    private int liveMasterLike;
    private int liveMasterIsSynthesisAudio;
    private String liveMasterSynthesisAudioUrl;
    private int liveMasterIsOnline;
    private int liveMasterIsRelation;
    private int liveMasterIsPush;
    private String liveMasterStarttime;
    private String liveMasterEndtime;
    private Timestamp liveMasterCreateTime;
    private String liveMasterCreateId;
    private Timestamp liveMasterUpdateTime;
    private String liveMasterUpdateId;

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
    @Column(name = "live_master_name")
    public String getLiveMasterName() {
        return liveMasterName;
    }

    public void setLiveMasterName(String liveMasterName) {
        this.liveMasterName = liveMasterName;
    }

    @Basic
    @Column(name = "live_master_describe")
    public String getLiveMasterDescribe() {
        return liveMasterDescribe;
    }

    public void setLiveMasterDescribe(String liveMasterDescribe) {
        this.liveMasterDescribe = liveMasterDescribe;
    }

    @Basic
    @Column(name = "live_master_status")
    public int getLiveMasterStatus() {
        return liveMasterStatus;
    }

    public void setLiveMasterStatus(int liveMasterStatus) {
        this.liveMasterStatus = liveMasterStatus;
    }

    @Basic
    @Column(name = "live_master_like")
    public int getLiveMasterLike() {
        return liveMasterLike;
    }

    public void setLiveMasterLike(int liveMasterLike) {
        this.liveMasterLike = liveMasterLike;
    }

    @Basic
    @Column(name = "live_master_is_synthesis_audio")
    public int getLiveMasterIsSynthesisAudio() {
        return liveMasterIsSynthesisAudio;
    }

    public void setLiveMasterIsSynthesisAudio(int liveMasterIsSynthesisAudio) {
        this.liveMasterIsSynthesisAudio = liveMasterIsSynthesisAudio;
    }

    @Basic
    @Column(name = "live_master_synthesis_audio_url")
    public String getLiveMasterSynthesisAudioUrl() {
        return liveMasterSynthesisAudioUrl;
    }

    public void setLiveMasterSynthesisAudioUrl(String liveMasterSynthesisAudioUrl) {
        this.liveMasterSynthesisAudioUrl = liveMasterSynthesisAudioUrl;
    }

    @Basic
    @Column(name = "live_master_is_online")
    public int getLiveMasterIsOnline() {
        return liveMasterIsOnline;
    }

    public void setLiveMasterIsOnline(int liveMasterIsOnline) {
        this.liveMasterIsOnline = liveMasterIsOnline;
    }

    @Basic
    @Column(name = "live_master_is_relation")
    public int getLiveMasterIsRelation() {
        return liveMasterIsRelation;
    }

    public void setLiveMasterIsRelation(int liveMasterIsRelation) {
        this.liveMasterIsRelation = liveMasterIsRelation;
    }

    @Basic
    @Column(name = "live_master_is_push")
    public int getLiveMasterIsPush() {
        return liveMasterIsPush;
    }

    public void setLiveMasterIsPush(int liveMasterIsPush) {
        this.liveMasterIsPush = liveMasterIsPush;
    }

    @Basic
    @Column(name = "live_master_starttime")
    public String getLiveMasterStarttime() {
        return liveMasterStarttime;
    }

    public void setLiveMasterStarttime(String liveMasterStarttime) {
        this.liveMasterStarttime = liveMasterStarttime;
    }

    @Basic
    @Column(name = "live_master_endtime")
    public String getLiveMasterEndtime() {
        return liveMasterEndtime;
    }

    public void setLiveMasterEndtime(String liveMasterEndtime) {
        this.liveMasterEndtime = liveMasterEndtime;
    }

    @Basic
    @Column(name = "live_master_create_time")
    public Timestamp getLiveMasterCreateTime() {
        return liveMasterCreateTime;
    }

    public void setLiveMasterCreateTime(Timestamp liveMasterCreateTime) {
        this.liveMasterCreateTime = liveMasterCreateTime;
    }

    @Basic
    @Column(name = "live_master_create_id")
    public String getLiveMasterCreateId() {
        return liveMasterCreateId;
    }

    public void setLiveMasterCreateId(String liveMasterCreateId) {
        this.liveMasterCreateId = liveMasterCreateId;
    }

    @Basic
    @Column(name = "live_master_update_time")
    public Timestamp getLiveMasterUpdateTime() {
        return liveMasterUpdateTime;
    }

    public void setLiveMasterUpdateTime(Timestamp liveMasterUpdateTime) {
        this.liveMasterUpdateTime = liveMasterUpdateTime;
    }

    @Basic
    @Column(name = "live_master_update_id")
    public String getLiveMasterUpdateId() {
        return liveMasterUpdateId;
    }

    public void setLiveMasterUpdateId(String liveMasterUpdateId) {
        this.liveMasterUpdateId = liveMasterUpdateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveMaster that = (LiveMaster) o;
        return liveMasterStatus == that.liveMasterStatus &&
                liveMasterLike == that.liveMasterLike &&
                liveMasterIsSynthesisAudio == that.liveMasterIsSynthesisAudio &&
                liveMasterIsOnline == that.liveMasterIsOnline &&
                liveMasterIsRelation == that.liveMasterIsRelation &&
                liveMasterIsPush == that.liveMasterIsPush &&
                Objects.equals(id, that.id) &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(liveMasterName, that.liveMasterName) &&
                Objects.equals(liveMasterDescribe, that.liveMasterDescribe) &&
                Objects.equals(liveMasterSynthesisAudioUrl, that.liveMasterSynthesisAudioUrl) &&
                Objects.equals(liveMasterStarttime, that.liveMasterStarttime) &&
                Objects.equals(liveMasterEndtime, that.liveMasterEndtime) &&
                Objects.equals(liveMasterCreateTime, that.liveMasterCreateTime) &&
                Objects.equals(liveMasterCreateId, that.liveMasterCreateId) &&
                Objects.equals(liveMasterUpdateTime, that.liveMasterUpdateTime) &&
                Objects.equals(liveMasterUpdateId, that.liveMasterUpdateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, liveMasterName, liveMasterDescribe, liveMasterStatus, liveMasterLike, liveMasterIsSynthesisAudio, liveMasterSynthesisAudioUrl, liveMasterIsOnline, liveMasterIsRelation, liveMasterIsPush, liveMasterStarttime, liveMasterEndtime, liveMasterCreateTime, liveMasterCreateId, liveMasterUpdateTime, liveMasterUpdateId);
    }
}
