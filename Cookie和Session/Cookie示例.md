# Cookie示例

需求: 
1. 第一次访问提示: 您好, 欢迎首次访问
2. 如果不是第一次访问, 提示: 欢迎回来, 您上次访问时间为: xxx  

```java
package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/cookieDemo")
public class CookieDemo extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("hello");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        Cookie[] cookies = request.getCookies();
        boolean flag = false;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("lastTime")) {
                    flag = true;
                    String value = cookie.getValue();
                    String decodeValue = URLDecoder.decode(value, "utf-8");
                    System.out.println(decodeValue);
                    response.getWriter().write("<h1>欢迎回来, 您上次访问时间为: " + decodeValue + "</h1>");

                    refreshCookie(response);

                    break;
                }
            }
        }
        if (flag == false) {
            response.getWriter().println("<h1>欢迎首次访问" + "</h1>");
            refreshCookie(response);
        }
    }

    private String refreshCookie(HttpServletResponse response) throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String dateString = dateFormat.format(date);
        // 如果不转码, cookie.setValue(dateString) Crash: An invalid character [32] was present in the Cookie value
        // 32是空格的ASCII
        System.out.println("转码前: " + dateString);
        String encodedStr = URLEncoder.encode(dateString, "utf-8");
        Cookie cookie = new Cookie("lastTime", encodedStr);
        cookie.setValue(encodedStr);
        cookie.setMaxAge(60*60*24*30); // 一个月
        response.addCookie(cookie);
        return dateString;
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```