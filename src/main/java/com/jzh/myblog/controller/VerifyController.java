package com.jzh.myblog.controller;

import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.VerifyService;
import com.jzh.myblog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/15 18:30
 * @description
 */
@RestController
@RequestMapping("verify")
public class VerifyController {

    @Autowired
    private VerifyService verifyService;

    /**
     * 获取验证码图片
     * @param session
     * @return
     */
    @GetMapping("imgCode")
    public Result getImgCode(HttpSession session) {
        return verifyService.getImgCode(session);
    }

    /**
     * 验证图片验证码是否输入正确
     *
     * @param code
     * @param session
     * @return
     */
    @GetMapping("checkImgCode")
    public Result checkImgCode(@RequestParam("code") String code, HttpSession session) {
        return verifyService.checkImgCode(code, session);
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @GetMapping("smsCode")
    public Result getSmsCode(@RequestParam("phone") String phone) {
        if (!"13229853308".equals(phone)) {
            return ResultUtil.error(CodeEnum.LOGIN_NOT_YET_OPEN);
        }
        return verifyService.getSmsCode(phone);
    }

    /**
     * 检验短信验证是否正确
     * @param phone
     * @return
     */
    @GetMapping("checkSmsCode")
    public Result checkSmsCode(@RequestParam("phone") String phone, @RequestParam("smsCode") String smsCode) {
        return verifyService.checkSmsCode(phone, smsCode);
    }

}
