package com.jzh.myblog.security;

import com.jzh.myblog.security.filter.ValidateCodeFilter;
import com.jzh.myblog.security.impl.UserAuthenticationProviderImpl;
import com.jzh.myblog.security.impl.UserDetailsServiceImpl;
import com.jzh.myblog.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/29 11:45
 * @description security配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RedisServiceImpl redisServiceImpl;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置 userDetails 的数据源，密码加密格式
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(new UserAuthenticationProviderImpl(userDetailsService(), passwordEncoder()));
    }

    /**
     * 配置放行的资源
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .headers()
                .frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/editor", "/superadmin", "/file/**", "/super/**").hasRole("SUPERADMIN")
//                .antMatchers("/test/**").hasRole("SUPERADMIN")
                .and()
                .addFilterBefore(new ValidateCodeFilter("/login", "/login?error", redisServiceImpl), UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and()
                // 开启 cookie 保存用户数据
                .rememberMe()
                // 设置有效期
                .tokenValiditySeconds(60*60*3);
//                .key(UUID.randomUUID().toString());
    }
}
