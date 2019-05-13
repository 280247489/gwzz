package com.memory.entity.jpa;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author INS6+
 * @date 2019/5/7 10:13
 */

@Entity
@Table(name = "user", schema = "gwzz_db", catalog = "")
public class UserInfos {
    private String id;
    private String password;
    private String userUnionId;
    private String userOpenId;
    private String userTel;
    private String userName;
    private String userLogo;
    private String userSex;
    private String userBirthday;
    private String userProvince;
    private String userCity;
    private String userArea;
    private String userAddress;
    private Timestamp userCreateTime;
    private int userForbidden;
    private int userNologin;
    private int userCancel;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "user_union_id")
    public String getUserUnionId() {
        return userUnionId;
    }

    public void setUserUnionId(String userUnionId) {
        this.userUnionId = userUnionId;
    }

    @Basic
    @Column(name = "user_open_id")
    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    @Basic
    @Column(name = "user_tel")
    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_logo")
    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }

    @Basic
    @Column(name = "user_sex")
    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    @Basic
    @Column(name = "user_birthday")
    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    @Basic
    @Column(name = "user_province")
    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    @Basic
    @Column(name = "user_city")
    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    @Basic
    @Column(name = "user_area")
    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea;
    }

    @Basic
    @Column(name = "user_address")
    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Basic
    @Column(name = "user_create_time")
    public Timestamp getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Timestamp userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    @Basic
    @Column(name = "user_forbidden")
    public int getUserForbidden() {
        return userForbidden;
    }

    public void setUserForbidden(int userForbidden) {
        this.userForbidden = userForbidden;
    }

    @Basic
    @Column(name = "user_nologin")
    public int getUserNologin() {
        return userNologin;
    }

    public void setUserNologin(int userNologin) {
        this.userNologin = userNologin;
    }

    @Basic
    @Column(name = "user_cancel")
    public int getUserCancel() {
        return userCancel;
    }

    public void setUserCancel(int userCancel) {
        this.userCancel = userCancel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfos that = (UserInfos) o;
        return userForbidden == that.userForbidden &&
                userNologin == that.userNologin &&
                userCancel == that.userCancel &&
                Objects.equals(id, that.id) &&
                Objects.equals(password, that.password) &&
                Objects.equals(userUnionId, that.userUnionId) &&
                Objects.equals(userOpenId, that.userOpenId) &&
                Objects.equals(userTel, that.userTel) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userLogo, that.userLogo) &&
                Objects.equals(userSex, that.userSex) &&
                Objects.equals(userBirthday, that.userBirthday) &&
                Objects.equals(userProvince, that.userProvince) &&
                Objects.equals(userCity, that.userCity) &&
                Objects.equals(userArea, that.userArea) &&
                Objects.equals(userAddress, that.userAddress) &&
                Objects.equals(userCreateTime, that.userCreateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, userUnionId, userOpenId, userTel, userName, userLogo, userSex, userBirthday, userProvince, userCity, userArea, userAddress, userCreateTime, userForbidden, userNologin, userCancel);
    }
}
