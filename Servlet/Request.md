# Request

```java
ServletRequest 接口
     |
     | 继承 
     |
HttpServletRequest 接口
     |
     | 实现
     |
org.apache.catalina.connector.RequestFacade 类(tomcat)
```

### 获取请求行数据
- GET /demo/login?name=xxx HTTP/1.1
    - 获取请求方式GET:  String getMethod()
    - 获取虚拟目录: /demo: String getContextPath()
    - 获取Servlet请求路径: /login: String getServletPath()
    - 获取请求URI: /demo/login: String getRequestURI()
    - 获取请求URL:  http://localhost/demo/login: StringBuffer getRequestURL()
    - 获取get方式的请求参数: name=xxx: String getQueryString()
    - 获取协议及版本: HTTP/1.1: String getProtocol()
    - 获取客户机ip: String getRemoteAddr()

示例: 

```java
package cn.daliu.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/daliu")
public class DaliuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("=========== 请求行数据 ===========");
        // http://localhost:8080/demo/daliu?name=xxx
        System.out.println(request.getMethod());        // GET
        System.out.println(request.getContextPath());   // /demo
        System.out.println(request.getServletPath());   // /daliu
        System.out.println(request.getQueryString());   // name=xxx
        System.out.println(request.getRequestURI());    // /demo/daliu
        System.out.println(request.getRequestURL());    // http://localhost:8080/demo/daliu
        System.out.println(request.getProtocol());      // HTTP/1.1
        System.out.println(request.getRemoteAddr());    // 0:0:0:0:0:0:0:1
    }
}
```

### 获取请求头数据  
- `String getHeader(String name)`: 通过请求头名称获取请求头值
- `Enumeration<String> getHeaderNames()`: 获取所有的请求头名称

```java
private void logHeader(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("=========== 请求头数据 ===========");
    // http://localhost:8080/demo/daliu?name=xxx
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
        String name = headerNames.nextElement();
        System.out.println(name + " ----> " + request.getHeader(name)); // getHeader参数name不区分大小写
    }
    //=========== 请求头数据 ===========
    //host ----> localhost:8080
    //connection ----> keep-alive
    //cache-control ----> max-age=0
    //upgrade-insecure-requests ----> 1
    //user-agent ----> Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36
    //sec-fetch-dest ----> document
    //accept ----> text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
    //sec-fetch-site ----> none
    //sec-fetch-mode ----> navigate
    //sec-fetch-user ----> ?1
    //accept-encoding ----> gzip, deflate, br
    //accept-language ----> en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7
    //cookie ----> JSESSIONID=F36E8E960C77BAB5807CC1DC712CE86B; Idea-362fdedf=8bf84632-20ec-4f72-817a-cbc8c5085084; Idea-362fdee0=9058b556-e54a-4487-817f-633013f38268; Idea-362fe663=eead7c48-5362-4353-bf85-ea137cfa082c; JSESSIONID=5134464F1ED4D641588859617D60226E
}
```

### 获取请求体数据   
- POST请求有请求体, GET请求没有, POST请求体中封装了请求参数  
- 步骤: 
  1. 获取流对象 
    - BufferedReader getReader(): 获取字符输入流, 只能操作字符数据
    - ServletInputStream getInputStream(): 获取字节输入流, 可以操作所有类型数据, ServletInputStream继承于InputStream, 可以处理如文件上传的字节数据
  2. 再从流对象中拿数据

```xml
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <a href="/demo/daliu">Click me to /daliu</a>
</body>
</html>
```

```java
package cn.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/registServlet")
public class RegistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // username=daliu&password=123456
        }
    }
}
```

### 获取请求参数通用方式
  - String getParameter(String name): 根据参数名称获取参数值
  - String[] getParameterValues(String name): 根据参数名称获取参数值数组 hobby=learn&hobby=game
  - Enumeration<String> getParameterNames(): 获取所有请求的参数名称
  - Map<String, String[]> getParameterMap(): 获取所有参数的键值对集合

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="/demo/registServlet" method="post">
        <input type="text" placeholder="输入用户名" name="username"><br>
        <input type="text" placeholder="输入密码" name="password"><br>
        <input type="submit" value="注册">

        <input type="checkbox" name="hobby" value="study">学习
        <input type="checkbox" name="hobby" value="game">游戏
        <input type="checkbox" name="hobby" value="pingpang">乒乓
        <input type="checkbox" name="hobby" value="basketball">篮球
    </form>
</body>
</html>
```

```java
package cn.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@WebServlet("/registServlet")
public class RegistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // For HTTP servlets, parameters are contained in the query string or posted form data.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.getWriter().println(username);
        response.getWriter().println(password);

        String[] hobbies = request.getParameterValues("hobby");
        for (String hobby : hobbies) {
            response.getWriter().println(hobby);
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            System.out.println(name + " --> " + request.getParameter(name));
            System.out.println("------------------------");
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> keySet = parameterMap.keySet();
        for (String name : keySet) {
            String[] values = parameterMap.get(name);
            for (String value : values) {
                System.out.println(name + " ===> " + value);
            }
            System.out.println("--------------------------");
        }
     }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.getWriter().println(username);
        response.getWriter().println(password);
    }
}
```

### Request和中文乱码问题 

- get方式: tomcat8已将get方式中文乱码问题解决
- post方式: 会乱码, 虽然通过getParameter获取数据, 但内部依然是通过流来获取参数的值, 需要手动设置流的编码

```java
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset='UTF-8'");
```