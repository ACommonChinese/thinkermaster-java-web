# Session示例

示例需求: 
1. 访问带有验证码的登录页面login.jsp
2. 用户输入用户名, 密码以及验证码
  - 如果输入有误, 跳转到登录页面, 并提示: 用户名或密码错误
  - 如果验证码输入有误, 跳转到登录页面, 提示: 验证码错误
  - 如果输入正确, 跳转到success.jsp, 显示: 用永久名, 欢迎您

伪代码: 
由于生成验证码是一个servlet, 而请求登录又是一个servlet, 在两个请求之间, 从session中获取程序生成的验证码
```java
if (验证码不正确) {
	提示用户
	跳转到登录页, forward
} else {
	if (用户名和密码正确) {
		登录成功, 存储数据
		跳转到success.jsp, redirect
	} else {
		提示信息
		跳转到登录页面, forward
	}
}
```

**web/login.jsp**  

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>

    <script>
        window.onload = function () {
            document.getElementById("img").onclick = function () {
                this.src = "/loginDemo/checkCodeServlet?time=" + new Date().getTime();
            };
        }
    </script>

    <style>
        .red {
            color: red;
        }
    </style>
</head>
<body>
    <form action="/loginDemo/loginServlet" method="post">
        <table align="center">
            <tr>
                <td>用户名</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>密码</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td>验证码</td>
                <td><input type="text" name="checkCode"></td>
            </tr>
            <tr>
                <td colspan="2"><img id="img" src="/loginDemo/checkCodeServlet"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="登 录"></td>
            </tr>
        </table>
    </form>
    <div id="checkCodeError" class="red"><%=request.getAttribute("checkCodeError") == null ? "" : request.getAttribute("checkCodeError") %></div>
    <div id="loginError" class="red"><%=request.getAttribute("loginError") == null ? "" : request.getAttribute("loginError") %></div>
</body>
</html>
```

**web/success.jsp**

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录成功页面</title>
</head>
<body>
    <h1><%=request.getSession().getAttribute("user")%>, 欢迎您</h1>
</body>
</html>
```

**com.daliu.sevlet.CheckCodeServlet.java** 

```java
package com.daliu.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 100;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.PINK);
        graphics.fillRect(0, 0, width, height);

        // 画边框
        graphics.setColor(Color.BLUE);
        graphics.drawRect(0, 0, width-1, height-1);

        // 画验证码
        String randomStr = getRandomString();
        System.out.println("随机字符串: " + randomStr);
        String drawStr = getDrawStr(randomStr);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
        drawCenteredString(graphics, drawStr, new Rectangle(width, height), font);
        request.getSession().setAttribute("checkCodeInSession", randomStr);

        // 画干扰线
        graphics.setColor(Color.GREEN);
        for (int i = 0; i < 10; i++) {
            int[] randomPoints = getRandomPoints(width, height);
            graphics.drawLine(randomPoints[0], randomPoints[1], randomPoints[2], randomPoints[3]);
        }

        // 把内存中的图片对象写入流中
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    // 随机字符串
    private String getRandomString() {
        String str = "ABCDEFGHJKLMNOPQRSTUVWXYZabcdefhijkmnpqrstuvwxyz2345678";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            builder.append(String.valueOf(c));
        }
        return builder.toString();
    }

    private String getDrawStr(String randomStr) {
        if (randomStr == null || randomStr.length() <= 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < randomStr.length()-1; i++) {
            builder.append(String.valueOf(randomStr.charAt(i)));
            builder.append(" ");
        }
        builder.append(randomStr.charAt(randomStr.length() - 1));
        String drawStr = builder.toString();
        System.out.println("要画的字符串: " + drawStr);
        return drawStr;
    }

    // 随机点
    private int[] getRandomPoints(int width, int height) {
        Random random = new Random();
        int x1 = random.nextInt(width);
        int y1 = random.nextInt(height);
        int x2 = random.nextInt(width);
        int y2 = random.nextInt(height);
        return new int[]{x1, y1, x2, y2};
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
```

**com.daliu.servlet.LoginServlet.java**  
```java
package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // Map<String, String[]> map = request.getParameterMap();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String inputCheckCode = request.getParameter("checkCode");

        HttpSession session = request.getSession();
        // 判断验证码是否正确
        String checkCode = (String) session.getAttribute("checkCodeInSession");
        // 删除session中存储的验证码, 让session中存储的验证码只能使用一次
        session.removeAttribute("checkCodeInSession");
        System.out.println("用户输入的验证码: " + inputCheckCode);
        System.out.println("session中的验证码: " + checkCode);
        if (inputCheckCode == null || inputCheckCode.equalsIgnoreCase(checkCode) == false) {
            request.setAttribute("checkCodeError", "验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            // TODO://需查询数据库
            if (username.equals("daliu") && password.equals("111")) {
                // TODO://存储用户信息到数据库
                session.setAttribute("user", username);
                // 重定向到success.jsp
                // response.sendRedirect("/loginDemo/success.jsp");
                response.sendRedirect(request.getContextPath() + "/success.jsp");
            } else {
                // 登录失败
                request.setAttribute("loginError", "用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```

