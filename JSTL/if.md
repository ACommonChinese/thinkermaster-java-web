# if

if标签
- 属性: test 必填属性
  - 接收boolean表达式, 如果表达式为true, 则显示标签体内容, 否则不显示
  - test属性值一般会结合EL表达式一起使用

示例:  

```html
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<c:if test="true">
Yes, show it, 显示<br>
</c:if>

<%
    List list = new ArrayList();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    request.setAttribute("list", list);

    request.setAttribute("number", 3);
%>

<c:if test="${not empty list}">
    遍历集合<br>
</c:if>

<c:if test="${number % 2 != 0}">
    ${number}为奇数<br>
</c:if>

</body>
</html>
```