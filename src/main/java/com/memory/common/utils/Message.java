package com.memory.common.utils;

/**
 * @Auther: cui.Memory
 * @Date: 2019/5/9 0009 9:43
 * @Description:
 */
public class Message {

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;

    private String state;
    private Integer recode;
    private String msg;
    private Object data;

    public static Message success(){
        Message message = new Message();
        message.state = "success";
        message.recode = SUCCESS;
        return message;
    }

    public static Message error(){
        Message message = new Message();
        message.state = "error";
        message.recode = ERROR;
        return message;
    }

    private Message() {

    }

    public String getState() {
        return state;
    }

    public Integer getRecode() {
        return recode;
    }

    public void setRecode(Integer recode) {
        this.recode = recode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
