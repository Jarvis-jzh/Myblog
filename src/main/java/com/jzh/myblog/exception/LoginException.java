package com.jzh.myblog.exception;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/2 11:00
 * @description 登陆异常
 */
public class LoginException extends RuntimeException{

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
