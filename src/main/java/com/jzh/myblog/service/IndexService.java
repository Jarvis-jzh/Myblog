package com.jzh.myblog.service;

import com.jzh.myblog.response.Result;
import com.jzh.myblog.vo.IndexNumVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/27 21:48
 * @description
 */
@CacheConfig(cacheNames = "index")
public interface IndexService {

    /**
     * 获取首页标签、分类、文章数值
     *
     * @return
     */
    @Cacheable(key = "#root.methodName")
    Result<IndexNumVO> getIndexNum();
}
