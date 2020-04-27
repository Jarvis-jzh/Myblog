package com.jzh.myblog.service;

import com.jzh.myblog.dto.FeedbackDTO;
import com.jzh.myblog.dto.PageDTO;
import com.jzh.myblog.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzh.myblog.response.Result;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 反馈 服务类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@CacheConfig(cacheNames = "feedback")
public interface FeedbackService extends IService<Feedback> {

    /**
     * 添加反馈信息
     *
     * @param feedbackDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "feedback", allEntries = true)
    Result addFeedback(FeedbackDTO feedbackDTO);

    /**
     * 获取反馈信息
     *
     * @param pageDTO
     * @return
     */
    @Cacheable
    Result getFeedbackManagement(PageDTO pageDTO);
}
