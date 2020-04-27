package com.jzh.myblog.task;

import com.jzh.myblog.redis.HashRedisServiceImpl;
import com.jzh.myblog.service.VisitorService;
import com.jzh.myblog.service.impl.RedisServiceImpl;
import com.jzh.myblog.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/1 11:26
 * @description 关于访问量的定时任务
 */
@Component
public class VisitorTask {
    @Autowired
    private HashRedisServiceImpl hashRedisService;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private RedisServiceImpl redisServiceImpl;

    /**
     * cron表达式生成器：http://cron.qqe2.com/
     *
     * 每晚24点清空redis中当日网站访问记录，但保存totalVisitor、visitorVolume、yesterdayVisitor
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetVisitorNumber(){
        //将redis中的所有访客记录更新到数据库中
        LinkedHashMap map = (LinkedHashMap) hashRedisService.getAllFieldAndValue(VisitorService.VISITOR);
        String pageName;

        // 获取新一天的日期
        TimeUtil timeUtil = new TimeUtil();
        String date = timeUtil.getFormatDateForThree();
        boolean flag = false;

        for(Object e : map.keySet()){
            pageName = String.valueOf(e);
            // 如果在任务进行期间有新一天的访问记录，则不做处理
            if (!flag && date.equals(pageName)) {
                flag = true;
                continue;
            }
            // 持久化至数据库中
            visitorService.updateVisitorNumByPageName(pageName, String.valueOf(map.get(e)));
//            hashRedisService.hashDelete(VisitorService.VISITOR, pageName);
        }

        // 将昨天的访问记录删除
        hashRedisService.hashDelete(VisitorService.VISITOR, timeUtil.getYesterdayDate());

        if (flag) {
            // 加锁防止定时任务期间有访客访问时，导致数据丢失
            synchronized (VisitorService.VISITOR) {
                // 再次确认是否有访问记录
                if (!hashRedisService.hasHashKey(VisitorService.VISITOR, date)) {
                    // 存入缓存
                    redisServiceImpl.putVisitorNumOnRedis(VisitorService.VISITOR, date, 0);
                }
            }
        }
    }
}
