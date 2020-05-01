package com.jzh.myblog.controller;


import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.VisitorService;
import com.jzh.myblog.util.ResultUtil;
import com.jzh.myblog.vo.DiscussNumVO;
import com.jzh.myblog.vo.VisitorVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 访客 前端控制器
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    /**
     * 增加访客量
     *
     * @return 网站总访问量以及访客量
     */
    @GetMapping(value = "/visitorNumByPageName")
    public Result<VisitorVO> getVisitorNumByPageName(@RequestParam("pageName") String pageName) {
        if (StringUtils.isBlank(pageName)) {
            pageName = "index";
        }
        if ("superadmin".equals(pageName) || "editor".equals(pageName)) {
           return ResultUtil.success();
        }
        return visitorService.addVisitorNumByPageName(pageName);
    }

    /**
     * 获取仪表盘数据
     *
     * @return
     */
    @GetMapping("discussNum")
    public Result<DiscussNumVO> getDiscussNum() {
        return visitorService.getDiscussNum();
    }

    /**
     * 获取文章访问量
     *
     * @param articleId
     * @return
     */
    @GetMapping("/visitorCountByArticleId")
    public Result<Long> getVisitorCountByArticleId(@RequestParam("articleId") Long articleId) {
        return visitorService.getVisitorNumByArticleId(articleId);
    }
}
