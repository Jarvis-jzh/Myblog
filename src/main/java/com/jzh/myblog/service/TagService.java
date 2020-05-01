package com.jzh.myblog.service;

import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.TagPageDTO;
import com.jzh.myblog.entity.Article;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@CacheConfig(cacheNames = "tag")
public interface TagService extends IService<Tag> {

    /**
     * 保存多个标签
     *
     * @param tagList
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = "index", allEntries = true),
            @CacheEvict(value = "tag", allEntries = true)
    })
    boolean saveAll(List<String> tagList);

    /**
     * 获取所有标签
     *
     * @return
     */
    @Cacheable(key = "#root.methodName")
    Result<List<String>> getTags();

    /**
     * 获取标签个数
     *
     * @return
     */
    Integer getTagCount();

    /**
     * 根据标签获取文章
     *
     * @param dto
     * @return
     */
    @Cacheable(key = "#p0.tagName+'_'+#p0.pageNum+'_'+#p0.rows")
    Result<PageInfo<Article>> getArticleByTag(TagPageDTO dto);
}
