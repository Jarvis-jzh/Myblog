package com.jzh.myblog.mapper;

import com.jzh.myblog.entity.Archive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzh.myblog.vo.ArchiveVO;

import java.util.List;

/**
 * <p>
 * 文章归档 Mapper 接口
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
public interface ArchiveMapper extends BaseMapper<Archive> {

    /**
     * 查询归档日期及其文章数
     *
     * @return
     */
    List<ArchiveVO> listArchives();
}
