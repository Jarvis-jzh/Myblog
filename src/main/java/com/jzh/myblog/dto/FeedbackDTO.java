package com.jzh.myblog.dto;

import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/11 13:21
 * @description
 */
@Data
public class FeedbackDTO {

    /**
     * 反馈人
     */
    private String feedbackName;

    /**
     * 反馈内容
     */
    private String feedbackContent;

    /**
     * 联系方式
     */
    private String contactInfo;
}
