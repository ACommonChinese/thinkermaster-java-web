# Forward

利用Servlet的跳转可以很容易把一项任务按模块分开。具体的Servet负责具体的事项。

Servlet跳转一般有：Forward、Redirect、Refresh三种方式, Servlet跳转也被称为请求转发  

### Forward

转发（Forward）是是一种在服务器内部的资源跳转方式, 通过RequestDispatcher对象的forward(HttpServletRequest req, HttpServletResponse res)方法来实现的。RequestDispatcher对象可以直接通过HttpServletRequest的getRequestDispatcher()方法获得  

```java
RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/HelloServlet");
dispatcher.forward(request, response);
```

`getRequestDispatcher()`方法的参数必须以"/"开始，"/"表示本Web应用程序, 假如配置了虚拟路径为/demo, 则此处的`getRequestDispatcher("/")`代表`/demo`

### 一个简单示例

```html
<!--regist.html-->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="/demo/registServlet" method="post">
        <!--其中demo是配置的虚拟路径-->
        <!--http://localhost:8080/demo/registServlet-->
        <input type="text" placeholder="输入用户名" name="username"><br>
        <input type="text" placeholder="输入密码" name="password"><br>
        <input type="submit" value="注册">
    </form>
</body>
</html>
```

**cn.daliu.servlet.RegistServlet.java**
```java
package cn.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registServlet")
public class RegistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/registServlet2").forward(request, response);
     }
}
```

**cn.daliu.servlet.RegistServlet2.java**
```java
package cn.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registServlet2")
public class RegistServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("go!!");
    }
}
```

### 再示例  

**com.daliu.servlet.ForwardServlet.java** 

```java
package com.daliu.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class ForwardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.setCommonHtml(request, response, out -> {
            String destination = request.getParameter("destination");
            System.out.println("destionation = " + destination);
            if ("file".equals(destination)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/web.xml");
                dispatcher.forward(request, response);
            }
            else if ("jsp".equals(destination)) {
                request.setAttribute("date", new Date());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forward.jsp");
                dispatcher.forward(request, response);
            }
            else if ("index".equals(destination)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }
            else if ("china".equals(destination)) {
                out.println("你好中国");
            }
            else {
                out.println("缺少参数，用法：" + request.getRequestURL() + "?destination=jsp|file|servlet");
            }
        });
    }

    @FunctionalInterface
    interface CommonHtml {
        public abstract void doIt(PrintWriter out) throws ServletException, IOException;
    }

    void setCommonHtml(HttpServletRequest req, HttpServletResponse resp, CommonHtml doIt) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.write("<meta http-equiv='content-type' content='text/html;charset=UTF-8'/>");
        out.println("<title>This is title</title>");
        out.println("</head>");
        out.println("<body>");
        if (doIt != null) {
            doIt.doIt(out);
        }
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }
}
```

**forward.jsp**  
```jsp
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%
    Date date = (Date)request.getAttribute("date");
%>
<html>
<head>
    <title>Forward跳转</title>
</head>
<body>
    <p>从ForwardServlet中取到的Date为</p><span><%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(date) %></span>
    <div align="center">
        <input type="button" onclick='location="<%= request.getContextPath() %>/servlet/forwardServlet?destination=index"; ' value="跳转到index" />
        <input type="button" onclick='location="<%= request.getContextPath() %>/servlet/forwardServlet?destination=file"; ' value="跳转到web.xml" />
        <input type="button" onclick='location="<%= request.getContextPath() %>/servlet/forwardServlet?destination=jsp"; ' value="跳转到JSP" />
    </div>
</body>
</html>
```

**WEB-INF/web.xml** 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
		  http://java.sun.com/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
    <servlet>
        <servlet-name>forwardServlet</servlet-name>
        <servlet-class>com.daliu.servlet.ForwardServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>forwardServlet</servlet-name>
        <url-pattern>/servlet/forwardServlet</url-pattern>
    </servlet-mapping>
</web-app>
```

### Forward的特点
1. 转发时浏览器地址栏不发生变化
2. 只能转发到当前服务器内部资源
3. 只有一次请求, 无论Forward转发多少次, 依然使用的同一个请求

### 共享数据
- 域对象: 一个有作用范围的对象, 可以在范围内共享数据
- request域: 代表一次请求, A servlet Forward转发到 B servlet, 这两个servlet是基于同一个request域
- request域一般用于请求转发的多个资源中共享数据  
- 方法: 
  - void setAttribute(String name, Object obj) // Stores an attribute in this request. Attributes are reset between requests. This method is most often used in conjunction with RequestDispatcher.
  - Object getAttribute(String name)
  - void removeAttribute(String name)

```java
@WebServlet("/registServlet2")
public class RegistServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("go servlet 2");
        request.setAttribute("message", "Hello, I'm RegistServlet2");
        request.getRequestDispatcher("/registServlet3").forward(request, response);
    }
}

@WebServlet("/registServlet3")
public class RegistServlet3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("go servlet 3");
        String message = (String)request.getAttribute("message");
        System.out.println(message);
    }
}
```