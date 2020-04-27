package com.jzh.myblog.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/13 14:59
 * @description
 */
@Data
@Builder
public class ImageUploadVO {
    /**
     *  0 表示上传失败，1 表示上传成功
     */
    private Integer success;

    /**
     * 提示的信息，上传成功或上传失败及错误信息等。
     */
    private String message;

    /**
     * 图片地址，上传成功时才返回
     */
    private String url;
}
