package com.jzh.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/13 14:14
 * @description 配置静态资源映射
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源映射
        registry.addResourceHandler("css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("img/**")
                .addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("font/**")
                .addResourceLocations("classpath:/static/font/");
        registry.addResourceHandler("plugin/**")
                .addResourceLocations("classpath:/static/plugin/");

//        registry.addResourceHandler("/article/")
//                .addResourceLocations("classpath:/static/");

        // swagger
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
