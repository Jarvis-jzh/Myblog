package com.jzh.myblog.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/17 20:17
 * @description
 */
@Data
public class PageVO {

    /**
     * 每页显示条数
     */
    private int pageSize;

    /**
     * 当前所在页码
     */
    private int pageNum;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 总记录数
     */
    private Long total;

    private List<?> data;
}
