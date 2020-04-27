package com.jzh.myblog.queue.request;

import com.jzh.myblog.constant.SiteConstant;
import com.jzh.myblog.dto.FeedbackDTO;
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
 * @description 发送邮件提醒网站有反馈信息
 */
@Slf4j(topic = "sendFeedbackRemindRequest")
public class SendFeedbackRemindRequest extends Request {

    private JavaMailSender mailSender;

    private FeedbackDTO feedbackDTO;

    public SendFeedbackRemindRequest(String jobName, String jobId, JavaMailSender mailSender, FeedbackDTO feedbackDTO) {
        super(jobName, jobId);
        this.mailSender = mailSender;
        this.feedbackDTO = feedbackDTO;
    }

    @Override
    public boolean deal() {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            //发件人
            helper.setFrom(SiteConstant.SITE_EMAIL);
            //收件人
            helper.setTo(SiteConstant.SITE_EMAIL);
            //标题
            helper.setSubject(getSubject());
            //文本
            helper.setText(getText());
            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取标题
     * @return
     */
    private String getSubject() {
        StringBuffer sb = new StringBuffer();
        sb.append("来自 ")
                .append(feedbackDTO.getFeedbackName())
                .append(" (")
                .append(feedbackDTO.getContactInfo())
                .append(") 的反馈\n");
        return sb.toString();
    }

    /**
     * 获取内容
     * @return
     */
    private String getText() {
        StringBuffer sb = new StringBuffer();
        sb.append("来自 ")
                .append(feedbackDTO.getFeedbackName())
                .append(" (")
                .append(feedbackDTO.getContactInfo())
                .append(") 的朋友说：\n")
                .append("\t")
                .append(feedbackDTO.getFeedbackContent());
        return sb.toString();
    }
}
