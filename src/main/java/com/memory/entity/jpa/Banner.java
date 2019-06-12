package com.memory.entity.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName Banner
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/11 18:11
 */
@Entity
public class Banner {
    private String id;
    private String typeTable;
    private String typeTableId;
    private String bannerName;
    private String bannerLogo;
    private int bannerSort;
    private int bannerOnline;
    private Date bannerCreateTime;
    private String bannerCreateId;
    private Date bannerUpdateTime;
    private String bannerUpdateId;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type_table")
    public String getTypeTable() {
        return typeTable;
    }

    public void setTypeTable(String typeTable) {
        this.typeTable = typeTable;
    }

    @Basic
    @Column(name = "type_table_id")
    public String getTypeTableId() {
        return typeTableId;
    }

    public void setTypeTableId(String typeTableId) {
        this.typeTableId = typeTableId;
    }

    @Basic
    @Column(name = "banner_name")
    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    @Basic
    @Column(name = "banner_logo")
    public String getBannerLogo() {
        return bannerLogo;
    }

    public void setBannerLogo(String bannerLogo) {
        this.bannerLogo = bannerLogo;
    }

    @Basic
    @Column(name = "banner_sort")
    public int getBannerSort() {
        return bannerSort;
    }

    public void setBannerSort(int bannerSort) {
        this.bannerSort = bannerSort;
    }

    @Basic
    @Column(name = "banner_online")
    public int getBannerOnline() {
        return bannerOnline;
    }

    public void setBannerOnline(int bannerOnline) {
        this.bannerOnline = bannerOnline;
    }

    @Basic
    @Column(name = "banner_create_time")
    public Date getBannerCreateTime() {
        return bannerCreateTime;
    }

    public void setBannerCreateTime(Date bannerCreateTime) {
        this.bannerCreateTime = bannerCreateTime;
    }

    @Basic
    @Column(name = "banner_create_id")
    public String getBannerCreateId() {
        return bannerCreateId;
    }

    public void setBannerCreateId(String bannerCreateId) {
        this.bannerCreateId = bannerCreateId;
    }

    @Basic
    @Column(name = "banner_update_time")
    public Date getBannerUpdateTime() {
        return bannerUpdateTime;
    }

    public void setBannerUpdateTime(Date bannerUpdateTime) {
        this.bannerUpdateTime = bannerUpdateTime;
    }

    @Basic
    @Column(name = "banner_update_id")
    public String getBannerUpdateId() {
        return bannerUpdateId;
    }

    public void setBannerUpdateId(String bannerUpdateId) {
        this.bannerUpdateId = bannerUpdateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banner banner = (Banner) o;
        return bannerSort == banner.bannerSort &&
                bannerOnline == banner.bannerOnline &&
                Objects.equals(id, banner.id) &&
                Objects.equals(typeTable, banner.typeTable) &&
                Objects.equals(typeTableId, banner.typeTableId) &&
                Objects.equals(bannerName, banner.bannerName) &&
                Objects.equals(bannerLogo, banner.bannerLogo) &&
                Objects.equals(bannerCreateTime, banner.bannerCreateTime) &&
                Objects.equals(bannerCreateId, banner.bannerCreateId) &&
                Objects.equals(bannerUpdateTime, banner.bannerUpdateTime) &&
                Objects.equals(bannerUpdateId, banner.bannerUpdateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeTable, typeTableId, bannerName, bannerLogo, bannerSort, bannerOnline, bannerCreateTime, bannerCreateId, bannerUpdateTime, bannerUpdateId);
    }
}
