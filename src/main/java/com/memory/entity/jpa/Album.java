package com.memory.entity.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/6/13 17:48
 */

@Entity
public class Album {
    private String id;
    private String albumName;
    private String albumLogo;
    private int albumIsOnline;
    private int albumCourseSum;
    private int albumTotalView;
    private int albumIsEnd;
    private int albumIsCharge;
    private double albumChargePrice;
    private Date albumCreateTime;
    private String albumCreateId;
    private Date albumUpdateTime;
    private String albumUpdateId;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "album_name")
    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Basic
    @Column(name = "album_logo")
    public String getAlbumLogo() {
        return albumLogo;
    }

    public void setAlbumLogo(String albumLogo) {
        this.albumLogo = albumLogo;
    }

    @Basic
    @Column(name = "album_is_online")
    public int getAlbumIsOnline() {
        return albumIsOnline;
    }

    public void setAlbumIsOnline(int albumIsOnline) {
        this.albumIsOnline = albumIsOnline;
    }

    @Basic
    @Column(name = "album_course_sum")
    public int getAlbumCourseSum() {
        return albumCourseSum;
    }

    public void setAlbumCourseSum(int albumCourseSum) {
        this.albumCourseSum = albumCourseSum;
    }

    @Basic
    @Column(name = "album_total_view")
    public int getAlbumTotalView() {
        return albumTotalView;
    }

    public void setAlbumTotalView(int albumTotalView) {
        this.albumTotalView = albumTotalView;
    }

    @Basic
    @Column(name = "album_is_end")
    public int getAlbumIsEnd() {
        return albumIsEnd;
    }

    public void setAlbumIsEnd(int albumIsEnd) {
        this.albumIsEnd = albumIsEnd;
    }

    @Basic
    @Column(name = "album_is_charge")
    public int getAlbumIsCharge() {
        return albumIsCharge;
    }

    public void setAlbumIsCharge(int albumIsCharge) {
        this.albumIsCharge = albumIsCharge;
    }

    @Basic
    @Column(name = "album_charge_price")
    public double getAlbumChargePrice() {
        return albumChargePrice;
    }

    public void setAlbumChargePrice(double albumChargePrice) {
        this.albumChargePrice = albumChargePrice;
    }

    @Basic
    @Column(name = "album_create_time")
    public Date getAlbumCreateTime() {
        return albumCreateTime;
    }

    public void setAlbumCreateTime(Date albumCreateTime) {
        this.albumCreateTime = albumCreateTime;
    }

    @Basic
    @Column(name = "album_create_id")
    public String getAlbumCreateId() {
        return albumCreateId;
    }

    public void setAlbumCreateId(String albumCreateId) {
        this.albumCreateId = albumCreateId;
    }

    @Basic
    @Column(name = "album_update_time")
    public Date getAlbumUpdateTime() {
        return albumUpdateTime;
    }

    public void setAlbumUpdateTime(Date albumUpdateTime) {
        this.albumUpdateTime = albumUpdateTime;
    }

    @Basic
    @Column(name = "album_update_id")
    public String getAlbumUpdateId() {
        return albumUpdateId;
    }

    public void setAlbumUpdateId(String albumUpdateId) {
        this.albumUpdateId = albumUpdateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return albumIsOnline == album.albumIsOnline &&
                albumCourseSum == album.albumCourseSum &&
                albumTotalView == album.albumTotalView &&
                albumIsEnd == album.albumIsEnd &&
                albumIsCharge == album.albumIsCharge &&
                Double.compare(album.albumChargePrice, albumChargePrice) == 0 &&
                Objects.equals(id, album.id) &&
                Objects.equals(albumName, album.albumName) &&
                Objects.equals(albumLogo, album.albumLogo) &&
                Objects.equals(albumCreateTime, album.albumCreateTime) &&
                Objects.equals(albumCreateId, album.albumCreateId) &&
                Objects.equals(albumUpdateTime, album.albumUpdateTime) &&
                Objects.equals(albumUpdateId, album.albumUpdateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, albumName, albumLogo, albumIsOnline, albumCourseSum, albumTotalView, albumIsEnd, albumIsCharge, albumChargePrice, albumCreateTime, albumCreateId, albumUpdateTime, albumUpdateId);
    }
}