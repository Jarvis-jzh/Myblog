package com.jzh.myblog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/2 21:38
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FriendLinkPageDTO extends PageDTO {

    private Integer id;

    /**
     * 博主
     */
    private String blogger;

    /**
     * 头像地址
     */
    private String headImg;

    /**
     * 博主url
     */
    private String url;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 1、新增    2、删除    3、修改
     */
    private Integer type;
}
