package com.jzh.myblog.controller;

import com.jzh.myblog.entity.Friendlink;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.FriendlinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 友情链接 前端控制器
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/friendlink")
public class FriendlinkController {

    @Autowired
    private FriendlinkService friendlinkService;

    @GetMapping(value = "friendLink")
    public Result<List<Friendlink>> getFriendLink() {
        return friendlinkService.getFriendLink();
    }
}

