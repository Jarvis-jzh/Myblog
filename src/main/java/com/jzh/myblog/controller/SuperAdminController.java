package com.jzh.myblog.controller;

import com.jzh.myblog.dto.*;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.*;
import com.jzh.myblog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/2 11:13
 * @description
 */
@RestController
@RequestMapping(value = "super")
public class SuperAdminController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FriendlinkService friendlinkService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private UserService userService;

    /**
     * 编写（发布）文章
     * @param editorDTO
     * @return
     */
    @PostMapping(value = "/editor", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result editorArticle(@RequestBody ArticleEditorDTO editorDTO, @AuthenticationPrincipal Principal principal) {
        String username = principal.getName();
        return articleService.editorArticle(editorDTO, username);
    }


    /**
     * 获取我的文章管理数据
     *
     * @param pageDTO
     * @return
     */
    @GetMapping(value = "articleManagement")
    public Result getArticleManagement(PageDTO pageDTO) {
        return articleService.getArticleManagement(pageDTO);
    }

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @PostMapping(value = "deleteArticle")
    public Result deleteArticle(@RequestParam("id") Integer id){
        return articleService.deleteArticle(id);
    }

    /**
     * 获取所有分类
     * @return
     */
    @GetMapping(value = "categories")
    public Result getCategories() {
        return categoryService.getCategories();
    }

    /**
     * 更新分类
     *
     * @param categoryName
     * @param type              1、添加    2、删除
     * @return
     */
    @PostMapping(value = "updateCategory")
    public Result updateCategory(@RequestParam("categoryName") String categoryName, @RequestParam("type") Integer type) {
        return categoryService.updateCategory(categoryName, type);
    }

    /**
     * 获取友链
     * @param pageDTO
     * @return
     */
    @GetMapping(value = "friendLink")
    public Result getFriendLink(PageDTO pageDTO) {
        return friendlinkService.getFriendLink(pageDTO);
    }

    /**
     * 更新友链
     *
     * @param   friendLinkPageDTO
     * @return
     */
    @PostMapping(value = "updateFriendLink")
    public Result updateFriendLink(@RequestBody FriendLinkPageDTO friendLinkPageDTO) {
        return friendlinkService.updateFriendLink(friendLinkPageDTO);
    }

    /**
     * 反馈管理
     *
     * @param pageDTO
     * @return
     */
    @GetMapping(value = "feedbackManagement")
    public Result getFeedbackManagement(PageDTO pageDTO) {
        return feedbackService.getFeedbackManagement(pageDTO);
    }

    /**
     * 回复反馈
     *
     * @param replyFeedbackDTO
     * @return
     */
    @PostMapping(value = "replyFeedback")
    public Result replyFeedback(@RequestBody ReplyFeedbackDTO replyFeedbackDTO) {
        sendEmailService.replyFeedback(replyFeedbackDTO);
        return ResultUtil.success();
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @GetMapping(value = "user")
    public Result getUser(@AuthenticationPrincipal Principal principal) {
        String username = principal.getName();
        return userService.getCurrentUser(username);
    }

    /**
     * 修改用户信息
     * @param dto
     * @param principal
     * @return
     */
    @PostMapping(value = "updateUserInfo")
    public Result updateUserInfo(UserInfoDTO dto, @AuthenticationPrincipal Principal principal) {
        String username = principal.getName();
        return userService.updateUserInfo(dto, username);
    }

    /**
     * 更换用户头像
     * @param img
     * @param principal
     * @return
     */
    @PostMapping(value = "uploadHead")
    public Result uploadHead(@RequestParam("img") String img, @AuthenticationPrincipal Principal principal) {
        String username = principal.getName();
        return userService.uploadHead(img, username);
    }
}
