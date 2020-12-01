# Redirect

重定向是利用服务器返回的状态码来实现的。服务器通过HttpServletResponse的`setStatus(int status)`方法设置状态码。  
如果服务器返回301或302， 则浏览器会到新的网址重新请求该资源。  

| 状态码  | 意义  |
| :------------ |:---------------|
| 1xx      | 请求已被接受，正在被处理 |
| 2xx      | 正确状态码，表示该请求已经被正确接受并处理，未发生错误。200表示一切正确        |
| 3xx      | 重定向状态码，301，302表示该资源已不存在或换了地址，客户端需要重新定向到一个新的资源。服务器响应中会附带这个资源地址        |
| 4xx      | 请求错误。401：没有访问权限，404：资源不存在 405：访问方式错误        |
| 5xx      | 服务器错误，例如500表示程序出现异常而中途停止运行        |

301、302都表示重定向，其中301表示永久性重定向，302表示临时重定向。下面代码将访问该Servlet的请求重定向到另一个网址 

```java
// int SC_MOVED_PERMANENTLY = 301;
// int SC_MOVED_TEMPORARILY = 302;
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
response.setHeader("Location", "http://www.helloweenvsfei.com");
```

设置重定向也可以直接使用 `httpServletResponse.sendRedirect(path)`的方式处理。  

示例：  

**com.daliu.servlet.RedirectServlet.java**

```java
package com.daliu.servlet;

import com.daliu.util.CommonHtml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RedirectServlet extends HttpServlet {
    Map<String, Integer> map = new HashMap<>();

    @Override
    public void init() throws ServletException {
        map.put("/download/1.txt", 0);
        map.put("/download/3.zip", 0);
        map.put("/download/2.png", 0);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("filename");
        if (fileName != null) {
            int hit = map.get(fileName); // 统计下载次数
            map.put(fileName, ++hit);
            String path = request.getContextPath() + fileName;
            System.out.println("重定向到：" + path);

            // http://localhost:8080/demo/servlet/redirectServlet
            // System.out.println("request.url " + request.getRequestURL());

            // /demo/servlet/redirectServlet
            // System.out.println("request.uri: " + request.getRequestURI());

            /**
             * 重定向到：/demo/download/1.txt
             * 重定向到：/demo/download/2.png
             * 重定向到：/demo/download/3.zip
             */
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", path);
            // 上面两句也可以使用下面一句代替：
            // response.sendRedirect(path);
        }
        else {
            new CommonHtml().setCommonHtml(request, response, out -> {
                out.println("<fieldset align=center style=width:90%><legend>文件下载</legend>");
                out.println("<table width=100%>");

                out.println("   <tr>");
                out.println("       <td><b>文件名</b></td>");
                out.println("       <td><b>下载次数</b></td>");
                out.println("       <td><b>下载</b></td>");
                out.println("   </tr>");

                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    String href = "<a href='" + request.getRequestURI() + "?filename=" + key + "' target=' blank' onclick='location=location; '>下载</a>";
                    out.println("<tr>");
                    out.println("   <td>" + key + "</td>");
                    out.println("   <td>" + value + "</td>");
                    out.println("   <td>" + href + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            });
        }
    }

    @Override
    public void destroy() {
        map = null;
    }
}
```

**注：** 当使用Chrome打开 `http://localhost:8080/demo/servlet/redirectServlet` 并点载下载时，由于进行了重定向，而Chrome默认是关闭此选项的，需要手动打开允许“弹出式窗口和重定向” 

------------------------------------------

### 示例2:  

**com.daliu.servlet.RedirectServlet.java** 

```java
package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/redirectServlet1")
public class RedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("demo1被访问"); // 这个并不会被显示在浏览器中

        // response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY); // SC_MOVED_TEMPORARILY值为302
        // response.setHeader("location", "/redirectDemo/redirectServlet2");
        // 这一行可以代替上面两行
        response.sendRedirect("/redirectDemo/redirectServlet2");
    }
}
```

**com.daliu.RedirectServlet2.java**

```java
package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/redirectServlet2")
public class RedirectServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("demo2被访问");
    }
}
```

-------------------------------------------------------

### 重定向注意事项 

路径问题  
在forward转发时不需要写虚拟目录, 但重定向需要: `response.sendRedirect("/redirectDemo/redirectServlet2");`   

转发forward的特点:  
1. 转发地址栏路径不变
2. 转发只能访问当前服务器下的资源
3. 转发是一次请求, 可以使用request域对象共享数据

重定向redirect的特点: 
1. 地址栏发生变化
2. 可以访问其他站点(服务器)的资源
3. 重定向是两次请求, 第一次请求得到一个302和新资源路径, 第二次请求新资源, 不能使用request域对象共享数据


