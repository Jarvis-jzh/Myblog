package com.jzh.myblog.util.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author jzh
 * @version 1.0
 * @date 2019/9/26 14:25
 * @description 图片水印工具类
 */
public class ImageRemarkUtil {

    /**
     * 水印透明度
     */
    private static float alpha = 0.5f;

    /**
     * 水印文字大小
     */
    public static final int FONT_SIZE = 28;

    /**
     * 水印文字字体
     */
    private static Font font = new Font("微软雅黑", Font.BOLD, FONT_SIZE);

    /**
     * 水印横向位置
     */
    private static int positionWidth = 150;

    /**
     * 水印纵向位置
     */
    private static int positionHeight = 300;

    /**
     * 水印文字颜色
     */
    private static Color color = Color.red;

    /**
     * 水印之间的间隔（X轴）
     */
    private static final int XMOVE = 80;

    /**
     * 水印之间的间隔（Y轴）
     */
    private static final int YMOVE = 80;

    /**
     * 获取文本长度。汉字为1:1，英文和数字为2:1
     */
    private int getTextLength(String text) {
        int length = text.length();
        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                length++;
            }
        }
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }

    /**
     * 修改水印配置
     *
     * @param alpha          水印透明度
     * @param positionWidth  水印横向位置
     * @param positionHeight 水印纵向位置
     * @param font           水印文字字体
     * @param fontSize       水印文字大小
     * @param color          水印文字颜色
     */
    public void setImageMarkOptions(float alpha, int positionWidth,
                                           int positionHeight, Font font, int fontSize, Color color) {
        if (alpha != 0.0f) {
            ImageRemarkUtil.alpha = alpha;
        }
        if (positionWidth != 0) {
            ImageRemarkUtil.positionWidth = positionWidth;
        }
        if (positionHeight != 0) {
            ImageRemarkUtil.positionHeight = positionHeight;
        }
        if (font != null) {
            ImageRemarkUtil.font = font;
        }
        if (fontSize != 0) {
            ImageRemarkUtil.font = font;
        }
        if (color != null) {
            ImageRemarkUtil.color = color;
        }
    }

    /**
     * 给图片添加水印图片
     *
     * @param iconPath   水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public void markImageByIcon(String iconPath, String srcImgPath,
                                       String targerPath) {
        markImageByIcon(iconPath, srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     *
     * @param iconPath   水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param degree     水印图片旋转角度
     */
    public void markImageByIcon(String iconPath, String srcImgPath,
                                       String targerPath, Integer degree) {
        OutputStream os = null;
        try {

            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            // 3、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }

            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            // 5、得到Image对象。
            Image img = imgIcon.getImage();

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            // 6、水印图片的位置
//            g.drawImage(img, positionWidth, positionHeight, null);

            // 原图宽度
            int width = srcImg.getWidth(null);
            // 原图高度
            int height = srcImg.getHeight(null);
            // 水印宽度
            int markWidth = img.getWidth(null);
            // 水印高度
            int markHeight = img.getHeight(null);
            int x = -width / 2;
            int y = -height / 2;
            // 循环添加水印
            while (x < width * 1.5) {
                y = -height / 2;
                while (y < height * 1.5) {
                    g.drawImage(img, x, y, null);

                    y += markHeight + YMOVE;
                }
                x += markWidth + XMOVE;
            }
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();

            // 8、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);

//            System.out.println("图片完成添加水印图片");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给图片添加水印文字
     *
     * @param logoText      水印文字
     * @param inputStream   图片输入流
     */
    public BufferedImage markImageByText(String logoText, InputStream inputStream) {
        return markImageByText(logoText, inputStream, null);
    }

    /**
     * 给图片添加水印文字
     *
     * @param logoText      水印文字
     * @param inputStream   图片输入流
     * @param degree        设置水印旋转(为null则水印在图片中央，反之循环打印)
     */
    public BufferedImage markImageByText(String logoText, InputStream inputStream, Integer degree) {
        try{
            BufferedImage image = ImageIO.read(inputStream);
            // 原图宽度
            int width = image.getWidth(null);
            // 原图高度
            int height = image.getHeight(null);
            Graphics2D g2 = (Graphics2D) image.getGraphics();
            // 设置对线段的锯齿状边缘处理
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(
                    image.getScaledInstance(width,
                            height, Image.SCALE_SMOOTH), 0, 0,
                    null);
            g2.setColor(Color.getColor("bdc1c6"));
            g2.setFont(font);
            // 设置水印文字透明度
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 字体长度
            int markWidth = FONT_SIZE * getTextLength(logoText);
            // 字体高度
            int markHeight = FONT_SIZE;
            // 设置水印旋转
            if (null != degree) {
                g2.rotate(Math.toRadians(degree),
                        (double) image.getWidth() / 2,
                        (double) image.getHeight() / 2);
                int x = -width / 2;
                int y = -height / 2;
                // 循环添加水印
                while (x < width * 1.5) {
                    y = -height / 2;
                    while (y < height * 1.5) {
                        g2.drawString(logoText, x, y);

                        y += markHeight + YMOVE;
                    }
                    x += markWidth + XMOVE;
                }
            } else {
                // 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
                g2.drawString(logoText, (width - markWidth) / 2, (height - markHeight) / 2);
            }
            // 释放资源
            g2.dispose();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 给图片添加水印文字
     *
     * @param logoText   水印文字
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public void markImageByText(String logoText, String srcImgPath,
                                       String targerPath) {
        markImageByText(logoText, srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param logoText   水印文字
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param degree     设置水印旋转
     */
    public void markImageByText(String logoText, String srcImgPath,
                                       String targerPath, Integer degree) {

        InputStream is = null;
        OutputStream os = null;
        try {
            // 1、源图片
            Image srcImg = ImageIO.read(new File(srcImgPath));
            // 原图宽度
            int width = srcImg.getWidth(null);
            // 原图高度
            int height = srcImg.getHeight(null);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
//            g.drawString(logoText, positionWidth, positionHeight);
            int x = -width / 2;
            int y = -height / 2;
            // 字体长度
            int markWidth = FONT_SIZE * getTextLength(logoText);
            // 字体高度
            int markHeight = FONT_SIZE;

            // 循环添加水印
            while (x < width * 1.5) {
                y = -height / 2;
                while (y < height * 1.5) {
                    g.drawString(logoText, x, y);

                    y += markHeight + YMOVE;
                }
                x += markWidth + XMOVE;
            }

            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加水印文字");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
