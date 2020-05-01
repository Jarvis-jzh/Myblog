package com.jzh.myblog.service;

import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.CategoryPageDTO;
import com.jzh.myblog.entity.Article;
import com.jzh.myblog.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.vo.CategoryVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 文章分类 服务类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@CacheConfig(cacheNames = "category")
public interface CategoryService extends IService<Category> {

    /**
     * 查询所有类型名
     *
     * @return 所有类型名
     */
    @Cacheable(key = "#root.methodName")
    Result<List<CategoryVO>> getCategoriesNameAndArticleNum();

    /**
     * 通过类型名获取所有文章
     *
     * @param category 类型分页DTO
     * @return 文章列表
     */
    @Cacheable(key = "#p0.category+'_'+#p0.pageNum+'_'+#p0.rows")
    Result<PageInfo<Article>> getArticleByCategory(CategoryPageDTO category);

    /**
     * 获取分类数
     *
     * @return
     */
    Integer getCategoryCount();

    /**
     * 获取所有分类
     * @return
     */
    @Cacheable(key = "#root.methodName")
    Result getCategories();

    /**
     * 更新分类
     * @param categoryName
     * @param type          1、添加    2、删除
     * @return
     */
    @CacheEvict(value = "category", allEntries = true)
    Result updateCategory(String categoryName, Integer type);
}
