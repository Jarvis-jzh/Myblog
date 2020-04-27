package com.jzh.myblog.mapper;

import com.jzh.myblog.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzh.myblog.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章分类 Mapper 接口
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 获取分类名和文章数量
     * @return
     */
    List<CategoryVO> getCategoriesNameAndArticleNum();


    /**
     * 获取分类
     * @param categoryName
     * @return
     */
    Category getCategoryByCategoryName(@Param("categoryName") String categoryName);
}
