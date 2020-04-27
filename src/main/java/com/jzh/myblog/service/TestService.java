package com.jzh.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.PageDTO;
import com.jzh.myblog.entity.Tests;
import org.springframework.cache.annotation.*;

import java.io.Serializable;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/13 18:02
 * @description 
 */
@CacheConfig(cacheNames = "tests")
public interface TestService extends IService<Tests>{

    @Cacheable(key = "#id")
    public Tests getTestsById(Serializable id);

    @Caching(put = {
            @CachePut(key = "#tests.id")
    }, evict = {
            @CacheEvict(value = "page::tests", allEntries = true)
    })
    Tests updateNameById(Tests tests);

    @Cacheable(cacheNames = "page::tests")
    PageInfo<Tests> getPage(PageDTO pageDto);
}
