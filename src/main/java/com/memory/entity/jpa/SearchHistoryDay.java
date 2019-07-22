package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/7/22 13:09
 */

@Entity
@Table(name = "search_history_day", schema = "gwzz_db", catalog = "")
public class SearchHistoryDay {
    private String id;
    private String searchType;
    private String time;
    private String year;
    private String month;
    private String day;
    private String keyWord;
    private int sums;
    private Date createTime;
    private Date updateTime;

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
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "year")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Basic
    @Column(name = "month")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Basic
    @Column(name = "day")
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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
    @Column(name = "sums")
    public int getSums() {
        return sums;
    }

    public void setSums(int sum) {
        this.sums = sums;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHistoryDay that = (SearchHistoryDay) o;
        return sums == that.sums &&
                Objects.equals(id, that.id) &&
                Objects.equals(searchType, that.searchType) &&
                Objects.equals(time, that.time) &&
                Objects.equals(year, that.year) &&
                Objects.equals(month, that.month) &&
                Objects.equals(day, that.day) &&
                Objects.equals(keyWord, that.keyWord) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, searchType, time, year, month, day, keyWord, sums, createTime, updateTime);
    }
}
