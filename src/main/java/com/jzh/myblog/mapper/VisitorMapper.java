package com.jzh.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzh.myblog.entity.Visitor;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 访客 Mapper 接口
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
public interface VisitorMapper extends BaseMapper<Visitor> {

    /**
     * 获取访问量
     *
     * @param pageName
     * @return
     */
    Long getVisitorNumByPageName(@Param("pageName") String pageName);

    /**
     * 获取总访问量
     *
     * @return
     */
    Long getTotalVisitor();

    /**
     * 访问量缓存持久化（更新）
     */
    Integer updateVisitorNumByPageName(@Param("pageName") String pageName, @Param("visitorNum") String visitorNum);

    /**
     * 访问量缓存持久化（插入）
     *
     * @param pageName
     * @param visitorNum
     */
    void insertVisitorNumByPageName(@Param("pageName") String pageName, @Param("visitorNum") String visitorNum);
}
