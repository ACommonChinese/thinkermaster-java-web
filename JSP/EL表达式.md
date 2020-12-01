# EL表达式

- 概念: EL, Expression Language 表达式语言
- 作用: 简化JSP页面中java代码的编写
- 语法: `${表达式}`
- 注意: 
  - jsp中默认支持EL表达式
  - 在jsp中可以使用`<%page isELIgnored="false"%>`忽略EL表达式
  - 也可以通转转义忽略单个EL表达式: `\${表达式被忽略, 原样输出}`
- 使用方式
  - 运算  `+ = * /(div) %(mod) > < >= <= == != &&(and) ||(or) !(not) empty(判断是否为空, 比如: ${empty myList})`
  - 获取值
    - EL表达式只能从域对象中获取值
    - 第一种语法: ${域对象.键名称}, 比如: `${request.name}`
      - 有4个域: 
      - pageScope: 对应域对象pageContext
      - requestScope: 对应域对象request
      - sessionScope: 对应域对象session
      - applicationScope: 对应域对象application(即java servlet中的ServletContext)
    - 第二种语法: ${键名称} 表示依次从最小的域中查找是否有该键对应的值, 直到找到为止, 顺序为: `pageScope -> requestScope -> sessionScope -> applicationScope`

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>EL Test</title>
</head>
<body>
    <h1>获取域中的数据</h1>
    <%
        request.setAttribute("name", "daliu request");
        session.setAttribute("name", "daliu session");
        application.setAttribute("name", "daliu application");
        pageContext.setAttribute("name", "daliu pageContext");
    %>
    <hr />
    <h1>获取值</h1>
    ${requestScope.name}<br>
    ${sessionScope.name}<br>
    ${applicationScope.name}<br>
    ${pageScope.name}<br>
    ${name}<br> <!--显示daliu pageContext-->
    ${pageScope.hahaha} <!--EL表达式如果取不到值, 则什么也不显示, 相当于""-->
</body>
</html>
```

------------------------------

### 获取对象, List集合, Map集合中的值  

1. 对象: ${域名称.键名.属性名}  本质上会调用对象的get方法
2. List集合: ${域名称.键名[索引]}
3. Map集合: ${域名称.键名.key名称}
4. empty 判断是否为空: 
  - ${empty str}
  - ${empty list}
  - ${not empty str}: 判断字符串, 集合, 数组不为null并且长度大于0

**注:** EL表达式中的.获取的是对象的属性, 即setter或getter方法, 去掉set或get, 再将剩余部分首字母小写
```
setName --> Name --> name
```

**com.daliu.domain.User.java**  

```java
package com.daliu.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private String name;
    private int age;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        // rmatDate("1988-01-03", "yyyy-MM-dd"));
        return birthday;
    }

    public String getBirthdayStr() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.birthday);
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String sayHello() {
        String msg = "Hello china";
        System.out.println(msg);
        return msg;
    }

    public void sing() {
        System.out.println("我的热情, 好像一把火~");
    }

    public void eat(String food) {
        System.out.println("eat: " + food);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
```

```html
<%@ page import="com.daliu.domain.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>EL表达式获取对象数据</title>
</head>
<body>
    <h1>获取域中的数据</h1>
    <%
        User user = new User();
        user.setName("daliu");
        user.setAge(30);
        user.setBirthday(getFormatDate("1988-01-03", "yyyy-MM-dd"));
        request.setAttribute("user", user);

        List list = new ArrayList() {
            {
                add("aaa");
                add("bbb");
            }
        };
        session.setAttribute("list", list);

        List<User> userList = new ArrayList<User>() {
            {
                add(user);
            }
        };
        session.setAttribute("userList", userList);

        Map map = new HashMap();
        map.put("name", "张三");
        map.put("gender", "男");
        map.put("user", user);
        request.setAttribute("map", map);

        String str = "abc";
        request.setAttribute("str", str);
    %>

    <%!
        private Date getFormatDate(String date, String format) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date d = new Date();
            try {
                d = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d;
        }
    %>

    <hr />
    <h3>获取对象的值</h3>
    ${requestScope.user}<br>
    ${requestScope.user.name}<br> <!--找的是requestScope.user.getName()-->
    ${requestScope.user.birthday}<br> <!--Sun Jan 03 00:00:00 CST 1988-->
    ${requestScope.user.birthdayStr}<br> <!--调用方法getBirthdayStr() 1988-01-03-->
    ${requestScope.user.sayHello()}<br> <!--调用方法-->
    ${requestScope.user.sing()}<br><!--调用无返回值方法-->
    ${requestScope.user.eat("Fruit")}<!--调用方法, 传参-->

    <h3>获取List集合中的值</h3>
    ${sessionScope.list}<br>
    ${sessionScope.list[0]}<br>
    ${sessionScope.list[1]}<br>
    ${sessionScope.list[10]}<br><!--如果脚标越异常, 什么都不做, 相当于""-->

    <h3>获取List集合中对象</h3>
    ${sessionScope.userList[0]}<br>
    ${sessionScope.userList[0].name}<br>
    ${sessionScope.userList[0].sayHello()}<br>

    <h3>获取Map集合中的值</h3>
    ${requestScope.map.name}<br>
    ${requestScope.map.gender}<br>
    ${requestScope.map["name"]}<br> <!--这种也可以-->

    <h3>获取Map集合中对象的值</h3>
    ${requestScope.map.user.name}<br>
    ${requestScope.map.user.sayHello()}<br>
    ${requestScope.map["user"].birthdayStr}<br>

    <h3>空运算符empty</h3>
    ${empty str}<br> <!--false-->
    ${empty null}<br> <!--true-->
    ${empty ""}<br> <!--true 长度为0也是true-->
    ${not empty userList} <!--true-->
</body>
</html>
```

### 隐式对象  

EL表达式中有11个隐式对象, 最常用的是pageContext, 示例:  

```html
<%--
  Created by IntelliJ IDEA.
  User: liuweizhen
  Date: 2020/3/23
  Time: 12:10 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${pageContext.request}
<h3>在JSP中动态获取虚拟目录</h3>
${pageContext.request.contextPath}

<form action="${pageContext.request.contextPath}/loginServlet">

</form>
<%--
response.sendRedirect(request.getContextPath());
--%>
</body>
</html>
```

EL内置11个对象,不需定义可直接使用  [CSDN](https://blog.csdn.net/qq_32115439/article/details/54685786)

| 隐式对象  | 作用  |
| :------------ |:---------------|
| pageScope | 代表page域中用于保存属性的Map对象 |
| requestScope | 代表request域中用于保存属性的Map对象 |
| sessionScope | 代表session域中用于保存属性的Map对象 |
| applicationScope | 代表application域中用于保存属性的Map对象 |
| pageContext | 对应于JSP页面中的pageContext对象, <br />可以通过它获取jsp的9大隐式对象, 如 `${pageContext.request}` |
| param | 表示一个保存了所有请求参数的Map<String,String>对象, 如: `${param.userName}` |
| paramValues | 表示一个保存了所有请求参数的Map<String,String[]>对象，如: `${paramValues.userName[1]}` |
| header | 表示一个保存了所有http请求头字段的Map<String,String>对象, 如: `${header["Accept-Language"] }` |
| headerValues | 表示一个保存了所有http请求头字段的Map<String,String[]>对象, 如: `${header["Accept-Language"][1]}` |
| cookie | 表示一个保存了所有cookie的Map对象 |
| initParam | 表示一个保存了所有web应用初始化参数的map对象 |
