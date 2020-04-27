package com.jzh.myblog.controller;


import com.jzh.myblog.dto.TagPageDTO;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 标签 前端控制器
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public Result getTags() {
        return tagService.getTags();
    }

    @GetMapping("/articleByTag")
    public Result getArticleByTag(TagPageDTO dto) {
        return tagService.getArticleByTag(dto);
    }
}

