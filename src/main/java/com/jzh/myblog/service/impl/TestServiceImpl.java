package com.jzh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.PageDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzh.myblog.entity.Tests;
import com.jzh.myblog.mapper.TestMapper;
import com.jzh.myblog.service.TestService;

import java.io.Serializable;
import java.util.List;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/13 18:02
 * @description
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Tests> implements TestService {

    @Override
    public Tests getTestsById(Serializable id) {
        return getBaseMapper().selectById(id);
    }

    @Override
    public Tests updateNameById(Tests tests) {
        this.updateById(tests);
        return tests;
    }

    @Override
    public PageInfo<Tests> getPage(PageDTO pageDto) {
        PageHelper.startPage(pageDto.getPageNum(), pageDto.getRows());
        List<Tests> list = this.list();
        PageInfo<Tests> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
