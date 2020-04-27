package com.jzh.myblog.util.file;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.UUID;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/4 17:47
 * @description 文件工具
 */
public class FileUtil {

    /**
     * 上传文件到阿里云OSS
     * @param file 文件流
     * @return 返回文件URL
     */
//    public String uploadFile(File file){
//        //初始化OSSClient
//        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();
//
//        String subCatalog ="articles/" + new TimeUtil().getFormatDateForThree();
//
//        String md5Key = AliYunOSSClientUtil.uploadObject2OSS(ossClient, file, OSSClientConstants.BACKET_NAME,
//                OSSClientConstants.FOLDER + subCatalog + "/");
//        String url = AliYunOSSClientUtil.getUrl(ossClient, md5Key);
//        String picUrl = "http://" + OSSClientConstants.BACKET_NAME + "." + OSSClientConstants.ENDPOINT +
//                "/" + OSSClientConstants.FOLDER + subCatalog + "/" + file.getName();
//
//        //删除临时生成的文件
//        File deleteFile = new File(file.toURI());
//        deleteFile.delete();
//
//        return picUrl;
//
//    }

    /**
     * base64字符转换成file
     *
     * @param destPath 保存的文件路径
     * @param base64   图片字符串
     * @param fileName 保存的文件名
     * @return file
     */
    public File base64ToFile(String destPath, String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath = destPath;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + "/" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 将file转换成base64字符串
     *
     * @param path
     * @return
     */
    public String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * MultipartFile类型文件转File
     *
     * @param multipartFile
     * @param filePath
     * @param fileName
     * @return File类型文件
     */
    public File multipartFileToFile(MultipartFile multipartFile, String filePath, String fileName) {
        File f = null;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        if ("".equals(multipartFile) || multipartFile.getSize() <= 0) {
            multipartFile = null;
        } else {
            try {
                InputStream ins = multipartFile.getInputStream();
                f = new File(filePath + fileName);
                OutputStream os = new FileOutputStream(f);
                int bytesRead = 0;
                int len = 8192;
                byte[] buffer = new byte[len];
                while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @throws Exception
     */
    public boolean exists(String filePath) {
        File targetFile = new File(filePath);
        return targetFile.exists();
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 文件重命名（UUID）
     *
     * @param fileName
     * @return
     */
    public String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 文件重命名（时间戳）
     *
     * @param fileName
     * @return
     */
    public String renameToDate(String fileName) {
        return System.currentTimeMillis() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 下载本地文件
     *
     * @param response
     * @param path     保存文件路径
     * @param fileName 文件名
     */
    public void downloadLocal(HttpServletResponse response, String path, String fileName) {
        FileInputStream fileIn = null;
        ServletOutputStream out = null;
        try {
            //String fileName = new String(fileNameString.getBytes("ISO8859-1"), "UTF-8");
            response.setContentType("application/octet-stream");
            // URLEncoder.encode(fileNameString, "UTF-8") 下载文件名为中文的，文件名需要经过url编码
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            File file;
            String filePathString = path + fileName;
            file = new File(filePathString);
            fileIn = new FileInputStream(file);
            out = response.getOutputStream();

            byte[] outputByte = new byte[1024];
            int readTmp = 0;
            while ((readTmp = fileIn.read(outputByte)) != -1) {
                //并不是每次都能读到1024个字节，所有用readTmp作为每次读取数据的长度，否则会出现文件损坏的错误
                out.write(outputByte, 0, readTmp);
            }
        } catch (Exception e) {
            //log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                fileIn.close();
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 下载网络文件
     *
     * @param fileUrl   文件下载地址
     * @param fileLocal 文件保存地址
     * @return
     * @throws Exception
     */
    public boolean downloadNet(String fileUrl, String fileLocal) throws Exception {
        boolean flag = false;
        URL url = new URL(fileUrl);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setConnectTimeout(6000);
        urlCon.setReadTimeout(6000);
        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("文件读取失败");
        }
        //读文件流
        DataInputStream in = new DataInputStream(urlCon.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileLocal));
        byte[] buffer = new byte[2048];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        flag = true;
        return flag;
    }
}
