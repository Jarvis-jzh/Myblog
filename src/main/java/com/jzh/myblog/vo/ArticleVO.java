package com.jzh.myblog.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/1 20:35
 * @description
 */
@Data
public class ArticleVO {

    private Integer id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章作者
     */
    private String author;

    /**
     * 文章原作者
     */
    private String originalAuthor;

    /**
     * 文章名
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 文章标签
     */
    private String[] articleTags;

    /**
     * 文章类型
     * 原创或转载
     */
    private String articleType;

    /**
     * 博客分类
     */
    private String articleCategories;

    /**
     * 原文链接
     * 转载：则是转载的链接
     * 原创：则是在本博客中的链接
     */
    private String articleUrl;

    /**
     * 文章摘要
     */
    private String articleTabloid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
