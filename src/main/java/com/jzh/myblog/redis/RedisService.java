package com.jzh.myblog.redis;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/5 17:47
 * @description
 */
public interface RedisService {

    /**
     * 判断key是否存在
     */
    Boolean hasKey(String key);

}
