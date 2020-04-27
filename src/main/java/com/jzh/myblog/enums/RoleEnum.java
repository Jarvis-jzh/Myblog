package com.jzh.myblog.enums;


/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/4 17:30
 * @description 权限
 */
public enum RoleEnum {
    /**
     * 普通用户
     */
    ROLE_USER("ROLE_USER", 1),

    /**
     * 管理员
     */
    ROLE_ADMIN("ROLE_ADMIN", 2),

    /**
     * 超级管理员
     */
    ROLE_SUPERADMIN("ROLE_SUPERADMIN", 3);


    private String name;
    private Integer value;

    RoleEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

}
