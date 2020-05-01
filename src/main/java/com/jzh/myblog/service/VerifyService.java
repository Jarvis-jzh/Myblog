package com.jzh.myblog.service;

import com.jzh.myblog.response.Result;

import javax.servlet.http.HttpSession;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/15 18:33
 * @description
 */
public interface VerifyService {

    /**
     * 获取验证码图片
     *
     * @param session
     * @return
     */
    Result<String> getImgCode(HttpSession session);

    /**
     * 检查验证码输入是否正确
     *
     * @param code
     * @param session
     * @return
     */
    Result checkImgCode(String code, HttpSession session);

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    Result getSmsCode(String phone);

    /**
     * 检验短信验证是否正确
     * @param phone
     * @param smsCode
     * @return
     */
    Result checkSmsCode(String phone, String smsCode);
}
