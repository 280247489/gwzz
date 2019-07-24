package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName LiveMemoryLoad
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/24 9:56
 */
@Entity
@Table(name = "live_memory_load", schema = "gwzz_db", catalog = "")
public class LiveMemoryLoad {
    private String id;
    private String content;
    private String operator;
    private int loadStatus;
    private Date createTime;
    private Date updateTime;
    private String liveRedisKey;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "operator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "load_status")
    public int getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(int loadStatus) {
        this.loadStatus = loadStatus;
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
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "live_redis_key")
    public String getLiveRedisKey() {
        return liveRedisKey;
    }

    public void setLiveRedisKey(String liveRedisKey) {
        this.liveRedisKey = liveRedisKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveMemoryLoad that = (LiveMemoryLoad) o;
        return loadStatus == that.loadStatus &&
                Objects.equals(id, that.id) &&
                Objects.equals(content, that.content) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(liveRedisKey, that.liveRedisKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, operator, loadStatus, createTime, updateTime, liveRedisKey);
    }
}
