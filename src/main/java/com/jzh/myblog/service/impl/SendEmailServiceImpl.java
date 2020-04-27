package com.jzh.myblog.service.impl;

import com.jzh.myblog.dto.FeedbackDTO;
import com.jzh.myblog.dto.ReplyFeedbackDTO;
import com.jzh.myblog.queue.AsQueue;
import com.jzh.myblog.queue.request.ReplyFeedbackRequest;
import com.jzh.myblog.queue.request.SendFeedbackRemindRequest;
import com.jzh.myblog.service.SendEmailService;
import com.jzh.myblog.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/17 19:35
 * @description
 */
@Service
public class SendEmailServiceImpl implements SendEmailService {

    @Autowired
    private JavaMailSender mailSender;

    private AsQueue asQueue = AsQueue.getInstance();

    @Override
    public void sendFeedbackRemind(FeedbackDTO feedbackDTO) {
        SendFeedbackRemindRequest request = new SendFeedbackRemindRequest(getJobName(feedbackDTO), new TimeUtil().getCurrentTime().toString(), mailSender, feedbackDTO);
        asQueue.putRequest(request);
    }

    @Override
    public void replyFeedback(ReplyFeedbackDTO replyFeedbackDTO) {
        ReplyFeedbackRequest request = new ReplyFeedbackRequest(getJobName(replyFeedbackDTO), new TimeUtil().getCurrentTime().toString(), mailSender, replyFeedbackDTO);
        asQueue.putRequest(request);
    }

    private String getJobName(FeedbackDTO feedbackDTO) {
        StringBuffer sb = new StringBuffer();
        sb.append("来自 ")
                .append(feedbackDTO.getFeedbackName())
                .append("(")
                .append(feedbackDTO.getContactInfo())
                .append(") 的反馈");
        return sb.toString();
    }

    private String getJobName(ReplyFeedbackDTO replyFeedbackDTO) {
        StringBuffer sb = new StringBuffer();
        sb.append("回复 ")
                .append(replyFeedbackDTO.getToMail())
                .append(" 的反馈");
        return sb.toString();
    }
}
