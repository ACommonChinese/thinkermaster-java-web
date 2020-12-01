# 获取request的变量


```java
// -- com.daliu.servlet.RequestServlet.java --
package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Locale;

public class RequestServlet extends HttpServlet {
    private void writeLine(PrintWriter out, String key, String value) {
        out.println("<p>" + key + ": " + value + "</p>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        String authType         = request.getAuthType();
        String localAddr        = request.getLocalAddr();
        int localPort           = request.getLocalPort();
        Locale locale           = request.getLocale();
        String contextPath      = request.getContextPath(); // /demo
        String method           = request.getMethod();
        String pathInfo         = request.getPathInfo();
        String pathTranslate    = request.getPathTranslated();
        String protocol         = request.getProtocol();
        String queryString      = request.getQueryString();
        String remoteAddr       = request.getRemoteAddr();
        int remotePort          = request.getRemotePort();
        String remoteUser       = request.getRemoteUser();
        String requestSessionId = request.getRequestedSessionId();
        String requestURI       = request.getRequestURI(); // /demo/servlet/RequestServlet
        StringBuffer requestURL = request.getRequestURL(); // http://localhost:8080/demo/servlet/RequestServlet
        String scheme           = request.getScheme();
        String serverName       = request.getServerName();
        int serverPort          = request.getServerPort();
        String servletPath      = request.getServletPath(); // /servlet/RequestServlet
        Principal userPrincipal = request.getUserPrincipal();
        String accept           = request.getHeader("accept");
        String referer          = request.getHeader("referer");
        String userAgent        = request.getHeader("user-agent");
        String serverInfo       = this.getServletContext().getServerInfo();

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>Request servlet</title></head>");
        out.println("<body>");
        writeLine(out, "authType", authType);
        writeLine(out, "localAddr", localAddr);
        writeLine(out, "localPort", ""+localPort);
        writeLine(out, "locale", locale.toString());
        writeLine(out, "contextPath", contextPath);
        writeLine(out, "method", method);
        writeLine(out, "pathInfo", pathInfo);
        writeLine(out, "pathTranslate", pathTranslate);
        writeLine(out, "protocol", protocol);
        writeLine(out, "queryString", queryString);
        writeLine(out, "remoteAddr", remoteAddr);
        writeLine(out, "remotePort", remotePort+"");
        writeLine(out, "requestSessionId", requestSessionId);
        writeLine(out, "requestURI", requestURI);
        writeLine(out, "requestURL", requestURL.toString());
        writeLine(out, "scheme", scheme);
        writeLine(out, "serverName", serverName);
        writeLine(out, "serverPort", ""+serverPort);
        writeLine(out, "servletPath", servletPath);
        writeLine(out, "accept", accept);
        writeLine(out, "refer", referer);
        writeLine(out, "userAgent", userAgent);
        writeLine(out, "serverInfo", serverInfo);
        if (userPrincipal != null) {
            writeLine(out, "userPrincipal", userPrincipal.toString());
        } else {
            writeLine(out, "userPrincipal", "null");
        }
        
        out.println("<br /><br /><a href=" + requestURI + ">" + "单击刷新本页面" + "</a>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }
}
```

```xml
// WEB-INF/web.xml
<servlet>
    <servlet-name>RequestServlet</servlet-name>
    <servlet-class>com.daliu.servlet.RequestServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>RequestServlet</servlet-name>
    <url-pattern>/servlet/RequestServlet</url-pattern>
</servlet-mapping>
```

### 读取web.xml参数

参照：JavaWeb整合开发王者归来 P53

上面我们设置`response.setContentType("image/jpeg");`, 如果要增加其他ContentType就需要更改代码，像这种配置信息一般可以放在配置文件web.xml中。

下面编写一个程序，提示用户输入用户名和密码，如果验证通过转到notice.html上

```java
// -- com.daliu.servlet.InitParamServlet.java --
package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/*
获取web.xml中配置的initParam参数
 */
public class InitParamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>获取web.xml参数</title></head>");
        out.println("<style>body, td, div {font-size:12px;}</style>");
        out.println("<body>");

        out.println("<form action='" + request.getRequestURI() + "' method='post'>");
        out.println("帐号：<input type='text' name='username' style='width:200px; '/> <br/><br/>");
        out.println("密码：<input type='password' name='password' style='width:200px; '/> <br/><br/>");
        out.println("<input type='submit' value=' 登 录 ' />");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 获取web.xml中配置的initParam配置信息
        Enumeration params = this.getInitParameterNames();
        // getInitParameter("firstName")
        /**
         *  <init-param>
         *      <param-name>firstName</param-name>
         *      <param-value>firstValue</param-value>
         *  </init-param>
         */
        while (params.hasMoreElements()) { // 获取所有names
            String usernameParam = (String) params.nextElement();
            String passwordParam = this.getInitParameter(usernameParam);
            if (usernameParam.equalsIgnoreCase(username) && passwordParam.equalsIgnoreCase(password)) {
                // 跳转到/WEB-INF/notice.html
                request.getRequestDispatcher("/WEB-INF/notice.html").forward(request, response);
                return;
            }
        }
        // 如果username, password不匹配，显示登录页面
        this.doGet(request, response);
     }
}
```

```xml
<!-- WEB-INF/web.xml -->
...
<servlet-name>InitParamServlet</servlet-name>
<servlet-class>com.daliu.servlet.InitParamServlet</servlet-class>
<init-param>
    <param-name>firstName</param-name>
    <param-value>firstValue</param-value>
</init-param>
<init-param>
    <param-name>secondName</param-name>
    <param-value>secondValue</param-value>
</init-param>
<init-param>
    <param-name>thirdName</param-name>
    <param-value>thirdValue</param-value>
</init-param>
...
```

```html
<!-- WEB-INF/notice.html -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>Welcome to China!</h1>
</body>
</html>
```