package com.jzh.myblog.util.sms;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/25 13:04
 * @description
 */
@Data
@Component
public class AliyunSmsProperties {
    @Value("${aliyun.sms.access-id}")
    private String accessId;

    @Value("${aliyun.sms.access-key}")
    private String accessKey;

    @Value("${aliyun.sms.sign-name}")
    private String signName;

    @Value("${aliyun.sms.template-code}")
    private String templateCode;
}
