package com.jzh.myblog.vo;

import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/15 16:37
 * @description
 */
@Data
public class VerifyCodeVO {

    private String imageUrl;

    private String code;
}
