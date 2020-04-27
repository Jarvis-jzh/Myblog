package com.jzh.myblog.util.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.jzh.myblog.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/25 10:31
 * @description 阿里云OSS连接工具
 */
@Component
@Slf4j(topic = "aliyun-oss")
public class AliyunOssUtil {

    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    /**
     * 上传文件
     *
     * @param fileName    文件名 默认是uuid生成
     * @param inputStream 输入流
     * @param folder      文件夹名字，默认是File
     */
    public String upLoad(String fileName, InputStream inputStream, FolderNameEnum folder, boolean isUseOldName) {

        String endpoint = aliyunOssProperties.getEndpoint();
        String accessKeyId = aliyunOssProperties.getAccessId();
        String accessKeySecret = aliyunOssProperties.getAccessKey();
        String bucketName = aliyunOssProperties.getBucket();
        String url = aliyunOssProperties.getUrl();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");

        /**
         * 创建OSS客户端
         */
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            String uuid = UUID.randomUUID().toString();
            String[] names = fileName.split("[.]");
            String name = null;
            if (isUseOldName) {
                name = fileName;
            } else {
                name = uuid + "." + names[names.length - 1];
            }
            String path = folder.getName() + "/" + format.format(new Date()) + "/" + name;
            oss.putObject(new PutObjectRequest(bucketName, path, inputStream));
            return url + "/" + path;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException("文件上传失败");
        } finally {
            oss.shutdown();
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 上传文件
     *
     * @param fileName      文件名
     * @param inputStream   输入流
     * @return              文件保存地址
     */
    public String upLoad(String fileName, InputStream inputStream) {
        return this.upLoad(fileName, inputStream, FolderNameEnum.FILE,false);
    }

    /**
     * 上传文件
     *
     * @param fileName      文件名
     * @param inputStream   输入流
     * @param folder        保存在哪个文件夹下
     * @return
     */
    public String upLoad(String fileName, InputStream inputStream, FolderNameEnum folder) {
        return this.upLoad(fileName, inputStream, folder, false);
    }


    public String upLoad(File file) {
        try {
            return this.upLoad(file.getName(), new FileInputStream(file), FolderNameEnum.FILE,false);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new CommonException("文件上传失败");
        }
    }

    public String upLoad(File file, FolderNameEnum folder) {
        try {
            return this.upLoad(file.getName(), new FileInputStream(file), folder,false);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new CommonException("文件上传失败");
        }
    }

    public String upLoad(String fileName, File file) {
        return this.upLoad(fileName, file, FolderNameEnum.FILE);
    }

    public String upLoad(String fileName, File file, FolderNameEnum folder) {
        try {
            return this.upLoad(fileName, new FileInputStream(file), folder,false);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new CommonException("文件上传失败");
        }
    }


}