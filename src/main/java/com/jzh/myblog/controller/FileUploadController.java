package com.jzh.myblog.controller;

import com.jzh.myblog.vo.ImageUploadVO;
import com.jzh.myblog.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/11 15:07
 * @description
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 图片上传（带水印）
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadArticleImage", method = RequestMethod.POST)
    public ImageUploadVO upload(@RequestParam("editormd-image-file") MultipartFile file) {
        return fileUploadService.upload(file);
    }
}
