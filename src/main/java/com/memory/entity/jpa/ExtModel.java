package com.memory.entity.jpa;

import com.memory.entity.bean.Ext;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/11 14:46
 */

public class ExtModel {
    private List<Ext> extList;

    public List<Ext> getExtList() {
        return extList;
    }

    public void setExtList(List<Ext> extList) {
        this.extList = extList;
    }
}
