package com.memory.entity.bean;

/**
 * @author INS6+
 * @date 2019/6/10 17:40
 */

public class LiveMaster {

    private String id;
    private String liveMasterName;
    private String liveMasterDescribe;
    private String operatorId;
    private String startTime;
    private String endTime;

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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public LiveMaster(String id, String liveMasterName, String liveMasterDescribe, String operatorId, String startTime, String endTime) {
        this.id = id;
        this.liveMasterName = liveMasterName;
        this.liveMasterDescribe = liveMasterDescribe;
        this.operatorId = operatorId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LiveMaster() {
    }

    public LiveMaster(String id, String liveMasterName) {
        this.id = id;
        this.liveMasterName = liveMasterName;
    }

    @Override
    public String toString() {
        return "LiveMaster{" +
                "id='" + id + '\'' +
                ", liveMasterName='" + liveMasterName + '\'' +
                ", liveMasterDescribe='" + liveMasterDescribe + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
