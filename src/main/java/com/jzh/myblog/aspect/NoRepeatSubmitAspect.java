package com.jzh.myblog.aspect;

import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.redis.StringRedisServiceImpl;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/4/13 15:06
 * @description aop 拦截所有 Controller Post请求，判断是否重复提交
 */
@Slf4j
@Aspect
@Component
public class NoRepeatSubmitAspect {

    @Autowired
    private StringRedisServiceImpl redisService;

    @Pointcut("execution(public * com.jzh.myblog.controller.*Controller.*(..))")
    public void controller(){

    }

    @Around("controller() && @annotation(postMapping)")
    public Object around(ProceedingJoinPoint pjp, PostMapping postMapping) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            HttpServletRequest request = attributes.getRequest();
            String key = sessionId + "-" + request.getServletPath();
            if (redisService.get(key) == null) {// 如果缓存中有这个url视为重复提交
                Object o = pjp.proceed();
                redisService.set(key, 0, 2);
                return o;
            } else {
//                log.warn(request.getRequestURI() + "重复提交");
                return ResultUtil.error(CodeEnum.REPEAT_SUBMIT);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("验证重复提交时出现未知异常!");
            return ResultUtil.error(CodeEnum.FAIL);
        }
    }
}
