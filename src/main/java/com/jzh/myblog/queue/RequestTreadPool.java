package com.jzh.myblog.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/17 17:49
 * @description 异步线程池
 */
public class RequestTreadPool {

    private ExecutorService threadPool = Executors.newFixedThreadPool(1);

    public RequestTreadPool(){
        threadPool.submit(new AsRequestThread());
    }

    private static class Singleton {

        private static RequestTreadPool instance;

        static {
            instance = new RequestTreadPool();
        }

        public static RequestTreadPool getInstance(){
            return instance;
        }
    }

    public static RequestTreadPool getInstence(){
        return Singleton.getInstance();
    }

    public static void init(){
        getInstence();
    }

}
