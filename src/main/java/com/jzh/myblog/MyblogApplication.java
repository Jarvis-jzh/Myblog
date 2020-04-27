package com.jzh.myblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/4 15:47
 * @description 基于springblog开发的个人博客
 */
@EnableSwagger2
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.jzh.myblog.mapper")
public class MyblogApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyblogApplication.class, args);
    }

}
