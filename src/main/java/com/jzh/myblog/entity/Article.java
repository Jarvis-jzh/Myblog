package com.jzh.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("article")
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    @TableField("article_id")
    //@JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    /**
     * 文章作者
     */
    @TableField("author")
    private String author;

    /**
     * 文章原作者
     */
    @TableField("original_author")
    private String originalAuthor;

    /**
     * 文章名
     */
    @TableField("article_title")
    private String articleTitle;

    /**
     * 文章内容
     */
    @TableField("article_content")
    private String articleContent;

    /**
     * 文章标签
     */
    @TableField("article_tags")
    private String articleTags;

    /**
     * 文章类型
     * 原创或转载
     */
    @TableField("article_type")
    private String articleType;

    /**
     * 博客分类
     */
    @TableField("article_categories")
    private String articleCategories;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 原文链接
     * 转载：则是转载的链接
     * 原创：则是在本博客中的链接
     */
    @TableField("article_url")
    private String articleUrl;

    /**
     * 文章摘要
     */
    @TableField("article_tabloid")
    private String articleTabloid;

//    /**
//     * 喜欢
//     */
//    @TableField("likes")
//    private Integer likes;
//
//    /**
//     * 上一篇文章id
//     */
//    @TableField("last_articleId")
//    private Long lastArticleid;
//
//    /**
//     * 下一篇文章id
//     */
//    @TableField("next_articleId")
//    private Long nextArticleid;

    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
