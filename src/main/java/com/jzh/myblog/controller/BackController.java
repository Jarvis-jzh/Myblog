package com.jzh.myblog.controller;

import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.ArticleService;
import com.jzh.myblog.util.TransCodingUtil;
import com.jzh.myblog.vo.ArticleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author jzh
 * @version 1.0.0
 * @title BackControl
 * @date 2020/2/7 15:18
 * @description 所有页面跳转
 */
@Controller
public class BackController {

    private final ArticleService articleService;

    public BackController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 跳转到首页
     */
    @GetMapping({"", "/index"})
    public String index(){
        return "index";
    }

    /**
     * robots txt，禁止爬虫爬取的路径
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/robots.txt")
    public void robotsTxt(HttpServletResponse response) throws IOException {
        Writer writer = response.getWriter();
        String lineSeparator = System.getProperty("line.separator", "\n");
        writer.append("User-agent: *").append(lineSeparator);
        writer.append("Disallow:").append("/user/*").append(lineSeparator);
        writer.append("Disallow:").append("/file/*").append(lineSeparator);
        writer.append("Disallow:").append("/verify/*").append(lineSeparator);
        writer.append("Disallow:").append("/super/*").append(lineSeparator);
        writer.append("Disallow:").append("/css/*").append(lineSeparator);
        writer.append("Disallow:").append("/js/*").append(lineSeparator);
        writer.append("Disallow:").append("/visitor/*").append(lineSeparator);
        writer.append("Disallow:").append("/feedback/*").append(lineSeparator);
        writer.append("Disallow:").append("/friendlink/*").append(lineSeparator);
        writer.append("Disallow:").append("/indexNums/*").append(lineSeparator);
    }

    /**
     * 编写文章页
     */
    @GetMapping("/editor")
    public String editor(@RequestParam(value = "id", required = false) Integer id, HttpServletResponse response) {
        if (null != id) {
            //将类型存入响应头
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("id", String.valueOf(id));
        }
        return "editor";
    }

    /**
     * 访问文章
     *
     * @param articleId
     * @return
     */
    @GetMapping(value = "/article/{articleId}")
    public String show(@PathVariable(value = "articleId") Long articleId,
                       Model model) {
        Result<ArticleVO> article = articleService.getArticleByArticleId(articleId);
        model.addAttribute("data", (ArticleVO) article.getData());
        model.addAttribute("code", article.getCode());
        model.addAttribute("msg", article.getMsg());
        model.addAttribute("success", article.getSuccess());
        return "show";
    }

    /**
     * 跳转到分类页面
     *
     * @param response
     * @param category      分类名
     * @return
     */
    @GetMapping(value = {"/category/{category}", "/category"})
    public String category(@PathVariable(value = "category", required = false) String category, HttpServletResponse response){
        //将类型存入响应头
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        if (StringUtils.isNotBlank(category)) {
            response.setHeader("category", TransCodingUtil.stringToUnicode(category));
        }
        return "category";
    }

    /**
     * 跳转标签页
     *
     * @param tagName
     * @param response
     * @return
     */
    @GetMapping(value = {"/tag/{tag}"})
    public String tag(@PathVariable(value = "tag") String tagName, HttpServletResponse response) {
        //将类型存入响应头
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        if (StringUtils.isNotBlank(tagName)) {
            response.setHeader("tag", TransCodingUtil.stringToUnicode(tagName));
        }
        return "tag";
    }

    /**
     * 跳转归档页
     *
     * @param archive
     * @return
     */
    @GetMapping(value = {"/archive/{archive}", "/archive"})
    public String archive(@PathVariable(value = "archive", required = false) String archive, HttpServletResponse response) {
        //将类型存入响应头
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        if (StringUtils.isNotBlank(archive)) {
            response.setHeader("archive", TransCodingUtil.stringToUnicode(archive));
        }
        return "archive";
    }

    /**
     * 后台
     * @return
     */
    @GetMapping("/superadmin")
    public String superadmin() {
        return "superadmin";
    }

    /**
     * 友链
     * @return
     */
    @GetMapping("/friendlink")
    public String friendLink() {
        return "friendLink";
    }

    /**
     * 登录
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 关于
     * @return
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/feedback")
    public String feedback() {
        return "feedback";
    }

}
