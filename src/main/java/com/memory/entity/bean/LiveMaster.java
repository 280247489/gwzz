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


    @Override
    public String toString() {
        return "LiveMaster{" +
                "id='" + id + '\'' +
                ", liveMasterName='" + liveMasterName + '\'' +
                ", liveMasterDescribe='" + liveMasterDescribe + '\'' +
                ", operatorId='" + operatorId + '\'' +
                '}';
    }
}
