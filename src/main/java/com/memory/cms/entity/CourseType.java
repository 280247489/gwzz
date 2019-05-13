package com.memory.cms.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/10 10:04
 */

@Entity
@Table(name = "course_type", schema = "gwzz_db", catalog = "")
public class CourseType {
    private int id;
    private String typeName;
    private Timestamp typeCreateTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    public Timestamp getTypeCreateTime() {
        return typeCreateTime;
    }

    public void setTypeCreateTime(Timestamp typeCreateTime) {
        this.typeCreateTime = typeCreateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseType that = (CourseType) o;
        return id == that.id &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(typeCreateTime, that.typeCreateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeName, typeCreateTime);
    }
}
