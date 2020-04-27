package com.jzh.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.CategoryPageDTO;
import com.jzh.myblog.entity.Article;
import com.jzh.myblog.entity.Category;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.mapper.ArticleMapper;
import com.jzh.myblog.mapper.CategoryMapper;
import com.jzh.myblog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzh.myblog.util.ResultUtil;
import com.jzh.myblog.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文章分类 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result getCategoriesNameAndArticleNum() {
        List<CategoryVO> list = getBaseMapper().getCategoriesNameAndArticleNum();
        return ResultUtil.success(list);

//        QueryWrapper<Categories> wrapper = new QueryWrapper<>();
//        wrapper.lambda().select(Categories::getCategoryName);
//        return listObjs(wrapper, String::valueOf);
    }

    @Override
    public Result getArticleByCategory(CategoryPageDTO category) {
        PageHelper.startPage(category.getPageNum(), category.getRows());
        List<Article> article = articleMapper.getArticleByCategoryName(category.getCategory());
        PageInfo<Article> pageInfo = new PageInfo<>(article);
        return ResultUtil.success(pageInfo);
    }

    @Override
    public Integer getCategoryCount() {
        return this.count();
    }

    @Override
    public Result getCategories() {
        List<Category> list = this.list();
        return ResultUtil.success(list);
    }

    @Override
    public Result updateCategory(String categoryName, Integer type) {
        Category category = getBaseMapper().getCategoryByCategoryName(categoryName);
        // 新增
        if (1 == type) {
            if (null == category) {
                category = new Category();
                category.setCategoryName(categoryName);
                return ResultUtil.success(this.save(category), CodeEnum.CATEGORY_FAIL);
            } else {
                return ResultUtil.error(CodeEnum.CATEGORY_EXIST);
            }
        } else {
            // 删除
            if (null == category) {
                return ResultUtil.error(CodeEnum.CATEGORY_NOT_EXIST);
            } else {
                List<Article> articles = articleMapper.getArticleByCategoryName(categoryName);
                if (null == articles || articles.size() == 0) {
                    return ResultUtil.success(getBaseMapper().deleteById(category.getId()), CodeEnum.DELETE_CATEGORY_SUCCESS);
                } else {
                    return ResultUtil.error(CodeEnum.CATEGORY_HAS_ARTICLE);
                }
            }
        }
    }


}
