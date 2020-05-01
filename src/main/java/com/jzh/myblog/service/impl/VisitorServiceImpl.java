package com.jzh.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.entity.Visitor;
import com.jzh.myblog.mapper.VisitorMapper;
import com.jzh.myblog.service.ArticleService;
import com.jzh.myblog.service.VisitorService;
import com.jzh.myblog.util.ResultUtil;
import com.jzh.myblog.util.TimeUtil;
import com.jzh.myblog.vo.DiscussNumVO;
import com.jzh.myblog.vo.VisitorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 访客 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Autowired
    private RedisServiceImpl redisServiceImpl;

    @Autowired
    private VisitorMapper visitorMapper;

    @Autowired
    private ArticleService articleService;

    @Override
    public Result<VisitorVO> addVisitorNumByPageName(String pageName) {
        // 增加页面访问量，先从缓存中取
        Long pageVisitor = redisServiceImpl.addVisitorNumOnRedis(VISITOR, pageName, 1);
        if (null == pageVisitor) {
            // 缓存未命中，从数据库里取
            pageVisitor = visitorMapper.getVisitorNumByPageName(pageName);
            // 存入缓存
            pageVisitor = redisServiceImpl.putVisitorNumOnRedis(VISITOR, pageName, pageVisitor + 1);
        }

        TimeUtil timeUtil = new TimeUtil();
        // 增加当天访问量
        String date = timeUtil.getFormatDateForThree();
        Long nowVissitor = redisServiceImpl.addVisitorNumOnRedis(VISITOR, date, 1);
        if (null == nowVissitor) {
            // 缓存未命中，从数据库里取
            nowVissitor = visitorMapper.getVisitorNumByPageName(pageName);
            // 存入缓存
            nowVissitor = redisServiceImpl.putVisitorNumOnRedis(VISITOR, date, nowVissitor + 1);
        }

        //增加总访问量
        Long totalVisitor = redisServiceImpl.addVisitorNumOnRedis(VISITOR, TOTAL_VISITOR, 1);
        if (totalVisitor == null) {
            // 缓存未命中，从数据库里取
            totalVisitor = visitorMapper.getTotalVisitor();
            // 存入缓存
            totalVisitor = redisServiceImpl.putVisitorNumOnRedis(VISITOR, TOTAL_VISITOR, totalVisitor + 1);
        }

        VisitorVO vo = new VisitorVO();
        vo.setPageVisitor(pageVisitor);
        vo.setTotalVisitor(totalVisitor);
        return ResultUtil.success(vo);
    }

    @Override
    public void updateVisitorNumByPageName(String pageName, String visitorNum) {
        Integer num = getBaseMapper().updateVisitorNumByPageName(pageName, visitorNum);
        if (0 == num) {
            getBaseMapper().insertVisitorNumByPageName(pageName, visitorNum);
        }
    }

    @Override
    public Result<DiscussNumVO> getDiscussNum() {
        TimeUtil timeUtil = new TimeUtil();
        DiscussNumVO vo = new DiscussNumVO();
        Long totalVisitor = redisServiceImpl.getVisitorNumOnRedis(VISITOR, TOTAL_VISITOR);
        if (totalVisitor == null) {
            // 缓存未命中，从数据库里取
            totalVisitor = visitorMapper.getTotalVisitor();
        }
        Long nowVisitor = redisServiceImpl.getVisitorNumOnRedis(VISITOR, timeUtil.getFormatDateForThree());
        if (nowVisitor == null) {
            // 缓存未命中
            nowVisitor = 0L;
        }

        Integer articleCount = articleService.getArticleCount();

        vo.setTotalVisitor(totalVisitor);
        vo.setNowVisitor(nowVisitor);
        vo.setArticleCount(articleCount);
        return ResultUtil.success(vo);
    }

    @Override
    public Result<Long> getVisitorNumByArticleId(Long articleId) {
        String pageName = "article/" + articleId;
        // 获取页面访问量，先从缓存中取
        Long pageVisitor = redisServiceImpl.addVisitorNumOnRedis(VISITOR, pageName, 0);
        if (null == pageVisitor) {
            // 缓存未命中，从数据库里取
            pageVisitor = visitorMapper.getVisitorNumByPageName(pageName);
            // 存入缓存
            pageVisitor = redisServiceImpl.putVisitorNumOnRedis(VISITOR, pageName, pageVisitor);
        }
        return ResultUtil.success(pageVisitor);
    }
}
