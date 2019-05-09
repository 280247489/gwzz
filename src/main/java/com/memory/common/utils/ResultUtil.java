package com.memory.common.utils;
/**
 * @Auther: cui.Memory
 * @Date: 2018/11/2
 * @Description: Http请求，返回统一格式基类
 */
public class ResultUtil {

    public static Result success(Integer code, String msg, Object object) {
        Result result = new Result();
        result.setState("success");
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    public static Result success() {
        return ResultUtil.success(SUCCESS, "success", null);
    }

    public static Result error(String msg, Object object) {
        Result result = new Result();
        result.setState("error");
        result.setCode(ERROR);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;

    public static final int ERROR_REQ = 100;
    public static final int ERROR_KEY = 101;
    public static final int ERROR_SIGN = 102;
    public static final int ERROR_PARAM = 103;
    public static final int ERROR_SERVER = 200;
    public static final int ERROR_UNUSED = 201;
    public static final int ERROR_REBOOT = 202;
    public static final int BUSINESS_FAILED = 300;
    public static final int REQUET_PARAMS_ERROR = 400;
}
