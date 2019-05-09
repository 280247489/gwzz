package com.memory.common.utils;
/**
 * @Auther: cui.Memory
 * @Date: 2018/11/2
 * @Description: Http请求，返回载体类
 */
public class Result {
    private String state;
    private Integer code;
    private String msg;
    private Object data;

    public Result() {
    }

    public Result(String state, Integer code, String msg, Object data) {
        this.state = state;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
