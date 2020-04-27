package com.jzh.myblog.controller;


import com.jzh.myblog.dto.PageDTO;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 根据id获取文章草稿
     *
     * @return
     */
    @GetMapping("/draftArticle")
    public Result getDraftArticle(@RequestParam("id") Integer id) {
        return articleService.getDraftArticle(id);
    }

    /**
     * 获取最新文章列表
     *
     * @return
     */
    @GetMapping(value = "recentPosts")
    public Result getRecentPosts() {
        return articleService.getRecentPosts();
    }

    /**
     * 获取我的文章
     *
     * @param pageDTO
     * @return
     */
    @GetMapping(value = "myArticles", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getMyArticles(PageDTO pageDTO) {
        return articleService.getMyArticles(pageDTO);
    }

}

