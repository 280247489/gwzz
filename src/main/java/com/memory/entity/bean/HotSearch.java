package com.memory.entity.bean;

import java.util.Date;

/**
 * @author INS6+
 * @date 2019/7/22 13:26
 */

public class HotSearch {

    private String id;
    private String searchType;
    private int status;
    private int sort;
    private String keyWord;
    private String operatorId;
    private String operatorName;

    public HotSearch() {
    }

    public HotSearch(String id, String searchType, int status, int sort, String keyWord, String operatorId, String operatorName) {
        this.id = id;
        this.searchType = searchType;
        this.status = status;
        this.sort = sort;
        this.keyWord = keyWord;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }



}
