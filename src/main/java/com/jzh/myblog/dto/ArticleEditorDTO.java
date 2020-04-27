package com.jzh.myblog.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/19 20:46
 * @description 新增文章DTO
 */
@Data
public class ArticleEditorDTO {

    private Integer id;

    /**
     * 文章id
     */
    @ApiModelProperty("文章id")
    private Long articleId;

    /**
     * 文章原作者
     */
    @ApiModelProperty("文章原作者")
    private String originalAuthor;

    /**
     * 文章名
     */
    @ApiModelProperty("文章名")
    private String articleTitle;

    /**
     * 文章内容
     */
    @ApiModelProperty("文章内容")
    private String articleContent;

    /**
     * 文章内容（html）
     */
    @ApiModelProperty("文章内容（html）")
    private String articleHtmlContent;

    /**
     * 文章标签
     */
    @ApiModelProperty("文章标签")
    private String articleTags;

    /**
     * 文章类型
     */
    @ApiModelProperty("文章类型")
    private String articleType;

    /**
     * 博客分类
     */
    @ApiModelProperty("博客分类")
    private String articleCategories;

    /**
     * 原文链接
     * 转载：则是转载的链接
     * 原创：则是在本博客中的链接
     */
    @ApiModelProperty("原文链接")
    private String articleUrl;
}
