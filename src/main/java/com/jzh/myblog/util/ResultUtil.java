package com.jzh.myblog.util;

import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.response.Result;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/4 17:47
 * @description 返回统一格式封装工具
 */
public class ResultUtil<T> {

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(Boolean bool, CodeEnum err) {
        return bool ? success() : error(err);
    }

    public static <T> Result<T> success(Boolean bool, CodeEnum suc, CodeEnum err) {
        // return bool ? success(suc) : error(err);
        if (bool) {
            Result<T> result = new Result<>();
            result.setCode(suc.getCode());
            result.setMsg(suc.getMessage());
            result.setSuccess(true);
            return result;
        } else {
            return error(err);
        }
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(CodeEnum.SUCCESS_STATUS.getCode());
        result.setMsg(CodeEnum.SUCCESS_STATUS.getMessage());
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data, CodeEnum code) {
        Result<T> result = new Result<>();
        result.setCode(code.getCode());
        result.setMsg(code.getMessage());
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(CodeEnum code){
        Result<T> result = new Result<>();
        result.setCode(code.getCode());
        result.setMsg(code.getMessage());
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }
}
