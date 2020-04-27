package com.jzh.myblog.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/1 17:21
 * @description
 */
@Data
public class ArticleManagementVO {
    private Integer id;

    private Long articleId;

    private String articleTitle;

    private String articleCategories;

    private Date createTime;

    private Date updateTime;

    private Long visitorNum;
}
