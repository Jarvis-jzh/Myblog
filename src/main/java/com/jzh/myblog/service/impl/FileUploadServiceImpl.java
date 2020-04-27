package com.jzh.myblog.service.impl;

import com.jzh.myblog.constant.SiteConstant;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.service.FileUploadService;
import com.jzh.myblog.util.image.ImageConverUtil;
import com.jzh.myblog.util.image.ImageRemarkUtil;
import com.jzh.myblog.util.oss.AliyunOssUtil;
import com.jzh.myblog.util.oss.FolderNameEnum;
import com.jzh.myblog.vo.ImageUploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/11 15:36
 * @description
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private AliyunOssUtil ossUtils;

    /**
     * 放行的图片后缀
     * "jpg", "jpeg", "gif", "png", "bmp", "webp", "JPG", "JPEG", "GIF", "PNG", "BMP", "WEBP"
     */
    private static Map<String, Integer> filePrefix = new HashMap<>();

    static {
        filePrefix.put("jpg", 1);
        filePrefix.put("jpeg", 1);
        filePrefix.put("gif", 1);
        filePrefix.put("png", 1);
        filePrefix.put("bmp", 1);
        filePrefix.put("webp", 1);
        filePrefix.put("JPG", 1);
        filePrefix.put("JPEG", 1);
        filePrefix.put("GIF", 1);
        filePrefix.put("PNG", 1);
        filePrefix.put("BMP", 1);
        filePrefix.put("WEBP", 1);
    }

    @Override
    public ImageUploadVO upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
//        System.out.println(prefix);
        if (!filePrefix.containsKey(prefix)) {
            return ImageUploadVO.builder()
                    .success(0)
                    .message(CodeEnum.IMG_UPLOAD_FORMAT_ERROR.getMessage())
                    .build();
        }
        try {
            ImageRemarkUtil util = new ImageRemarkUtil();
            BufferedImage image = util.markImageByText(SiteConstant.SITE_NAME, file.getInputStream(), null);
            String fileUrl = ossUtils.upLoad(file.getOriginalFilename(), ImageConverUtil.imageToInputStream(image, prefix), FolderNameEnum.ARTICLE_IMAGE);
            return ImageUploadVO.builder()
                    .success(1)
                    .message(CodeEnum.IMG_UPLOAD_SUCCESS.getMessage())
                    .url(fileUrl)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ImageUploadVO.builder()
                .success(0)
                .message(CodeEnum.IMG_UPLOAD_FAIL.getMessage())
                .build();
    }
}
