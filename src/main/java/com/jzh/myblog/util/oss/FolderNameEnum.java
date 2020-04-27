package com.jzh.myblog.util.oss;

import lombok.Getter;

/**
 * @author 老肥猪
 * @since 2019/4/23
 */
/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/25 10:34
 * @description 阿里云OSS存储空间文件夹
 */
@Getter
public enum FolderNameEnum {
    /**
     * 文件
     */
    FILE("file"),

    /**
     * 验证码图片
     */
    VERIFY_CODE("verify-code"),

    /**
     * 文章图片
     */
    ARTICLE_IMAGE("article-image"),

    /**
     * 友链头像
     */
    FRIEND_LINK("friend-link"),

    /**
     * 用户头像
     */
    USER_HEAD_IMAGE("user-head-image");
    private String name;

    FolderNameEnum(String name) {
        this.name = name;
    }
}
