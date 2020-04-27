package com.jzh.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzh.myblog.dto.FriendLinkPageDTO;
import com.jzh.myblog.dto.PageDTO;
import com.jzh.myblog.entity.Friendlink;
import com.jzh.myblog.response.Result;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 友情链接 服务类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@CacheConfig(cacheNames = "friendlink")
public interface FriendlinkService extends IService<Friendlink> {

    /**
     * 更新友链
     * @param friendLinkPageDTO
     * @return
     */
    @CacheEvict(value = "friendlink", allEntries = true)
    Result updateFriendLink(FriendLinkPageDTO friendLinkPageDTO);

    /**
     * 获取友链
     *
     * @param pageDTO
     * @return
     */
    @Cacheable
    Result getFriendLink(PageDTO pageDTO);

    /**
     * 获取友链
     *
     * @return
     */
    @Cacheable(key = "#root.methodName")
    Result getFriendLink();
}
