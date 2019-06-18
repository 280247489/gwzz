package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName LiveSlave
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/13 16:56
 */
@Entity
@Table(name = "live_slave", schema = "gwzz_db", catalog = "")
public class LiveSlave {
    private String id;
    private String liveMasterId;
    private String liveSlaveNickname;
    private String liveSlaveLogo;
    private int liveSlaveType;
    private String liveSlaveWords;
    private String liveSlaveImgurl;
    private String liveSlaveAudio;
    private int liveSlaveAudioTime;
    private int liveSlaveSort;
    private Date liveSlaveCreateTime;
    private String liveSlaveCreateId;
    private Date liveSlaveUpdateTime;
    private String liveSlaveUpdateId;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "live_master_id")
    public String getLiveMasterId() {
        return liveMasterId;
    }

    public void setLiveMasterId(String liveMasterId) {
        this.liveMasterId = liveMasterId;
    }

    @Basic
    @Column(name = "live_slave_nickname")
    public String getLiveSlaveNickname() {
        return liveSlaveNickname;
    }

    public void setLiveSlaveNickname(String liveSlaveNickname) {
        this.liveSlaveNickname = liveSlaveNickname;
    }

    @Basic
    @Column(name = "live_slave_logo")
    public String getLiveSlaveLogo() {
        return liveSlaveLogo;
    }

    public void setLiveSlaveLogo(String liveSlaveLogo) {
        this.liveSlaveLogo = liveSlaveLogo;
    }

    @Basic
    @Column(name = "live_slave_type")
    public int getLiveSlaveType() {
        return liveSlaveType;
    }

    public void setLiveSlaveType(int liveSlaveType) {
        this.liveSlaveType = liveSlaveType;
    }

    @Basic
    @Column(name = "live_slave_words")
    public String getLiveSlaveWords() {
        return liveSlaveWords;
    }

    public void setLiveSlaveWords(String liveSlaveWords) {
        this.liveSlaveWords = liveSlaveWords;
    }

    @Basic
    @Column(name = "live_slave_imgurl")
    public String getLiveSlaveImgurl() {
        return liveSlaveImgurl;
    }

    public void setLiveSlaveImgurl(String liveSlaveImgurl) {
        this.liveSlaveImgurl = liveSlaveImgurl;
    }

    @Basic
    @Column(name = "live_slave_audio")
    public String getLiveSlaveAudio() {
        return liveSlaveAudio;
    }

    public void setLiveSlaveAudio(String liveSlaveAudio) {
        this.liveSlaveAudio = liveSlaveAudio;
    }

    @Basic
    @Column(name = "live_slave_audio_time")
    public int getLiveSlaveAudioTime() {
        return liveSlaveAudioTime;
    }

    public void setLiveSlaveAudioTime(int liveSlaveAudioTime) {
        this.liveSlaveAudioTime = liveSlaveAudioTime;
    }

    @Basic
    @Column(name = "live_slave_sort")
    public int getLiveSlaveSort() {
        return liveSlaveSort;
    }

    public void setLiveSlaveSort(int liveSlaveSort) {
        this.liveSlaveSort = liveSlaveSort;
    }

    @Basic
    @Column(name = "live_slave_create_time")
    public Date getLiveSlaveCreateTime() {
        return liveSlaveCreateTime;
    }

    public void setLiveSlaveCreateTime(Date liveSlaveCreateTime) {
        this.liveSlaveCreateTime = liveSlaveCreateTime;
    }

    @Basic
    @Column(name = "live_slave_create_id")
    public String getLiveSlaveCreateId() {
        return liveSlaveCreateId;
    }

    public void setLiveSlaveCreateId(String liveSlaveCreateId) {
        this.liveSlaveCreateId = liveSlaveCreateId;
    }

    @Basic
    @Column(name = "live_slave_update_time")
    public Date getLiveSlaveUpdateTime() {
        return liveSlaveUpdateTime;
    }

    public void setLiveSlaveUpdateTime(Date liveSlaveUpdateTime) {
        this.liveSlaveUpdateTime = liveSlaveUpdateTime;
    }

    @Basic
    @Column(name = "live_slave_update_id")
    public String getLiveSlaveUpdateId() {
        return liveSlaveUpdateId;
    }

    public void setLiveSlaveUpdateId(String liveSlaveUpdateId) {
        this.liveSlaveUpdateId = liveSlaveUpdateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveSlave liveSlave = (LiveSlave) o;
        return liveSlaveType == liveSlave.liveSlaveType &&
                liveSlaveAudioTime == liveSlave.liveSlaveAudioTime &&
                liveSlaveSort == liveSlave.liveSlaveSort &&
                Objects.equals(id, liveSlave.id) &&
                Objects.equals(liveMasterId, liveSlave.liveMasterId) &&
                Objects.equals(liveSlaveNickname, liveSlave.liveSlaveNickname) &&
                Objects.equals(liveSlaveLogo, liveSlave.liveSlaveLogo) &&
                Objects.equals(liveSlaveWords, liveSlave.liveSlaveWords) &&
                Objects.equals(liveSlaveImgurl, liveSlave.liveSlaveImgurl) &&
                Objects.equals(liveSlaveAudio, liveSlave.liveSlaveAudio) &&
                Objects.equals(liveSlaveCreateTime, liveSlave.liveSlaveCreateTime) &&
                Objects.equals(liveSlaveCreateId, liveSlave.liveSlaveCreateId) &&
                Objects.equals(liveSlaveUpdateTime, liveSlave.liveSlaveUpdateTime) &&
                Objects.equals(liveSlaveUpdateId, liveSlave.liveSlaveUpdateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, liveMasterId, liveSlaveNickname, liveSlaveLogo, liveSlaveType, liveSlaveWords, liveSlaveImgurl, liveSlaveAudio, liveSlaveAudioTime, liveSlaveSort, liveSlaveCreateTime, liveSlaveCreateId, liveSlaveUpdateTime, liveSlaveUpdateId);
    }
}
