package com.jzh.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzh.myblog.entity.Article;
import com.jzh.myblog.vo.ArticleManagementVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章 Mapper 接口
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 获取上一篇文章id，条件为该文章的下一篇文章id为空
     *
     * @return
     */
    Long getArticleIdByNextArticleIdIsNull();

    /**
     * 根据文章id保存下一篇文章id
     *
     * @param articleId
     * @param nextArticleId
     * @return
     */
    Boolean updateNextArticleIdByArticleId(@Param("articleId") Long articleId, @Param("nextArticleId") Long nextArticleId);

    /**
     * 获取最新文章
     *
     * @return
     */
//    List<Article> getRecentPosts();

    /**
     * 获取所有文章
     *
     * @return
     */
    List<Article> getAll();

    /**
     * 根据类型名获取文章列表
     */
    List<Article> getArticleByCategoryName(@Param("categoryName") String categoryName);

    /**
     * 根据标签名获取文章列表
     */
    List<Article> getArticleByTagName(@Param("tagName") String tagName);

    /**
     * 根据归档日期获取文章列表
     *
     * @param archiveName
     * @return
     */
    List<Article> getArticleByArchiveName(@Param("archiveName") String archiveName);

    /**
     * 后台文章管理
     *
     * @return
     */
    List<ArticleManagementVO> getArticleManagement();
}
