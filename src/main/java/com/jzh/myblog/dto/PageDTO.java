package com.jzh.myblog.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/17 19:53
 * @description 分页DTO
 */
public class PageDTO {

    /**
     * 每页显示条数，默认 10
     */
    private int rows = 10;

    /**
     * 当前页
     */
    private int pageNum = 1;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "rows=" + rows +
                ", pageNum=" + pageNum +
                '}';
    }
}
