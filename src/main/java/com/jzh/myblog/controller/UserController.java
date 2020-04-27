package com.jzh.myblog.controller;


import com.jzh.myblog.dto.ModifyPasswordDTO;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户实体类 前端控制器
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("modifyPassword")
    public Result modifyPassword(@RequestBody @Validated ModifyPasswordDTO dto) {
        return userService.modifyPassword(dto);
    }
}

