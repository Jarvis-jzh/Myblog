package com.jzh.myblog.service;

import com.jzh.myblog.dto.FeedbackDTO;
import com.jzh.myblog.dto.ReplyFeedbackDTO;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/17 19:35
 * @description 发送邮件业务
 */
public interface SendEmailService {
    /**
     * 发送反馈邮件提醒
     *
     * @param feedbackDTO
     */
    void sendFeedbackRemind(FeedbackDTO feedbackDTO);

    /**
     * 发送回复反馈邮件
     *
     * @param replyFeedbackDTO
     */
    void replyFeedback(ReplyFeedbackDTO replyFeedbackDTO);
}
