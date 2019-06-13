package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName ShareUrlCmsRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 20:13
 */
@Entity
@Table(name = "share_url", schema = "gwzz_db", catalog = "")
public class ShareUrl {
    private String id;
    private String urlName;
    private String url;
    private Date createTime;
    private String createId;

    public ShareUrl() {
    }

    public ShareUrl(String id, String urlName, String url, Date createTime, String createId) {
        this.id = id;
        this.urlName = urlName;
        this.url = url;
        this.createTime = createTime;
        this.createId = createId;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url_name")
    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "create_id")
    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareUrl shareUrl = (ShareUrl) o;
        return Objects.equals(id, shareUrl.id) &&
                Objects.equals(urlName, shareUrl.urlName) &&
                Objects.equals(url, shareUrl.url) &&
                Objects.equals(createTime, shareUrl.createTime) &&
                Objects.equals(createId, shareUrl.createId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, urlName, url, createTime, createId);
    }
}
