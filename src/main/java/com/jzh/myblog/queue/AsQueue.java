package com.jzh.myblog.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/17 17:49
 * @description 异步队列
 */
@Slf4j(topic = "queue")
public class AsQueue {
    //最多承受1000个任务
    private final ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(1000);

    /**
     * 放入请求
     * @param request
     */
    public void putRequest(Request request){
        try {
            queue.put(request);
        } catch (InterruptedException e) {
            //如果放入队列失败,尝试重新放入
            try {
                queue.put(request);
            } catch (InterruptedException e2) {
                log.error("任务名称为:"+request.getJobName()+",任务id为:"+request.getJobId()+"的任务放入失败,"+e2.getMessage(),e2);
            }
        }
    }

    /**
     * 获取请求
     * @return
     * @throws InterruptedException 中断异常
     */
    public Request takeRequest() throws InterruptedException {
        return queue.take();
    }

    private static class Sinleton {

        private static AsQueue instence;

        static {
            instence = new AsQueue();
        }

        public static AsQueue getInstence(){
            return instence;
        }
    }

    public static AsQueue getInstance(){
        return Sinleton.getInstence();
    }

    public static void init(){
        getInstance();
    }
}
