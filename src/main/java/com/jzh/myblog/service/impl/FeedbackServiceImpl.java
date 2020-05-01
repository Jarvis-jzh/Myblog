package com.jzh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.FeedbackDTO;
import com.jzh.myblog.dto.PageDTO;
import com.jzh.myblog.entity.Feedback;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.mapper.FeedbackMapper;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzh.myblog.service.SendEmailService;
import com.jzh.myblog.util.RegexpUtil;
import com.jzh.myblog.util.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 反馈 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Autowired
    private SendEmailService sendEmailService;

    @Override
    public Result addFeedback(FeedbackDTO feedbackDTO) {
        if (!RegexpUtil.checkEmail(feedbackDTO.getContactInfo())) {
            return ResultUtil.error(CodeEnum.EMAIL_ERROR);
        }
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(feedbackDTO, feedback);
        boolean save = this.save(feedback);
        // 通过异步线程队列发送反馈信息到邮箱
        sendEmailService.sendFeedbackRemind(feedbackDTO);
        return ResultUtil.success(save, CodeEnum.FEEDBACK_ADD_SUCCESS, CodeEnum.FEEDBACK_ADD_FAIL);
    }

    @Override
    public Result<PageInfo<Feedback>> getFeedbackManagement(PageDTO pageDTO) {
        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(Feedback::getCreateTime);
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getRows());
        List<Feedback> feedbacks = this.list(wrapper);
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbacks);
        return ResultUtil.success(pageInfo);
    }
}
