package com.jzh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.jzh.myblog.dto.TagPageDTO;
import com.jzh.myblog.entity.Article;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.entity.Tag;
import com.jzh.myblog.mapper.ArticleMapper;
import com.jzh.myblog.mapper.TagMapper;
import com.jzh.myblog.service.TagService;
import com.jzh.myblog.util.ResultUtil;
import com.jzh.myblog.util.StringAndArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public boolean saveAll(List<String> tagList) {
        for (int i = 0; i < tagList.size(); i++) {
            if (isExist(tagList.get(i))) {
                tagList.remove(i);
                i--;
            }
        }
        List<Tag> entity = this.createEntity(tagList);
        return this.saveBatch(entity);
    }

    @Override
    public Result getTags() {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("tag_name");
        List<String> tagList = this.listObjs(wrapper, String::valueOf);
        return ResultUtil.success(tagList);
    }

    @Override
    public Integer getTagCount() {
        return this.count();
    }

    @Override
    public Result getArticleByTag(TagPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getRows());
        List<Article> articles = articleMapper.getArticleByTagName(dto.getTagName());
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<Article> result = new ArrayList<>(articles.size());
        // 二次判断标签是否匹配
        String tagName = dto.getTagName();
        for (Article article : articles) {
            String[] tagsArray = StringAndArray.stringToArray(article.getArticleTags());
            for (String str : tagsArray) {
                if (tagName.equals(str)) {
                    result.add(article);
                    break;
                }
            }
        }
        pageInfo.setList(result);

        return ResultUtil.success(pageInfo);
    }

    /**
     * 根据标签名封装实体类
     *
     * @param tagList
     * @return
     */
    private List<Tag> createEntity(List<String> tagList) {
        ArrayList<Tag> list = Lists.newArrayListWithCapacity(tagList.size());
        for (String s : tagList) {
            list.add(new Tag().setTagName(s));
        }
        return list;
    }

    /**
     * 判断标签是否存在
     *
     * @param tagName
     * @return
     */
    private boolean isExist(String tagName) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Tag::getTagName, tagName);
        return this.count() == 1;
    }
}
