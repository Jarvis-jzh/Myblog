package com.jzh.myblog.dto;

import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/17 19:31
 * @description 回复邮件DTO
 */
@Data
public class ReplyFeedbackDTO {

    /**
     * 收件人
     */
    private String toMail;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String context;
}
