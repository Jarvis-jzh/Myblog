package com.jzh.myblog.controller;


import com.jzh.myblog.dto.FeedbackDTO;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 反馈 前端控制器
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping(value = "addFeedback")
    public Result addFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        return feedbackService.addFeedback(feedbackDTO);
    }

}

