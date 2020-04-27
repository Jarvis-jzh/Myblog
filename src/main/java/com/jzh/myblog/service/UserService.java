package com.jzh.myblog.service;

import com.jzh.myblog.dto.ModifyPasswordDTO;
import com.jzh.myblog.dto.UserInfoDTO;
import com.jzh.myblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzh.myblog.response.Result;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户实体类 服务类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@CacheConfig(cacheNames = "user")
public interface UserService extends IService<User> {

    /**
     * 修改密码
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Result modifyPassword(ModifyPasswordDTO dto);

    /**
     * 获取当前登录用户信息
     * @return
     */
    @Cacheable
    Result getCurrentUser(String username);

    /**
     * 修改用户信息
     * @param dto
     * @param username
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    Result updateUserInfo(UserInfoDTO dto, String username);

    /**
     * 修改头像
     * @param img
     * @param username
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user", allEntries = true)
    Result uploadHead(String img, String username);
}
