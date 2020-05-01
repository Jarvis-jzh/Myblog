package com.jzh.myblog.filter;

import com.alibaba.fastjson.JSON;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.vo.ImageUploadVO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/4/22 14:48
 * @description 图片上传过渡器
 */
//@Order(1)
//@WebFilter(urlPatterns = "/file/uploadArticleImage", filterName = "imageFileFilter")
public class ImageFileFilter implements Filter {

    /**
     * 放行的图片后缀
     * "jpg", "jpeg", "gif", "png", "bmp", "webp", "JPG", "JPEG", "GIF", "PNG", "BMP", "WEBP"
     */
//    private static Map<String, Integer> filePrefix = new HashMap<>();
//
//    static {
//        filePrefix.put("jpg", 1);
//        filePrefix.put("jpeg", 1);
//        filePrefix.put("gif", 1);
//        filePrefix.put("png", 1);
//        filePrefix.put("bmp", 1);
//        filePrefix.put("webp", 1);
//        filePrefix.put("JPG", 1);
//        filePrefix.put("JPEG", 1);
//        filePrefix.put("GIF", 1);
//        filePrefix.put("PNG", 1);
//        filePrefix.put("BMP", 1);
//        filePrefix.put("WEBP", 1);
//    }

    /**
     * 初始化
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 返回<filter-name>元素的设置值。
        // System.out.println("getFilterName:" + filterConfig.getFilterName());
        // 返回FilterConfig对象中所包装的ServletContext对象的引用。
        // System.out.println("getServletContext:" + filterConfig.getServletContext());
        // 用于返回在web.xml文件中为Filter所设置的某个名称的初始化的参数值
        // System.out.println("getInitParameter:" + filterConfig.getInitParameter("cacheTimeout"));
        // 返回一个Enumeration集合对象。
        // System.out.println("getInitParameterNames:" + filterConfig.getInitParameterNames());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        // TODO 原想通过MultipartHttpServletRequest获取图片，但强转失败
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file = multipartRequest.getFile("editormd-image-file");
//        String fileName = file.getName();
//        if (filePrefix.containsKey(fileName.substring(fileName.lastIndexOf(".") + 1))) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//        response.setContentType("UTF-8");
//        ServletOutputStream ops = response.getOutputStream();
//        ops.print(JSON.toJSONString(ImageUploadVO.builder()
//                .success(0)
//                .message(CodeEnum.IMG_UPLOAD_FAIL.getMessage())
//                .build()));
//        ops.flush();
//        ops.close();
    }
}
