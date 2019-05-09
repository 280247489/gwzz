package com.memory.cms.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/7 15:52
 */

@Entity
@Table(name = "sys_admin", schema = "gwzz_db", catalog = "")
public class SysAdmin {
    private String id;
    private String loginname;
    private String password;
    private String logo;
    private String name;
    private String sex;
    private Date birthday;
    private String tel;
    private String email;
    private String address;
    private Timestamp createTime;
    private int nologin;

    public SysAdmin(){

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
    @Column(name = "loginname")
    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "nologin")
    public int getNologin() {
        return nologin;
    }

    public void setNologin(int nologin) {
        this.nologin = nologin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysAdmin sysAdmin = (SysAdmin) o;
        return nologin == sysAdmin.nologin &&
                Objects.equals(id, sysAdmin.id) &&
                Objects.equals(loginname, sysAdmin.loginname) &&
                Objects.equals(password, sysAdmin.password) &&
                Objects.equals(logo, sysAdmin.logo) &&
                Objects.equals(name, sysAdmin.name) &&
                Objects.equals(sex, sysAdmin.sex) &&
                Objects.equals(birthday, sysAdmin.birthday) &&
                Objects.equals(tel, sysAdmin.tel) &&
                Objects.equals(email, sysAdmin.email) &&
                Objects.equals(address, sysAdmin.address) &&
                Objects.equals(createTime, sysAdmin.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginname, password, logo, name, sex, birthday, tel, email, address, createTime, nologin);
    }


}
