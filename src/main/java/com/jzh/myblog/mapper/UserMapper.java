package com.jzh.myblog.mapper;

import com.jzh.myblog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户实体类 Mapper 接口
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 登录
     *
     * @param username
     * @return
     */
    User getUserAndRoleByUsername(@Param("username") String username);

    /**
     * 更新登录时间
     *
     * @param username
     * @param recentlyLanded
     */
    void updateRecentlyLanded(@Param("username") String username, @Param("recentlyLanded") String recentlyLanded);

    /**
     * 通过手机号修改密码
     *
     * @param password
     * @param phone
     * @return
     */
    int updatePasswordByUsername(@Param("password") String password, @Param("phone") String phone);

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    User getUserByUsername(@Param("username") String username);

    /**
     * 根据用户名获取用户昵称
     * @param username
     * @return
     */
    String getNicknameByUsername(@Param("username") String username);

    /**
     * 更新用户头像地址
     *
     * @param avatarImgUrl
     * @param username
     * @return
     */
    int updateAvatarImgUrlByUsername(@Param("avatarImgUrl") String avatarImgUrl, @Param("username") String username);
}
