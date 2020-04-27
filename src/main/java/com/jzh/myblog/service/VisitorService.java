package com.jzh.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.entity.Visitor;

/**
 * <p>
 * 访客 服务类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
public interface VisitorService extends IService<Visitor> {

    /**
     * 缓存名
     */
    String VISITOR = "visitor";

    /**
     * 总访问里
     */
    String TOTAL_VISITOR = "totalVisitor";

    /**
     * 增加并访问量
     *
     * @param pageName
     * @return
     */
    Result addVisitorNumByPageName(String pageName);

    /**
     * 通过访问路径更新访问量
     *
     * @param pageName
     * @param visitorNum
     */
    void updateVisitorNumByPageName(String pageName, String visitorNum);

    /**
     * 获取议表盘所要数据
     *
     * @return
     */
    Result getDiscussNum();

    /**
     * 获取文章访问量
     */
    Result<Long> getVisitorNumByArticleId(Long articleId);
}
