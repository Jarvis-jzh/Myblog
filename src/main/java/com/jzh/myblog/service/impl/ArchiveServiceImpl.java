package com.jzh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.ArchivePageDTO;
import com.jzh.myblog.entity.Archive;
import com.jzh.myblog.entity.Article;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.mapper.ArchiveMapper;
import com.jzh.myblog.mapper.ArticleMapper;
import com.jzh.myblog.service.ArchiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzh.myblog.util.ResultUtil;
import com.jzh.myblog.vo.ArchiveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文章归档 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class ArchiveServiceImpl extends ServiceImpl<ArchiveMapper, Archive> implements ArchiveService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Boolean saveArchive(String date) {
        QueryWrapper<Archive> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Archive::getArchiveName, date);
        Archive archive = this.getOne(wrapper);
        if (archive == null) {
            archive = new Archive();
            archive.setArchiveName(date);
            return this.save(archive);
        }
        return true;
    }

    @Override
    public Result listArticlesByArchive(ArchivePageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getRows());
        List<Article> article = articleMapper.getArticleByArchiveName(dto.getArchiveName());
        PageInfo<Article> pageInfo = new PageInfo<>(article);
        return ResultUtil.success(pageInfo);
    }

    @Override
    public Result listArchives() {
        List<ArchiveVO> list = getBaseMapper().listArchives();
        return ResultUtil.success(list);
    }
}
