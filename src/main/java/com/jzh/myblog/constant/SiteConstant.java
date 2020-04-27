package com.jzh.myblog.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/4 17:30
 * @description 博客网站常量
 */
@Component
public class SiteConstant {

    /**
     * 填写超级管理员的用户名
     */
    public static final String SITE_OWNER = "夏末";

    /**
     * 填写网站名
     */
    public static final String SITE_NAME = "夏末之家";

    /**
     * 邮箱，用于网站消息提醒
     */
    public static final String SITE_EMAIL = "813997065@qq.com";

    /**
     * 填写网站域名或ip地址(最好是域名，如果你还没有域名填ip也行吧)
     */
    public static final String SITE_OWNER_URL = "https://www.jzh.plus";

    /**
     * 获取 cookie 域名
     */
    @Value("${cookie.domain}")
    public static String cookieDomain = "localhost";

}
