package com.jzh.myblog.service.impl;

import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.ArticleService;
import com.jzh.myblog.service.CategoryService;
import com.jzh.myblog.service.IndexService;
import com.jzh.myblog.service.TagService;
import com.jzh.myblog.util.ResultUtil;
import com.jzh.myblog.vo.IndexNumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/27 21:48
 * @description
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Result getIndexNum() {
        Integer articleCount = articleService.getArticleCount();
        Integer tagCount = tagService.getTagCount();
        Integer categoryCount = categoryService.getCategoryCount();

        IndexNumVO numVO = new IndexNumVO();
        numVO.setArticleCount(articleCount);
        numVO.setCategoryCount(categoryCount);
        numVO.setTagCount(tagCount);
        return ResultUtil.success(numVO);
    }
}
