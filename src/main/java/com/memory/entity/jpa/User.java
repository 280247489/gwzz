package com.memory.entity.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName User
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/3 16:20
 */
@Entity
public class User {
    private String id;
    private String password;
    private String userUnionId;
    private String userOpenId;
    private String userTel;
    private String userNickName;
    private String userLogo;
    private String userName;
    private String userSex;
    private String userBirthday;
    private String userProvince;
    private String userCity;
    private String userArea;
    private String userAddress;
    private Date userCreateTime;
    private int userForbidden;
    private int userNologin;
    private int userCancel;

    public User() {
    }

    public User(String id, String password, String userUnionId, String userOpenId, String userTel, String userNickName,
                String userLogo, String userName, String userSex, String userBirthday, String userProvince,
                String userCity, String userArea, String userAddress, Date userCreateTime,
                int userForbidden, int userNologin, int userCancel) {
        this.id = id;
        this.password = password;
        this.userUnionId = userUnionId;
        this.userOpenId = userOpenId;
        this.userTel = userTel;
        this.userNickName = userNickName;
        this.userLogo = userLogo;
        this.userName = userName;
        this.userSex = userSex;
        this.userBirthday = userBirthday;
        this.userProvince = userProvince;
        this.userCity = userCity;
        this.userArea = userArea;
        this.userAddress = userAddress;
        this.userCreateTime = userCreateTime;
        this.userForbidden = userForbidden;
        this.userNologin = userNologin;
        this.userCancel = userCancel;
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
    @Column(name = "user_nick_name")
    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
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
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
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
        User user = (User) o;
        return userForbidden == user.userForbidden &&
                userNologin == user.userNologin &&
                userCancel == user.userCancel &&
                Objects.equals(id, user.id) &&
                Objects.equals(password, user.password) &&
                Objects.equals(userUnionId, user.userUnionId) &&
                Objects.equals(userOpenId, user.userOpenId) &&
                Objects.equals(userTel, user.userTel) &&
                Objects.equals(userNickName, user.userNickName) &&
                Objects.equals(userLogo, user.userLogo) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userSex, user.userSex) &&
                Objects.equals(userBirthday, user.userBirthday) &&
                Objects.equals(userProvince, user.userProvince) &&
                Objects.equals(userCity, user.userCity) &&
                Objects.equals(userArea, user.userArea) &&
                Objects.equals(userAddress, user.userAddress) &&
                Objects.equals(userCreateTime, user.userCreateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, userUnionId, userOpenId, userTel, userNickName, userLogo, userName, userSex, userBirthday, userProvince, userCity, userArea, userAddress, userCreateTime, userForbidden, userNologin, userCancel);
    }
}
