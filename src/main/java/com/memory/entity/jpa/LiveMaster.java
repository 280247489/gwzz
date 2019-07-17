package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/7/17 10:42
 */

@Entity
@Table(name = "live_master", schema = "gwzz_db", catalog = "")
public class LiveMaster {
    private String id;
    private String courseId;
    private String liveMasterName;
    private String liveMasterDescribe;
    private int liveMasterStatus;
    private int liveMasterShare;
    private int liveMasterView;
    private int liveMasterLike;
    private int liveMasterIsSynthesisAudio;
    private String liveMasterSynthesisAudioUrl;
    private int liveMasterIsOnline;
    private int liveMasterIsRelation;
    private int liveMasterIsPush;
    private String liveMasterStarttime;
    private String liveMasterEndtime;
    private Date liveMasterCreateTime;
    private String liveMasterCreateId;
    private Date liveMasterUpdateTime;
    private String liveMasterUpdateId;
    private int liveNumber;

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
    @Column(name = "live_master_share")
    public int getLiveMasterShare() {
        return liveMasterShare;
    }

    public void setLiveMasterShare(int liveMasterShare) {
        this.liveMasterShare = liveMasterShare;
    }

    @Basic
    @Column(name = "live_master_view")
    public int getLiveMasterView() {
        return liveMasterView;
    }

    public void setLiveMasterView(int liveMasterView) {
        this.liveMasterView = liveMasterView;
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
    public Date getLiveMasterCreateTime() {
        return liveMasterCreateTime;
    }

    public void setLiveMasterCreateTime(Date liveMasterCreateTime) {
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
    public Date getLiveMasterUpdateTime() {
        return liveMasterUpdateTime;
    }

    public void setLiveMasterUpdateTime(Date liveMasterUpdateTime) {
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

    @Basic
    @Column(name = "live_number")
    public int getLiveNumber() {
        return liveNumber;
    }

    public void setLiveNumber(int liveNumber) {
        this.liveNumber = liveNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveMaster master = (LiveMaster) o;
        return liveMasterStatus == master.liveMasterStatus &&
                liveMasterShare == master.liveMasterShare &&
                liveMasterView == master.liveMasterView &&
                liveMasterLike == master.liveMasterLike &&
                liveMasterIsSynthesisAudio == master.liveMasterIsSynthesisAudio &&
                liveMasterIsOnline == master.liveMasterIsOnline &&
                liveMasterIsRelation == master.liveMasterIsRelation &&
                liveMasterIsPush == master.liveMasterIsPush &&
                Objects.equals(id, master.id) &&
                Objects.equals(courseId, master.courseId) &&
                Objects.equals(liveMasterName, master.liveMasterName) &&
                Objects.equals(liveMasterDescribe, master.liveMasterDescribe) &&
                Objects.equals(liveMasterSynthesisAudioUrl, master.liveMasterSynthesisAudioUrl) &&
                Objects.equals(liveMasterStarttime, master.liveMasterStarttime) &&
                Objects.equals(liveMasterEndtime, master.liveMasterEndtime) &&
                Objects.equals(liveMasterCreateTime, master.liveMasterCreateTime) &&
                Objects.equals(liveMasterCreateId, master.liveMasterCreateId) &&
                Objects.equals(liveMasterUpdateTime, master.liveMasterUpdateTime) &&
                Objects.equals(liveMasterUpdateId, master.liveMasterUpdateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, liveMasterName, liveMasterDescribe, liveMasterStatus, liveMasterShare, liveMasterView, liveMasterLike, liveMasterIsSynthesisAudio, liveMasterSynthesisAudioUrl, liveMasterIsOnline, liveMasterIsRelation, liveMasterIsPush, liveMasterStarttime, liveMasterEndtime, liveMasterCreateTime, liveMasterCreateId, liveMasterUpdateTime, liveMasterUpdateId);
    }
}
