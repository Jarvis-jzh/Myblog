package com.jzh.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/21 20:38
 * @description 错误页面跳转
 */
@Controller
public class ErrorPageController {

    @GetMapping("/404")
    public String error404(){
        return "404";
    }
}
