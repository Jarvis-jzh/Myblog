package com.jzh.myblog.queue.request;

import com.jzh.myblog.queue.Request;
import com.jzh.myblog.redis.StringRedisServiceImpl;
import com.jzh.myblog.util.sms.AliyunSmsUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/25 14:58
 * @description
 */
@Slf4j(topic = "sendSmsRequest")
public class SendSmsRequest extends Request {

    private StringRedisServiceImpl redisService;

    private AliyunSmsUtils utils;

    private String phone;

    private String key;

    /**
     *
     * @param jobName           任务名
     * @param jobId             任务id
     * @param redisService      注入 StringRedisServiceImpl
     * @param utils             注入 AliyunSmsUtils 工具类
     * @param phone             发送的手机号
     * @param key               将验证码保存到redis的 key
     */
    public SendSmsRequest(String jobName, String jobId, StringRedisServiceImpl redisService, AliyunSmsUtils utils, String phone, String key) {
        super(jobName, jobId);
        this.redisService = redisService;
        this.utils = utils;
        this.phone = phone;
        this.key = key;
    }

    @Override
    public boolean deal() {
        try {
            Integer code = utils.smsTest();
            if (null == code) {
                return false;
            }
            redisService.set(key, code, 60 * 5);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
