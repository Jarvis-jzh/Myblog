package com.jzh.myblog.controller;


import com.jzh.myblog.dto.CategoryPageDTO;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章分类 前端控制器
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有类型名
     *
     * @return
     */
    @GetMapping("categoryNameAndArticleNum")
    public Result getCategoriesNameAndArticleNum() {
        return categoryService.getCategoriesNameAndArticleNum();
    }

    /**
     * 通过类型名获取所有文章
     *
     * @param category
     * @return
     */
    @GetMapping("articleByCategory")
    public Result getArticleByCategory(CategoryPageDTO category){
        return categoryService.getArticleByCategory(category);
    }
}

