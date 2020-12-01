# ServletContext

> public interface ServletContext  
>
> Defines a set of methods that a servlet uses to communicate with its servlet container, for example, to get the MIME type of a file, dispatch requests, or write to a log file.
> There is one context per "web application" per Java Virtual Machine. (A "web application" is a collection of servlets and content installed under a specific subset of the server's URL namespace such as /catalog and possibly installed via a .war file.) 
>
> ...
> 

ServletContext定义了一组让Servlet和Servlet容器交互的接口. 一般说ServletContext代表整个web应用, 使用它和容器(服务器)通信;  
ServletContext域对象作用范围是整个web应用  
功能: 
- 获取MIME类型
- 通过域对象共享数据
- 获取文件的真实(服务器)路径

**获取ServletContext: **

- servlet.getServletConfig()
- servletConfig.getServletContext()

**获取MIME类型**  

- context.getMimeType(fileName)

`/Users/liuweizhen/Library/Tomcat/apache-tomcat-9.0.31/conf/web.xml`中描述了MIME类型: 

```xml
<mime-mapping>
    <extension>jpeg</extension>
    <mime-type>image/jpeg</mime-type>
</mime-mapping>
<mime-mapping>
    <extension>jpg</extension>
    <mime-type>image/jpeg</mime-type>
</mime-mapping>
<mime-mapping>
    <extension>jpgm</extension>
    <mime-type>video/jpm</mime-type>
</mime-mapping>
<mime-mapping>
    <extension>png</extension>
    <mime-type>image/png</mime-type>
</mime-mapping>
...
<mime-mapping>
    <extension>htm</extension>
    <mime-type>text/html</mime-type>
</mime-mapping>
<mime-mapping>
    <extension>html</extension>
    <mime-type>text/html</mime-type>
</mime-mapping>
...
```

**通过ServletContext全局域对象共享数据**
由于是域对象, 可以setAttribute() getAttribute() remoteAttribute(), 但一般不建议在这个全局对象中设置过多的共享数据  

示例:  

**com.daliu.servletContext.ServletContextDemo1.java**
```java
package com.daliu.servletContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servletContextDemo1")
public class ServletContextDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ---------------------------------------------
        // 两种获取ServletContext的方式
        ServletContext context1 = getServletContext();
        ServletContext context2 = getServletConfig().getServletContext();
        if (context1 == context2) {
            System.out.println("Equal");
            System.out.println(context1);
            System.out.println(context2);
        }

        // ---------------------------------------------
        // 获取MIME
        String fileName = "a.jpg";
        String mineType = context1.getMimeType(fileName);
        System.out.println(mineType); // image/jpeg

        // ---------------------------------------------
        // 使用ServletContext全局域对象共享数据
        // ServletContext域范围是整个App级
        context1.setAttribute("name", "daliu");
    }
}
```

**com.daliu.servletContext.ServletContextDemo2.java**

```java
package com.daliu.servlet.com.daliu.servletContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servletContextDemo2")
public class ServletContextDemo2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(getServletContext().getAttribute("name"));
        // 换一个浏览器, 访问http://localhost:8080/demo/servletContextDemo2, 依然可以得到"daliu"
    }
}
```
-----------------------------------------

### 获取文件的真实(服务器)路径  

- String getRealPath(String path)  

在IDEA中运行项目时, 通过LOG找到: 

```
CATALINA_BASE:         /Users/liuweizhen/Library/Caches/IntelliJIdea2019.3/tomcat/Unnamed_LearServlet
```

打开这个路径, 可以找到conf/Catalina/localhost/demo.xml:  

```
<Context path="/demo" docBase="/Users/liuweizhen/IdeaProjects/LearServlet/out/artifacts/Demo_war_exploded" />
```

docBase指明的路径即是web项目要部署的根目录, 这个目录中的东西会被发布到服务器中, 而通过ServletContext获取的realPath就是指这个路径:  

```java
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  ServletContext context = this.getServletContext();
    String realPath = context.getRealPath("");
    System.out.println(realPath); 
    // /Users/liuweizhen/IdeaProjects/LearServlet/out/artifacts/Demo_war_exploded/
}
```

在IDEA中, 这个路径对应的是web目录, web目录下文件即获取方式:
- a.txt   servletContext.getRealPath("/a.txt")
- web/WEB-INF/b.txt  servletContext.getRealPath("/WEB-INF/b.txt")

**源代码路径**
在IDEA中src源代码目录下的文件部署时会被部署到/WEB-INF/classes目录下, 因此获取IDEA中src/c.txt方式为: 

```
servletContext.getRealPath("/WEB-INF/classes/c.txt")
```

不过, 获取src下的文件也可以通过classLoader来获取, 但通过ClassLoader的方式不可以获取web目录下的文件

----------------------------

### 示例  

```java
package com.daliu.servletContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/servletContextDemo")
public class ServletContextDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        String aPath = context.getRealPath("/WEB-INF/classes/a.txt");
        String bPath = context.getRealPath("/WEB-INF/b.txt");
        String cPath = context.getRealPath("c.txt");
        String dPath = context.getRealPath("/WEB-INF/classes/com/daliu/servletContext/d.txt");
        BufferedReader reader = new BufferedReader(new FileReader(aPath));
        System.out.println("a.txt ---> " + aPath + " ---> " + reader.readLine());
        reader.close();
        reader = new BufferedReader(new FileReader(bPath));
        System.out.println("b.txt ---> " + bPath + " ---> "  + reader.readLine());
        reader.close();
        reader = new BufferedReader(new FileReader(cPath));
        System.out.println("c.txt ---> " + cPath + " ---> "  + reader.readLine());
        reader.close();
        reader = new BufferedReader(new FileReader(dPath));
        System.out.println("d.txt ---> " + dPath + " ---> "  + reader.readLine());
        reader.close();
        /**
         a.txt ---> /Users/liuweizhen/IdeaProjects/LearServlet/out/artifacts/Demo_war_exploded/WEB-INF/classes/a.txt ---> 我是a.txt
         b.txt ---> /Users/liuweizhen/IdeaProjects/LearServlet/out/artifacts/Demo_war_exploded/WEB-INF/b.txt ---> 我是b.txt
         c.txt ---> /Users/liuweizhen/IdeaProjects/LearServlet/out/artifacts/Demo_war_exploded/c.txt ---> 我是c.txt
         d.txt ---> /Users/liuweizhen/IdeaProjects/LearServlet/out/artifacts/Demo_war_exploded/WEB-INF/classes/com/daliu/servletContext/d.txt ---> 我是d.txt
         */
    }
}
```