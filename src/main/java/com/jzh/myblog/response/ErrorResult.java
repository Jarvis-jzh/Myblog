package com.jzh.myblog.response;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JSONType(orders = {"code", "success", "msg", "data","path"})
public class ErrorResult extends  Result {
    private String path;
    private String exception;

    public ErrorResult(Integer code, boolean success, String msg, Object data, String path, String exception) {
        super(code, success, msg, data);
        this.path = path;
        this.exception = exception;
    }

    public ErrorResult() {
    }
}
