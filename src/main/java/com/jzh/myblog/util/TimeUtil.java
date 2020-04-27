package com.jzh.myblog.util;

import javax.xml.crypto.Data;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/4 17:47
 * @description 时间工具
 */
public class TimeUtil {

    /**
     * 获取当前时间戳
     * @return
     */
    public Long getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 格式化日期
     * 使用线程安全的DateTimeFormatter
     * @return “年-月-日”字符串
     */
    public String getFormatDateForThree(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(format);
    }

    /**
     * 格式化日期
     * 使用线程安全的DateTimeFormatter
     * @return “年-月-日 时:分:秒”字符串
     */
    public String getFormatDateForSix(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(format);
    }

    /**
     * 格式化日期
     * 使用线程安全的DateTimeFormatter
     * @return “年-月-日 时:分”字符串
     */
    public String getFormatDateForFive(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(format);
    }

    /**
     * 解析日期
     *
     * @param  date
     * @return          日期：2020年2月
     */
    public String getParseDateForTwo(Date date) {
        return DateTimeFormatter.ofPattern("yyyy年MM月").format(date2LocalDateTime(date));
    }

    /**
     * 解析日期
     * @param date 日期 2018-06-21
     * @return
     */
    public LocalDate getParseDateForThree(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, format);
    }

    /**
     * 解析日期
     * @param date 日期 2018-06-21 12:01:25
     * @return
     */
    public LocalDate getParseDateForSix(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDate.parse(date, format);
    }

    /**
     * 获得当前时间的时间戳
     * @return 时间戳
     */
    public long getLongTime(){
        Date now = new Date();
        return now.getTime()/1000;
    }

    /**
     * 时间中横杆转换为年
     * @param str 2018-08
     * @return 2018年8月
     */
    public String timeWhippletreeToYear(String str){
        StringBuilder s = new StringBuilder();
        s.append(str.substring(0,4));
        s.append("年");
        s.append(str.substring(5,7));
        s.append("月");
        return String.valueOf(s);
    }

    /**
     * 时间中的年转换为横杠
     * @param str 2018年07月
     * @return 2018-07
     */
    public String timeYearToWhippletree(String str){
        StringBuilder s = new StringBuilder();
        s.append(str.substring(0,4));
        s.append("-");
        s.append(str.substring(5,7));
        return String.valueOf(s);
    }

    /**
     * Date转换为LocalDateTime
     * @param date
     */
    public LocalDateTime date2LocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
//        System.out.println(localDateTime.toString());
//        System.out.println(localDateTime.toLocalDate() + " " +localDateTime.toLocalTime());
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println(dateTimeFormatter.format(localDateTime));
    }

    /**
     * LocalDateTime转换为Date
     * @param localDateTime
     */
    public Date localDateTime2Date( LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 获取昨天的日期
     * @return
     */
    public String getYesterdayDate() {
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date2LocalDateTime(yesterday));
    }
}
