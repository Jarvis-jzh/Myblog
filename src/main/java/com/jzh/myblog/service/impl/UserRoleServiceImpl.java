package com.jzh.myblog.service.impl;

import com.jzh.myblog.entity.UserRole;
import com.jzh.myblog.mapper.UserRoleMapper;
import com.jzh.myblog.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户权限关联表 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
