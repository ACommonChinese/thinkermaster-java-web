# CommonHtml

**CommonHtml.java**

```java
package com.daliu.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CommonHtml {
    public void setCommonHtml(HttpServletRequest req, HttpServletResponse resp, DoIt doIt) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.write("<meta http-equiv='content-type' content='text/html;charset=UTF-8'/>");
        out.println("<title>This is title</title>");
        out.println("</head>");
        out.println("<body>");
        if (doIt != null) {
            doIt.doIt(out);
        }
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }
}
```

```java
package com.daliu.util;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@FunctionalInterface
public interface DoIt {
    public abstract void doIt(PrintWriter out) throws ServletException, IOException;
}
```