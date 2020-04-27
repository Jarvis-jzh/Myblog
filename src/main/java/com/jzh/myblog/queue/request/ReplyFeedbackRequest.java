package com.jzh.myblog.queue.request;

import com.jzh.myblog.constant.SiteConstant;
import com.jzh.myblog.dto.ReplyFeedbackDTO;
import com.jzh.myblog.queue.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/17 17:53
 * @description 向反馈的用户回复信息
 */
@Slf4j(topic = "replyFeedbackRequest")
public class ReplyFeedbackRequest extends Request {

    private JavaMailSender mailSender;

    private ReplyFeedbackDTO replyFeedbackDTO;

    public ReplyFeedbackRequest(String jobName, String jobId, JavaMailSender mailSender, ReplyFeedbackDTO replyFeedbackDTO) {
        super(jobName, jobId);
        this.mailSender = mailSender;
        this.replyFeedbackDTO = replyFeedbackDTO;
    }

    @Override
    public boolean deal() {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            //发件人
            helper.setFrom(SiteConstant.SITE_EMAIL);
            //收件人
            helper.setTo(replyFeedbackDTO.getToMail());
            //标题
            helper.setSubject(replyFeedbackDTO.getSubject());
            //文本
            helper.setText(replyFeedbackDTO.getContext());
            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
