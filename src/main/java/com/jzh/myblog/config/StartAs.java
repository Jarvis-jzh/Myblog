package com.jzh.myblog.config;

import com.jzh.myblog.queue.RequestTreadPool;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/17 17:51
 * @description 初始化自定义的请求队列
 */
@Configuration
public class StartAs implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化队列
        RequestTreadPool.init();
    }
}
