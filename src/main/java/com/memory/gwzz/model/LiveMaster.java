package com.memory.gwzz.model;

import java.util.Objects;

/**
 * @ClassName LiveMaster
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/14 15:49
 */
public class LiveMaster {
    private String id;
    private String liveMasterName;
    private String liveMasterDescribe;
    private int liveMasterStatus;
    private int liveMasterIsOnline;
    private String liveMasterStarttime;
    private int liveNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiveMasterName() {
        return liveMasterName;
    }

    public void setLiveMasterName(String liveMasterName) {
        this.liveMasterName = liveMasterName;
    }

    public String getLiveMasterDescribe() {
        return liveMasterDescribe;
    }

    public void setLiveMasterDescribe(String liveMasterDescribe) {
        this.liveMasterDescribe = liveMasterDescribe;
    }

    public int getLiveMasterStatus() {
        return liveMasterStatus;
    }

    public void setLiveMasterStatus(int liveMasterStatus) {
        this.liveMasterStatus = liveMasterStatus;
    }

    public int getLiveMasterIsOnline() {
        return liveMasterIsOnline;
    }

    public void setLiveMasterIsOnline(int liveMasterIsOnline) {
        this.liveMasterIsOnline = liveMasterIsOnline;
    }

    public String getLiveMasterStarttime() {
        return liveMasterStarttime;
    }

    public void setLiveMasterStarttime(String liveMasterStarttime) {
        this.liveMasterStarttime = liveMasterStarttime;
    }

    public int getLiveNumber() { return liveNumber;}

    public void setLiveNumber(int liveNumber) {this.liveNumber = liveNumber; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveMaster that = (LiveMaster) o;
        return liveMasterStatus == that.liveMasterStatus &&
                liveMasterIsOnline == that.liveMasterIsOnline &&
                liveNumber == that.liveNumber &&
                Objects.equals(id, that.id) &&
                Objects.equals(liveMasterName, that.liveMasterName) &&
                Objects.equals(liveMasterDescribe, that.liveMasterDescribe) &&
                Objects.equals(liveMasterStarttime, that.liveMasterStarttime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, liveMasterName, liveMasterDescribe, liveMasterStatus, liveMasterIsOnline, liveMasterStarttime, liveNumber);
    }

    public LiveMaster() {
    }

    public LiveMaster(String id, String liveMasterName, String liveMasterDescribe, int liveMasterStatus, int liveMasterIsOnline, String liveMasterStarttime, int liveNumber) {
        this.id = id;
        this.liveMasterName = liveMasterName;
        this.liveMasterDescribe = liveMasterDescribe;
        this.liveMasterStatus = liveMasterStatus;
        this.liveMasterIsOnline = liveMasterIsOnline;
        this.liveMasterStarttime = liveMasterStarttime;
        this.liveNumber = liveNumber;
    }
}
