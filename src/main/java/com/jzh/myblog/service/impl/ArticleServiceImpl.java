package com.jzh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzh.myblog.constant.SiteConstant;
import com.jzh.myblog.dto.ArticleEditorDTO;
import com.jzh.myblog.dto.PageDTO;
import com.jzh.myblog.entity.Article;
import com.jzh.myblog.entity.User;
import com.jzh.myblog.mapper.UserMapper;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.mapper.ArticleMapper;
import com.jzh.myblog.mapper.VisitorMapper;
import com.jzh.myblog.service.ArchiveService;
import com.jzh.myblog.service.ArticleService;
import com.jzh.myblog.service.TagService;
import com.jzh.myblog.service.VisitorService;
import com.jzh.myblog.util.BuildArticleTabloidUtil;
import com.jzh.myblog.util.ResultUtil;
import com.jzh.myblog.util.TimeUtil;
import com.jzh.myblog.vo.ArticleManagementVO;
import com.jzh.myblog.vo.ArticleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private BuildArticleTabloidUtil buildArticleTabloidUtil;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private VisitorMapper visitorMapper;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result editorArticle(ArticleEditorDTO editorDTO, String username) {
        Article article = new Article();
        BeanUtils.copyProperties(editorDTO, article);

        if (article.getId() != null) {
            // 保存文章
            if (!updateById(article)) {
                return ResultUtil.error(CodeEnum.PUBLISH_ARTICLE_FAIL);
            }
            return ResultUtil.success("/article/" + article.getArticleId());
        }

        article.setArticleId(new TimeUtil().getLongTime())
                .setArticleTabloid(buildArticleTabloidUtil.buildArticleTabloid(editorDTO.getArticleHtmlContent()));
//                .setLastArticleid(lastArticle)

        // 设置文章作者 和 文章链接
        String author = userMapper.getNicknameByUsername(username);
        article.setAuthor(author);
        if ("原创".equals(article.getArticleType())) {
            article.setOriginalAuthor(SiteConstant.SITE_OWNER)
                    .setArticleUrl(SiteConstant.SITE_OWNER_URL + "/article/" + article.getArticleId());
        }

        // 保存文章
        if (!this.save(article)) {
            return ResultUtil.error(CodeEnum.PUBLISH_ARTICLE_FAIL);
        }

        // 保存标签
        // TODO Arrays.asList() 返回的 ArrayList 不是 java.util 下的，
        // TODO 而是 Arrays 下的内部实现类，此类继承了 AbstractList 类，add() 和 remove() 方法均未实现（直接抛异常）
        tagService.saveAll(new ArrayList<String>(Arrays.asList(article.getArticleTags().split(","))));
        // List<String> collect = Arrays.stream(article.getArticleTags().split(",")).collect(Collectors.toList());

        // 归档
        String date = new TimeUtil().getParseDateForTwo(article.getCreateTime());
        archiveService.saveArchive(date);

        // 添加访问记录
        visitorMapper.insertVisitorNumByPageName("article/" + article.getArticleId(), "0");

        return ResultUtil.success("/article/" + article.getArticleId());
    }

    @Override
    public Result getArticleByArticleId(Long articleId) {
        Article article = this.getArticle(articleId);
        if (article == null) {
            return ResultUtil.error(CodeEnum.ARTICLE_NOT_EXIST);
        }
        ArticleVO vo = new ArticleVO();
        BeanUtils.copyProperties(article, vo);
        vo.setArticleTags(article.getArticleTags().split(","));
        return ResultUtil.success(vo);
    }

    @Override
    public Result getRecentPosts() {
        PageHelper.startPage(1, 5);
        List<Article> articles = this.baseMapper.getAll();
        return ResultUtil.success(articles);
    }

    @Override
    public Result getMyArticles(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getRows());
        List<Article> articleList = getBaseMapper().getAll();
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        return ResultUtil.success(pageInfo);
    }

    @Override
    public Integer getArticleCount() {
        return this.count();
    }

    @Override
    public Result getArticleManagement(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getRows());
        List<ArticleManagementVO> articleList = getBaseMapper().getArticleManagement();
        for (ArticleManagementVO article : articleList) {
            article.setVisitorNum(visitorService.getVisitorNumByArticleId(article.getArticleId()).getData());
        }
        PageInfo<ArticleManagementVO> pageInfo = new PageInfo<>(articleList);
        return ResultUtil.success(pageInfo);
    }

    @Override
    public Result getDraftArticle(Integer id) {
        Article article =  this.getById(id);
        ArticleVO vo = new ArticleVO();
        BeanUtils.copyProperties(article, vo);
        vo.setArticleTags(article.getArticleTags().split(","));
        return ResultUtil.success(vo);
    }

    @Override
    public Result deleteArticle(Integer id) {
        return ResultUtil.success(getBaseMapper().deleteById(id) == 1, CodeEnum.DELETE_ARTICLE_FAIL);
    }

    private Article getArticle(Long articleId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Article::getArticleId, articleId);
        return this.getOne(wrapper);
    }
}
