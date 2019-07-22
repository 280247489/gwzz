package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/7/22 13:09
 */

@Entity
@Table(name = "hot_search", schema = "gwzz_db", catalog = "")
public class HotSearch {
    private String id;
    private String searchType;
    private int status;
    private int sort;
    private String keyWord;
    private String operatorId;
    private String operatorName;
    private Date lastUpdateTime;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "search_type")
    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "key_word")
    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Basic
    @Column(name = "operator_id")
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Basic
    @Column(name = "operator_name")
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Basic
    @Column(name = "last_update_time")
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotSearch hotSearch = (HotSearch) o;
        return status == hotSearch.status &&
                sort == hotSearch.sort &&
                Objects.equals(id, hotSearch.id) &&
                Objects.equals(searchType, hotSearch.searchType) &&
                Objects.equals(keyWord, hotSearch.keyWord) &&
                Objects.equals(operatorId, hotSearch.operatorId) &&
                Objects.equals(operatorName, hotSearch.operatorName) &&
                Objects.equals(lastUpdateTime, hotSearch.lastUpdateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, searchType, status, sort, keyWord, operatorId, operatorName, lastUpdateTime);
    }
}
