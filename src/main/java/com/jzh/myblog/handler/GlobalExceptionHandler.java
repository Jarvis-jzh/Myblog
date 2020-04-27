package com.jzh.myblog.handler;

import com.jzh.myblog.constant.SiteConstant;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.exception.CommonException;
import com.jzh.myblog.exception.LoginException;
import com.jzh.myblog.response.ErrorResult;
import com.jzh.myblog.util.cookie.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/2 11:00
 * @description 全局异常处理器
 */
@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截通用异常
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(CommonException.class)
    public ErrorResult commonExceptionHandler(HttpServletRequest request, CommonException exception) {
        return getErrorInfo(CodeEnum.GET_SET_FAIL.getCode(), request, exception.getMessage(), exception);
    }

    /**
     * 拦截登录异常
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(LoginException.class)
    public ErrorResult loginExceptionHandler(HttpServletRequest request, LoginException exception) {
        return getErrorInfo(CodeEnum.LOGIN_FAIL.getCode(), request, CodeEnum.LOGIN_FAIL.getMessage(), exception);
    }

//    @ExceptionHandler(ValidateCodeException.class)
//    public ErrorResult validateCodeExceptionHandler(HttpServletRequest request, HttpServletResponse response, LoginException exception) {
//        setUsernameToCookie(request, response);
//        return getErrorInfo(CodeEnum.VALIDATE_CODE_FAIL.getCode(), request, CodeEnum.VALIDATE_CODE_FAIL.getMessage(), exception);
//    }

    /**
     * 拦截参数校验异常
     *
     * @param request
     * @param response
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ErrorResult constraintViolationException(HttpServletRequest request, HttpServletResponse response, BindException exception) {
        setUsernameToCookie(request, response);
        return getErrorInfo(CodeEnum.REQUEST_PARAMETER_ERROR.getCode(), request, getErrorMsg(exception.getBindingResult().getFieldErrors()), exception);
    }

    /**
     * 拦截认证异常
     *
     * @param request
     * @param response
     * @param exception
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResult authenticationException(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        setUsernameToCookie(request, response);
        return getErrorInfo(CodeEnum.USERNAME_OR_PASSWORD_WRONG.getCode(), request, CodeEnum.USERNAME_OR_PASSWORD_WRONG.getMessage(), exception);
    }

    /**
     * 拦截所有异常
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ErrorResult ExceptionHandler(HttpServletRequest request, Exception exception) {
        return getErrorInfo(CodeEnum.FAIL.getCode(), request, CodeEnum.FAIL.getMessage(), exception);
    }

    private ErrorResult getErrorInfo(Integer code, HttpServletRequest request, String msg, Exception ex) {
        log.error(ex.getMessage(), ex);
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(code);
        errorResult.setMsg(msg);
        errorResult.setSuccess(false);
        errorResult.setException(ex.getMessage());
        errorResult.setPath(request.getRequestURI());
        return errorResult;
    }

    /**
     * 拼接异常信息
     *
     * @param errorList
     * @return
     */
    private String getErrorMsg(List<FieldError> errorList) {
        StringBuffer sb = new StringBuffer();
        for (FieldError fieldError : errorList) {
            sb.append(fieldError.getDefaultMessage() + "/");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 将用户名写入到cookie
     *
     * @param request
     * @param response
     */
    private void setUsernameToCookie(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        CookieUtil.setCookie(request, response, "username", username, 60, true, SiteConstant.cookieDomain);
    }
}
