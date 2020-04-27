package com.jzh.myblog.util.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/15 16:59
 * @description
 */
public class ImageConverUtil {

    /**
     * 图片转输入流
     *
     * @param image
     * @param prefix
     * @return
     */
    public static InputStream imageToInputStream(BufferedImage image, String prefix) {
        return new ByteArrayInputStream(imageToBytes(image,  prefix));
    }

    /**
     * 图片转字节
     *
     * @param image
     * @param prefix
     * @return
     */
    public static byte[] imageToBytes(BufferedImage image, String prefix) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, prefix, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
