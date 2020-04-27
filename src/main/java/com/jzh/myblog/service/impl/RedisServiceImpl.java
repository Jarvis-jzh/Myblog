package com.jzh.myblog.service.impl;

import com.jzh.myblog.constant.VerifyConstant;
import com.jzh.myblog.redis.HashRedisServiceImpl;
import com.jzh.myblog.redis.StringRedisServiceImpl;
import com.jzh.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/29 21:52
 * @description redis业务逻辑
 */
@Service
public class RedisServiceImpl {

    @Autowired
    private StringRedisServiceImpl stringRedisServiceImpl;
    @Autowired
    private HashRedisServiceImpl hashRedisServiceImpl;

    /**
     * 增加redis中的访客量
     */
    public Long addVisitorNumOnRedis(String key, Object field, long increment){
        boolean fieldIsExist = hashRedisServiceImpl.hasHashKey(key, field);
        if(fieldIsExist){
            return hashRedisServiceImpl.hashIncrement(key, field, increment);
        }
        return null;
    }

    /**
     * 向redis中保存访客量
     */
    public Long putVisitorNumOnRedis(String key, Object field, Object value){
        hashRedisServiceImpl.put(key, field, value);
        return Long.valueOf(hashRedisServiceImpl.get(key, field).toString());
    }

    /**
     * 获得redis中的访客记录
     */
    public Long getVisitorNumOnRedis(String key, Object field){
        boolean fieldIsExist = hashRedisServiceImpl.hasHashKey(key, field);
        if(fieldIsExist){
            return Long.valueOf(hashRedisServiceImpl.get(key, field).toString());
        }
        return null;
    }

    /**
     * 检验图片验证码
     *
     * @param verifyCode    用户输入的验证码
     * @param session       会话
     * @return              如果输入正确返回验证码，输入错误返回 null
     */
    public String checkImgCode(String verifyCode, HttpSession session) {
        String key = VerifyConstant.VERIFY_CODE + session.getId();
        if (stringRedisServiceImpl.hasKey(key)) {
            String code = (String) stringRedisServiceImpl.get(key);
            // 不管输入是否正确都删除并重新获取验证码
            stringRedisServiceImpl.remove(key);
            // 不区分大小写进行比较
            if (code.equalsIgnoreCase(verifyCode)) {
                return code;
            }
        }
        return null;
    }
}
