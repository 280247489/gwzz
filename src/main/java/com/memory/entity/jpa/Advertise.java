package com.memory.entity.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName Advertise
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/31 9:07
 */
@Entity
public class Advertise {
    private String id;
    private String advertiseName;
    private String advertiseImgUrl;
    private int advertiseType;
    private String advertiseH5Url;
    private int advertiseOnline;
    private Date advertiseCreateTime;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "advertise_name")
    public String getAdvertiseName() {
        return advertiseName;
    }

    public void setAdvertiseName(String advertiseName) {
        this.advertiseName = advertiseName;
    }

    @Basic
    @Column(name = "advertise_img_url")
    public String getAdvertiseImgUrl() {
        return advertiseImgUrl;
    }

    public void setAdvertiseImgUrl(String advertiseImgUrl) {
        this.advertiseImgUrl = advertiseImgUrl;
    }

    @Basic
    @Column(name = "advertise_type")
    public int getAdvertiseType() {
        return advertiseType;
    }

    public void setAdvertiseType(int advertiseType) {
        this.advertiseType = advertiseType;
    }

    @Basic
    @Column(name = "advertise_h5_url")
    public String getAdvertiseH5Url() {
        return advertiseH5Url;
    }

    public void setAdvertiseH5Url(String advertiseH5Url) {
        this.advertiseH5Url = advertiseH5Url;
    }

    @Basic
    @Column(name = "advertise_online")
    public int getAdvertiseOnline() {
        return advertiseOnline;
    }

    public void setAdvertiseOnline(int advertiseOnline) {
        this.advertiseOnline = advertiseOnline;
    }

    @Basic
    @Column(name = "advertise_create_time")
    public Date getAdvertiseCreateTime() {
        return advertiseCreateTime;
    }

    public void setAdvertiseCreateTime(Date advertiseCreateTime) {
        this.advertiseCreateTime = advertiseCreateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertise advertise = (Advertise) o;
        return advertiseType == advertise.advertiseType &&
                advertiseOnline == advertise.advertiseOnline &&
                Objects.equals(id, advertise.id) &&
                Objects.equals(advertiseName, advertise.advertiseName) &&
                Objects.equals(advertiseImgUrl, advertise.advertiseImgUrl) &&
                Objects.equals(advertiseH5Url, advertise.advertiseH5Url) &&
                Objects.equals(advertiseCreateTime, advertise.advertiseCreateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, advertiseName, advertiseImgUrl, advertiseType, advertiseH5Url, advertiseOnline, advertiseCreateTime);
    }
}
