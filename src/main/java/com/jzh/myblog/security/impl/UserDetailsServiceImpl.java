package com.jzh.myblog.security.impl;

import com.jzh.myblog.entity.Role;
import com.jzh.myblog.entity.User;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.mapper.UserMapper;
import com.jzh.myblog.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/29 11:55
 * @description
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!"13229853308".equals(username)) {
            throw new UsernameNotFoundException(CodeEnum.LOGIN_NOT_YET_OPEN.getMessage());
        }

        User user = userMapper.getUserAndRoleByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        // 修改用户登录时间
        TimeUtil timeUtil = new TimeUtil();
        String recentlyLanded = timeUtil.getFormatDateForSix();
        userMapper.updateRecentlyLanded(user.getUsername(), recentlyLanded);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
