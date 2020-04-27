package com.jzh.myblog.service.impl;

import com.jzh.myblog.entity.Role;
import com.jzh.myblog.mapper.RoleMapper;
import com.jzh.myblog.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
