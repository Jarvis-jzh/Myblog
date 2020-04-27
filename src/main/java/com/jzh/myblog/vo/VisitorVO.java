package com.jzh.myblog.vo;

import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/29 22:11
 * @description
 */
@Data
public class VisitorVO {

    /**
     * 总访问量
     */
    private Long totalVisitor;

    /**
     * 当前页访问量
     */
    private Long pageVisitor;
}
