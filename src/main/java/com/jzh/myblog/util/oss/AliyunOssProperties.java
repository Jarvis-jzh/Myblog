package com.jzh.myblog.util.oss;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/25 10:31
 * @description 阿里云基本配置
 */
@Data
@Component
public class AliyunOssProperties {
    @Value("${aliyun.oss.access-id}")
    private String accessId;

    @Value("${aliyun.oss.access-key}")
    private String accessKey;

    @Value("${aliyun.oss.bucket}")
    private String bucket;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.dir}")
    private String dir;

    @Value("${aliyun.oss.url}")
    private String url;

    private Integer maxSize = Integer.valueOf(1);

    private Integer expire = Integer.valueOf(30);

    private boolean secure = false;

    private String roleSessionName;

    public AliyunOssProperties() {
    }
}
