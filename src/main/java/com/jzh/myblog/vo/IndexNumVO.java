package com.jzh.myblog.vo;

import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/27 22:00
 * @description
 */
@Data
public class IndexNumVO {

    /**
     * 文章数
     */
    private Integer articleCount;

    /**
     * 分类数
     */
    private Integer categoryCount;

    /**
     * 标签数
     */
    private Integer tagCount;
}
