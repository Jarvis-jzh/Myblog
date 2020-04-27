package com.jzh.myblog.mapper;

import com.jzh.myblog.entity.Friendlink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 友情链接 Mapper 接口
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
public interface FriendlinkMapper extends BaseMapper<Friendlink> {
    /**
     * 根据id或者博主获取友链
     * @param id
     * @param blogger
     * @return
     */
    Friendlink getFriendlinkByIdOrBlogger(@Param("id") Integer id, @Param("blogger") String blogger);

    /**
     * 获取友链
     * @return
     */
    List<Friendlink> getFriendLink();
}
