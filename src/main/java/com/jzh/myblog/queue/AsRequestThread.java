package com.jzh.myblog.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/17 17:49
 * @description 异步线程
 */
@Slf4j(topic = "queue")
public class AsRequestThread implements Callable<Boolean> {

    private AsQueue queue = AsQueue.getInstance();

    private final static int MAX_RETRIES_SIZE = 5;

    @Override
    public Boolean call() {
        while (true) {
            Request request = null;
            try {
                request = queue.takeRequest();
            } catch (InterruptedException e) {
                try {
                    request = queue.takeRequest();
                } catch (InterruptedException e1) {
                    log.error("从队列消费请求异常", e);
                }
            }
            if (request != null) {
                //如果失败,进一步放到队尾,稍后再执行一次
                if (!request.deal()) {
                    //最多重试5次
                    if (request.getCount().get() <= MAX_RETRIES_SIZE) {
                        request.getCount().incrementAndGet();
                        queue.putRequest(request);
                    } else {
                        log.error("队列异步任务的Id为:" + request.getJobId() + ",名称为:" + request.getJobName() + ",已经超过重试次数");
                    }
                }
            }
        }
    }
}
