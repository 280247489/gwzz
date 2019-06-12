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
 * @Date 2019/6/11 16:22
 */
@Entity
public class Advertise {
    private String id;
    private String advertiseName;
    private String advertiseLogo;
    private int advertiseType;
    private String advertiseH5Type;
    private String advertiseH5Url;
    private int advertiseOnline;
    private Date advertiseCreateTime;
    private String advertiseCreateId;
    private Date advertiseUpdateTime;
    private String advertiseUpdateId;

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
    @Column(name = "advertise_logo")
    public String getAdvertiseLogo() {
        return advertiseLogo;
    }

    public void setAdvertiseLogo(String advertiseLogo) {
        this.advertiseLogo = advertiseLogo;
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
    @Column(name = "advertise_h5_type")
    public String getAdvertiseH5Type() {
        return advertiseH5Type;
    }

    public void setAdvertiseH5Type(String advertiseH5Type) {
        this.advertiseH5Type = advertiseH5Type;
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

    @Basic
    @Column(name = "advertise_create_id")
    public String getAdvertiseCreateId() {
        return advertiseCreateId;
    }

    public void setAdvertiseCreateId(String advertiseCreateId) {
        this.advertiseCreateId = advertiseCreateId;
    }

    @Basic
    @Column(name = "advertise_update_time")
    public Date getAdvertiseUpdateTime() {
        return advertiseUpdateTime;
    }

    public void setAdvertiseUpdateTime(Date advertiseUpdateTime) {
        this.advertiseUpdateTime = advertiseUpdateTime;
    }

    @Basic
    @Column(name = "advertise_update_id")
    public String getAdvertiseUpdateId() {
        return advertiseUpdateId;
    }

    public void setAdvertiseUpdateId(String advertiseUpdateId) {
        this.advertiseUpdateId = advertiseUpdateId;
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
                Objects.equals(advertiseLogo, advertise.advertiseLogo) &&
                Objects.equals(advertiseH5Type, advertise.advertiseH5Type) &&
                Objects.equals(advertiseH5Url, advertise.advertiseH5Url) &&
                Objects.equals(advertiseCreateTime, advertise.advertiseCreateTime) &&
                Objects.equals(advertiseCreateId, advertise.advertiseCreateId) &&
                Objects.equals(advertiseUpdateTime, advertise.advertiseUpdateTime) &&
                Objects.equals(advertiseUpdateId, advertise.advertiseUpdateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, advertiseName, advertiseLogo, advertiseType, advertiseH5Type, advertiseH5Url, advertiseOnline, advertiseCreateTime, advertiseCreateId, advertiseUpdateTime, advertiseUpdateId);
    }
}
