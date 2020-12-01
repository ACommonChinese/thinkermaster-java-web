# JSP脚本标签

JSP脚本标签: JSP定义Java代码的方式

1. <% java代码 %>: 定义的java代码, 在_jspService方法中. service方法中可以定义什么, 此处就可以定义什么
2. <% !java代码 %>: 定义的java代码, 在jsp转换后的java类的成员位置, 成员变量或成员方法
3. <%= java代码 %>: 定义的java代码, 会输出到页面上, 输出语句中可以定义什么, 该脚本中就可以定义什么

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <%
      // 定义的java代码, 在_jspService方法中. service方法中可以定义什么, 此处就可以定义什么
      System.out.println("hello jsp! this is java code");
      int i = 5; // 方法中的int i, 并非成员变量
    %>
    <%!
      // java类的成员位置
      int i = 3;
      String name = "Hello";
    %>
    <%= i %><br> <!--相当于输出语句, 会把方法service中的i的值5输出-->
    <%= this.i %> <!--相当于输出语句, 会把i的值3输出-->
    <h1>This is html code</h1>
  </body>
</html>
```