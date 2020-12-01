package com.daliu.servlet;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class IdentityServlet extends HttpServlet {
    public static final char[] CHARS = {
            // 不包括0、O、1、I等难分辩字符
            '2', '3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q', 'R','X','T','U','V','W','X','Y','Z'
    };
    public static Random random = new Random();

    @Override
    protected long getLastModified(HttpServletRequest req) {
        return super.getLastModified(req);
    }

    /// 获取5位随机数
    public static String getRandomString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }

    /// 获取随机颜色
    public static Color getRandomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    /// 返回某颜色的反色
    public static Color getReversColor(Color c) {
        return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // https://www.jianshu.com/p/7362b08238fe
        // response.setDateHeader("Expires", 0);
        // response.setHeader("Cache-Control", "no-cache");
        // response.setHeader("Pragma", "no-cache");

        response.setContentType("image/jpeg");

        String randomString = getRandomString();
        // 放到session中
        // getSession(true)同getSession(): 获取request对象关联的session对象，如果没有session，则返回一个新的session
        // getSession(false) 也是返回一个request对象关联的session对象，但如果没有session，则返回null
        // request.getSession(true).setAttribute("randomString", randomString);
        int width = 100;
        int height = 30;
        Color color = getRandomColor();
        Color reverse = getReversColor(color);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        graphics2D.setColor(color);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(reverse);
        graphics2D.drawString(randomString, 18, 20);
        for (int i = 0, n = random.nextInt(100); i < n; i++) {
            // 画最多100个随机噪音点
            graphics2D.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
        }
        // response.getWriter()是字符输出流中的打印流，而此处要用字节输出流
        ServletOutputStream out = response.getOutputStream();
        // 在输出流上建立一个编码器，对BufferedImage进行编码
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(bufferedImage);
        out.flush();
        out.close();
    }
}
