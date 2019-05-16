package com.memory.excel.entity;

import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/11 10:33
 */

public class ExcelResult {

    private Boolean isSuccess;

    private List<?> list;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ExcelResult{" +
                "isSuccess=" + isSuccess +
                ", list=" + list +
                '}';
    }
}
