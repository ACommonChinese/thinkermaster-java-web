# Post

参见：Java Web整合开发王者归来p60
Demo: ServletDemo

```html
<!--postPersonalInformation.html-->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Post测试</title>
</head>
<body>
    <form action="servlet/PostServlet" method="post">
        姓名：<input type="text" name="name" value="" /> <br />
        密码：<input type="password" name="password" value="" /> <br />
        再次输入密码：<input type="password" name="rePassword" value="" /> <br />
        性别：<input type="radio" name="sex" value="男" id="male" />
              <label for="male">男</label>
              <input type="radio" name="sex" value="女" id="female" />
              <label for="female">女</label> <br />
        年龄：<input type="text" name="age" /> <br />
        生日：<input type="text" name="birthday" id="birthday"/> <br />
        &nbsp;&nbsp;&nbsp;<label for="birthday">格式：xxxx-mm-dd</label> <br />
        爱好：<input type="checkbox" name="interest" value="音乐影视" id="i1" /> 音乐影视
              <input type="checkbox" name="interest" value="外出旅游" id="i2" /> 外出旅游
              <input type="checkbox" name="interest" value="社交活动" id="i3" /> 社交活动
        <br />
        省市：<select name="area">
                <optgroup label="北京市">
                    <option value="北京市海淀区">海淀区</option>
                    <option value="北京市朝阳区">海淀区</option>
                    <option value="北京市东城区">海淀区</option>
                    <option value="北京市西城区">海淀区</option>
                </optgroup>
                <optgroup label="山东省">
                    <option value="山东省济南市">济南市</option>
                    <option value="山东省青岛市">青岛市</option>
                    <option value="山东省潍坊市">潍坊市</option>
                </optgroup>
              </select> <br /><br />
        自我描述：<br><br><textarea name="description" rows="8">请填写其他资料...</textarea> <br /><br />
        <input type="submit" name="btn" value="提交信息"> <br />
    </form>
</body>
</html>
```

```java
-- com.daliu.servlet.PostServlet.java --
package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>Request servlet</title></head>");
        out.println("<body>");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("rePassword");
        String sex = request.getParameter("sex");
        int age = Integer.parseInt(request.getParameter("age"));
        Date birthday = null;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            birthday = format.parse(request.getParameter("birthday"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String[] interestList = request.getParameterValues("interest");
        String description = request.getParameter("description");
        writeLine(out, "姓名", name);
        writeLine(out, "密码", password);
        writeLine(out, "重复密码", rePassword);
        writeLine(out, "生日", birthday.toString());
        for (String inter :
                interestList) {
            writeLine(out, "爱好", inter);
        }
        out.println("<br />");
        out.println("<input type='button' name='btn' value='返回上一页' onclick='history.go(-1);'");
        out.println("</body>");
        out.println("</html>");
    }

    private void writeLine(PrintWriter out, String name, String value) {
        out.println("<h1> " + name + ": " + value + " </h1>");
    }
}
```

### GET 和 POST底层数据不同

```java
package com.daliu.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeBackParameter(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeBackParameter(req, resp);
    }

    private void writeBackParameter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder();
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            builder.append(name + " ----> " + req.getParameter(name) + "\n");
        }
        resp.getWriter().print(builder.toString());
    }
}
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <form action="/login" method="get">
        <input type="text" name="username" placeholder="input username here"><br>
        <input type="password" name="password" placeholder="input password here"><br>
        <input type="submit" value="提 交">
    </form>
</body>
</html>
```

当时GET时,  假如输入username为"daliu", password为"111111", 则: 

```
Request URL: http://localhost:8080/login?username=daliu&password=111111
Request Method: GET
Status Code: 200 
...
Referer: http://localhost:8080/login.html

查询字符串Query String: 
username=daliu&password=111111
```

而POST时:  
```
Request URL: http://localhost:8080/login
Request Method: POST
Status Code: 200
...
Content-Type: application/x-www-form-urlencoded
Referer: http://localhost:8080/login.html

表单数据form data:
username=daliu&password=111111
```

