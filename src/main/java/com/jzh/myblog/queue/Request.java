package com.jzh.myblog.queue;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/17 17:49
 * @description 请求
 */
@Data
public abstract class Request {
    private String jobName;

    private String jobId;

    private AtomicInteger count=new AtomicInteger(1);

    public Request(String jobName, String jobId) {
        this.jobName = jobName;
        this.jobId = jobId;
    }

    public abstract boolean deal();
}
