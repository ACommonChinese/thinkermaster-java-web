# Cookie

Cookie使用大致流程:

浏览器第一次向服务器发送请求, 由服务器生成Cookie, 绑定数据发送给浏览器
浏览器再次向服务器发送请求, 会附带上服务器颁发的Cookie, 服务器接收到Cookie后拿出Cookie中的数据使用  

服务器要做的事:
1. 创建Cookie, 绑定数据: new Cookie(String name, String value)
2. 发送Cookie对象: response.addCookie(Cookie cookie)
3. 获取Cookie对象: Cookie[] request.getCookies()

示例: 

**com.daliu.servlet.CookieDemoServlet**

```java
package com.daliu.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import java.io.IOException;

@WebServlet("/cookieDemoServlet")
public class CookieDemoServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Cookie cookie = new Cookie("message", "hello");
        cookie.setVersion(1);
        response.addCookie(cookie);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.doPost(request, response);
    }
}
```

**com.daliu.servlet.**
```java
package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cookieDemoServlet2")
public class CookieDemoServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                System.out.println(name + " : " + value);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```

当服务器调用`response.addCookie(cookie)`时,  这导致发给浏览器的 Response Headers有:
- set-cookie: message=hello
浏览器看到了set-cookie这个响应头的东西就会存储在本地, 浏览器再次请求时, 其请求头上会带上:
- cookie:msg=hello 
这就是底层使用Cookie的机制, 即实现原理是基于响应头set-cookie和请求头cookie 
