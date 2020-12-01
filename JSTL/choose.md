# choose

choose标签相当于java中的switch, 一般结合使用when标签做判断 

choose - when - otherwise 相当于:
switch - case - default

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
request.setAttribute("number", 3);
%>

<c:choose>
    <c:when test="${number == 1}">周一</c:when>
    <c:when test="${number == 2}">周二</c:when>
    <c:when test="${number == 3}">周三</c:when>
    <c:when test="${number == 4}">周四</c:when>
    <c:when test="${number == 5}">周五</c:when>
    <c:when test="${number == 6}">周六</c:when>
    <c:when test="${number == 7}">周日</c:when>
    <c:otherwise>输入有误</c:otherwise>
</c:choose>
</body>
</html>

```