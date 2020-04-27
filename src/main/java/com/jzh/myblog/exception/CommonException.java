package com.jzh.myblog.exception;

/**
 * @author jzh
 * @version 1.0.0
 * @title CommonException
 * @date 2020/2/4 18:46
 * @description 通用异常
 */
public class CommonException extends RuntimeException{
    public CommonException(){
        super();
    }

    public CommonException(String message){
        super(message);
    }

    public CommonException(Throwable cause){
        super(cause);
    }

    public CommonException(String message, Throwable cause){
        super(message, cause);
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}