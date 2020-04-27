package com.jzh.myblog.vo;

import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/1 15:05
 * @description
 */
@Data
public class DiscussNumVO {
    private Long totalVisitor;

    private Long nowVisitor;

    private Integer articleCount;
}
