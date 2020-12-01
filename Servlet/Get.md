# Get

参见：Java Web整合开发王者归来p60

HTML中使用FORM提交数据，FORM的method决定了使用GET还是POST, FORM的action决定把数据提交到哪个URL.  

以GET方式提交表单数据时，所有被提交的内容都将以key_1=value_1&key_2=value_2的格式显示在浏览器中，下面使用GET请求做一个简单的搜索引擎：  

参见：`ServletDemo/SearchServlet`

![](images/1.png)

```java
// -- com.daliu.servlet.SearchServlet.java --
package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	// request.getRequestDispatcher("/WEB-INF/content/welcome.html").forward(request, response);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        String type = request.getParameter("type"); // 搜索类型
        String allowAdult = request.getParameter("allowedAdult");
        boolean adultOk = "true".equals(allowAdult);
        String word = request.getParameter("word"); // 搜索关键字
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>Request servlet</title></head>");
        out.println("<body>");
        out.println("搜索类型：" + type + " <br />");
        if (adultOk) {
            out.println("允许成人 <br />");
        }
        else {
            out.println("不允许成人 <br />");
        }
        out.println("搜索关键字：" + word);
        out.println("</body>");
        out.println("</html>");
    }
}
```

```html
// -- search.html --
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>serch.html</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <style>
        div, body, span {
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div align="center">
        <img src="http://localhost:8080/demo/yahoo.png" style="margin:25px;">
        <div>
            <!--注意这个地方action不可以写成/servlet/SearchServlet-->
            <form action="servlet/SearchServlet" method="get">
                <input type="radio" name="type" value="web" checked />网页
                <input type="radio" name="type" value="news" />新闻
                <input type="radio" name="type" value="image" />图片
                <input type="radio" name="type" value="video" />视频 &nbsp;&nbsp;
                <input type="checkbox" name="allowedAdult" value="true" checked />允许成人<br/><br/>
                <input type="text" name="word" value="" style="width: 300px;" />
                <input type="submit" value="用雅虎搜索" style="width: 100px;" />
            </form>
        </div>
        <div style="margin-top: 50px; ">
            &copy; 大刘 2019-02-03
        </div>
    </div>
</body>
</html>
```

```xml
// -- WEB-INF/web.xml --
<servlet>
    <servlet-name>SearchServlet</servlet-name>
    <servlet-class>com.daliu.servlet.SearchServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>SearchServlet</servlet-name>
    <url-pattern>/servlet/SearchServlet</url-pattern>
</servlet-mapping>
```

参数带在浏览器地址栏中：

```
http://localhost:8080/demo/servlet/SearchServlet?type=news&word=Hello
```

