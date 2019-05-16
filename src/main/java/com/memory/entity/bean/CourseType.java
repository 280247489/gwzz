package com.memory.entity.bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/15 10:11
 */

public class CourseType {
    private String id;
    private String typeName;
    private Date typeCreateTime;
    private String img;
    private int sum;
    private int isUse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getTypeCreateTime() {
        return typeCreateTime;
    }

    public void setTypeCreateTime(Date typeCreateTime) {
        this.typeCreateTime = typeCreateTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public CourseType(String id, String typeName, Date typeCreateTime, String img, int sum, int isUse) {
        this.id = id;
        this.typeName = typeName;
        this.typeCreateTime = typeCreateTime;
        this.img = img;
        this.sum = sum;
        this.isUse = isUse;
    }
}
