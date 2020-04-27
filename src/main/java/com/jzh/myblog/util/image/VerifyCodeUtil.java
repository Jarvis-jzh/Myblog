package com.jzh.myblog.util.image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/15 12:05
 * @description 生成验证码图片
 */
public class VerifyCodeUtil {
    /**
     * 宽
     */
    private int width = 70;

    /**
     * 高
     */
    private int height = 25;

    /**
     * 验证码随机数
     */
    private Random random = new Random();

    /**
     * 随机字体：宋体，华文楷书，黑体，华文新魏，华文隶书，微软雅黑，楷体_GB2312,Times New Roman
     */
    private String[] font = {"宋体", "华文楷书", "黑体", "华文新魏", "微软雅黑", "华文隶书", "楷体_GB2312", "Times New Roman"};

    /**
     * 随机字符：可选的字符
     */
    private String codes = "23456789abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";

    /**
     * 背景色
     */
    private Color bgColor = new Color(255, 255, 255);

    /**
     * 干扰线数量
     */
    private Integer lineNum = 4;

    /**
     * 验证码
     */
    private String code;

    /**
     * 更改验证码配置
     *
     * @param width  宽
     * @param height 高
     * @param font   字体数组
     * @param codes  背景色
     */
    public void setOptions(int width, int height, String[] font, Color codes, Integer lineNum) {
        this.width = width;
        this.height = height;
        this.font = font;
        this.bgColor = codes;
        this.lineNum = lineNum;
    }

    /**
     * 获取验证码（用于上传）
     *
     * @param rotate    是否旋转
     * @return
     */
    public BufferedImage getImageInputStream(boolean rotate) {
        // 获取一个画布
        BufferedImage image = createImage();
        // 获取画笔
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        // 用来装载生成的验证码文本
        StringBuffer sb = new StringBuffer();
        // 向图中画4个字符
        for (int i = 0; i < 4; i++) {
            String s = randomChar() + "";
            sb.append(s);
            int x = i * width / 4 + 5;
            int y = height - 5;
//            System.out.println("x => " + x + ", y => " + y);
            g2.setFont(randomFont());
            g2.setColor(randomColor());
            if (rotate) {
                Integer degree = randomDegree();
//                System.out.println("degree => " + degree + ", radians => " + Math.toRadians(degree));
                AffineTransform at = new AffineTransform();
                at.setToRotation(Math.toRadians(degree), x, y);
                g2.setTransform(at);
//            g2.rotate(Math.toRadians(degree), x, y);
            }
            g2.drawString(s, x, y);
        }
        // 画干扰线
        drawLine(image);
        // 释放资源
        g2.dispose();
        this.code = sb.toString();
        return image;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 生成随机颜色
     *
     * @return 颜色
     */
    private Color randomColor() {
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);
        return new Color(red, green, blue);
    }

    /**
     * 生成随机字体
     *
     * @return 字体
     */
    private Font randomFont() {
        // 获取随机字体下标
        int index = random.nextInt(font.length);
        // 获取字体
        String fontName = font[index];
        // 获取随机样式，0表无样式，1表粗体，2表斜体，3表粗体加斜体
        int style = random.nextInt(4);
        // 获取字体大小，20-24
        int size = random.nextInt(5) + 20;
        return new Font(fontName, style, size);
    }

    /**
     * 画干扰线
     *
     * @param image 图片
     */
    private void drawLine(BufferedImage image) {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        // 默认画4条
        for (int i = 0; i < lineNum; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            // 设置笔画的宽度
            g2.setStroke(new BasicStroke(1.5F));
            // 干扰线颜色
            g2.setColor(randomColor());
            // 画线
            g2.drawLine(x1, y1, x2, y2);
        }
        g2.dispose();
    }

    /**
     * 创建一个空白的画布，用于画验证码
     *
     * @return 空白画布
     */
    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.bgColor);
        g2.fillRect(0, 0, width, height);
        g2.dispose();
        return image;
    }

    /**
     * 创建一个字符的画布
     *
     * @return
     */
    private BufferedImage createCharImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.bgColor);
        g2.fillRect(0, 0, width / 4, height);
        g2.dispose();
        return image;
    }

    /**
     * 生成随机字符
     *
     * @return 字符
     */
    private char randomChar() {
        int index = random.nextInt(codes.length());
        return codes.charAt(index);
    }

    /**
     * 生成随机旋转度数
     *
     * @return
     */
    private Integer randomDegree() {
        return random.nextInt(61) - 30;
    }
}
