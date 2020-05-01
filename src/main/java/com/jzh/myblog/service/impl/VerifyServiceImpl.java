package com.jzh.myblog.service.impl;

import cn.hutool.core.codec.Base64Encoder;
import com.jzh.myblog.constant.VerifyConstant;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.queue.AsQueue;
import com.jzh.myblog.queue.request.SendSmsRequest;
import com.jzh.myblog.redis.StringRedisServiceImpl;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.VerifyService;
import com.jzh.myblog.util.ResultUtil;
import com.jzh.myblog.util.TimeUtil;
import com.jzh.myblog.util.image.ImageConverUtil;
import com.jzh.myblog.util.image.VerifyCodeUtil;
import com.jzh.myblog.util.sms.AliyunSmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/15 18:33
 * @description
 */
@Service
public class VerifyServiceImpl implements VerifyService {

    @Autowired
    private StringRedisServiceImpl stringRedisService;

    @Autowired
    private RedisServiceImpl redisServiceImpl;

    @Autowired
    private AliyunSmsUtils smsUtils;

    private AsQueue asQueue = AsQueue.getInstance();

    /**
     * base64图片前缀
     */
    private static final String BASE64_IMAGE = "data:image/png;base64,";

    @Override
    public Result<String> getImgCode(HttpSession session) {
        String key = VerifyConstant.VERIFY_CODE + session.getId();
        VerifyCodeUtil util = new VerifyCodeUtil();
        BufferedImage bufferedImage = util.getImageInputStream(false);
        byte[] bytes = ImageConverUtil.imageToBytes(bufferedImage, "JPEG");
        if (null == bytes) {
            return ResultUtil.error(CodeEnum.VERIFY_CODE_IMAGE_FAIL);
        }
//        String imageUrl = ossUtils.upLoad(VERIFY_CODE_FILENAME, is, FolderNameEnum.VERIFY_CODE);

        String imageStr = Base64Encoder.encode(bytes);
        String code = util.getCode();
        stringRedisService.set(key, code, 60 * 5);
        return ResultUtil.success(BASE64_IMAGE + imageStr);
    }

    @Override
    public Result checkImgCode(String verifyCode, HttpSession session) {
        String code = redisServiceImpl.checkImgCode(verifyCode, session);
        return ResultUtil.success(null != code, CodeEnum.VERIFY_CODE_FAIL);
    }

    @Override
    public Result getSmsCode(String phone) {
        SendSmsRequest request = new SendSmsRequest("发送短信验证至 " + phone, new TimeUtil().getCurrentTime().toString()
                , stringRedisService, smsUtils, phone, VerifyConstant.SMS_CODE + phone);
        asQueue.putRequest(request);
        return ResultUtil.success();
    }

    @Override
    public Result checkSmsCode(String phone, String smsCode) {
        String key = VerifyConstant.SMS_CODE + phone;
        if (stringRedisService.hasKey(key)) {
            String code = (String) stringRedisService.get(key);
            stringRedisService.remove(key);
            if (smsCode.equals(code)) {
                return ResultUtil.success();
            }
        }
        return ResultUtil.error(CodeEnum.VALIDATE_CODE_FAIL);
    }
}
