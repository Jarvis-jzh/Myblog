package com.jzh.myblog.controller;


import com.jzh.myblog.dto.ArchivePageDTO;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章归档 前端控制器
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/archive")
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;

    /**
     * 根据归档日期获取文章
     *
     * @param dto
     * @return
     */
    @GetMapping(value = "/articleByArchive")
    public Result getArticleByArchive(ArchivePageDTO dto){
        return archiveService.listArticlesByArchive(dto);
    }

    /**
     * 查询所有归档及其文章数
     *
     * @return
     */
    @GetMapping(value = "/archives")
    public Result getArchiveNameAndArticleNum() {
        return archiveService.listArchives();
    }
}

