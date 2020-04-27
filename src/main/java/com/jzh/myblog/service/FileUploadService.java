package com.jzh.myblog.service;

import com.jzh.myblog.vo.ImageUploadVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/11 15:36
 * @description
 */
public interface FileUploadService {
    ImageUploadVO upload(MultipartFile file);
}
