# foreach

foreach相当于java中的for语句

### 使用foreach完成重复的操作
属性:
- begin: 开始值
- end: 结束值
- var: 临时变量
- step: 步长
- varStatus: 循环状态对象
  - index: 容器中元索的索引, 从0开始
  - count: 循环次数, 从1开始

示例:  

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
<!-- for(i = 1; i <= 10; i+=1) -->
<c:forEach begin="1" end="10" var="i" step="1">
    ${i}<br>
</c:forEach>
<!--
1
2
3
4
5
6
7
8
9
10
-->
<br>
<!-- for(i = 1; i <= 10; i+=2) -->
<c:forEach var="i" begin="1" end="10" step="2" varStatus="s">
    i=${i}<br>
    s.index=${s.index}<br><!--容器中元素的索引-->
    s.count=${s.count}<br><!--count表示循环的次数-->
    <br/>
    <!--
    i=1
    s.index=1
    s.count=1

    i=3
    s.index=3
    s.count=2

    i=5
    s.index=5
    s.count=3

    i=7
    s.index=7
    s.count=4

    i=9
    s.index=9
    s.count=5
    -->
</c:forEach>

</body>
</html>
```

----------------------------

### 使用foreach遍历容器 
属性:
- items: 容器对象
- var: 容器中元素的临时变量
- varStatus: 循环状态对象
  - index: 容器中元索的索引, 从0开始
  - count: 循环次数, 从1开始

```jsp
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    List list = new ArrayList() {
        {
            add("aaa");
            add("bbb");
            add("ccc");
        }
    };
    request.setAttribute("list", list);
%>

<c:forEach items="${requestScope.list}" var="str" varStatus="s">
    ${s.index} ${s.count} ${str}<br>
</c:forEach>
<%--
0 1 aaa
1 2 bbb
2 3 ccc
--%>

</body>
</html>
```