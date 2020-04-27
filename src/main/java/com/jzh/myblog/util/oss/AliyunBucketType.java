package com.jzh.myblog.util.oss;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/25 10:34
 * @description 阿里云OSS存储空间
 */
public enum AliyunBucketType {

    PUBLIC_BUCKET("jzh-myblog"),
    ;

    private String name;

    AliyunBucketType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
