package com.jzh.myblog.controller;

import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/27 21:46
 * @description
 */
@Slf4j
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/indexNums")
    public Result getIndexNum() {
        return indexService.getIndexNum();
    }
}
