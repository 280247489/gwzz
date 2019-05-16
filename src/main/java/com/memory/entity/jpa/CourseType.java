package com.memory.entity.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/15 10:11
 */

@Entity
@Table(name = "course_type", schema = "gwzz_db", catalog = "")
public class CourseType {
    private String id;
    private String typeName;
    private Date typeCreateTime;
    private String img;
    private int sum;
    private int isUse;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "type_create_time")
    public Date getTypeCreateTime() {
        return typeCreateTime;
    }

    public void setTypeCreateTime(Date typeCreateTime) {
        this.typeCreateTime = typeCreateTime;
    }

    @Basic
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Basic
    @Column(name = "sum")
    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Basic
    @Column(name = "is_use")
    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseType that = (CourseType) o;
        return sum == that.sum &&
                isUse == that.isUse &&
                Objects.equals(id, that.id) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(typeCreateTime, that.typeCreateTime) &&
                Objects.equals(img, that.img);
    }

    public  CourseType(){};

    public CourseType(String id, String typeName, Date typeCreateTime, String img, int sum, int isUse) {
        this.id = id;
        this.typeName = typeName;
        this.typeCreateTime = typeCreateTime;
        this.img = img;
        this.sum = sum;
        this.isUse = isUse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeName, typeCreateTime, img, sum, isUse);
    }
}