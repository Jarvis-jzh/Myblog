package com.jzh.myblog.service;

import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.ArchivePageDTO;
import com.jzh.myblog.entity.Archive;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzh.myblog.entity.Article;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.vo.ArchiveVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 文章归档 服务类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@CacheConfig(cacheNames = "archive")
public interface ArchiveService extends IService<Archive> {

    /**
     * 保存归档
     *
     * @param date
     * @return
     */
    Boolean saveArchive(String date);

    /**
     * 根据归档日期获取文章
     *
     * @param dto
     * @return
     */
    @Cacheable
    Result<PageInfo<Article>> listArticlesByArchive(ArchivePageDTO dto);

    /**
     * 查询所有归档及其文章数
     *
     * @return
     */
    @Cacheable(key = "#root.methodName")
    Result<List<ArchiveVO>> listArchives();

}
