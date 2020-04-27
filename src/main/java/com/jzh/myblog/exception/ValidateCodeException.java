package com.jzh.myblog.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/25 20:01
 * @description 验证码异常
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
