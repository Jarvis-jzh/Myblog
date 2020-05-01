package com.jzh.myblog.service;

import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.ArticleEditorDTO;
import com.jzh.myblog.dto.PageDTO;
import com.jzh.myblog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.vo.ArticleVO;
import org.springframework.cache.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 文章 服务类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@CacheConfig(cacheNames = "article")
public interface ArticleService extends IService<Article> {
    /**
     * 编写文章(修改文章)
     *
     * @param editorDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(allEntries = true),
            @CacheEvict(value = "category", allEntries = true),
            @CacheEvict(value = "tag", allEntries = true),
            @CacheEvict(value = "index", allEntries = true),
            @CacheEvict(value = "archive", allEntries = true),
            @CacheEvict(value = "article", allEntries = true)
    })
    Result editorArticle(ArticleEditorDTO editorDTO, String username);

    /**
     * 通过文章id获取文章
     *
     * @param articleId
     * @return
     */
    @Cacheable(key = "#p0")
    Result<ArticleVO> getArticleByArticleId(Long articleId);

    /**
     * 获取最新的5条文章
     *
     * @return
     */
    @Cacheable(key = "#root.methodName")
    Result<List<Article>> getRecentPosts();

    /**
     * 获取首页我的文章
     *
     * @param pageDTO
     * @return
     */
    @Cacheable(key = "#root.methodName+'_PageDTO(rows='+#pageDTO.rows+', pageNum='+#pageDTO.pageNum+')'")
    Result<PageInfo<Article>> getMyArticles(PageDTO pageDTO);

    /**
     * 获取文章数
     *
     * @return
     */
    @Cacheable(key = "#root.methodName")
    Integer getArticleCount();

    /**
     * 获取后台文章管理数据
     *
     * @param pageDTO
     * @return
     */
    // @Cacheable(key = "#root.methodName+'_PageDTO(rows='+#pageDTO.rows+', pageNum='+#pageDTO.pageNum+')'")
    Result getArticleManagement(PageDTO pageDTO);

    /**
     * 获取文章
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#root.methodName+'_id='+#id")
    Result<ArticleVO> getDraftArticle(Integer id);

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "article", allEntries = true)
    Result deleteArticle(Integer id);
}
