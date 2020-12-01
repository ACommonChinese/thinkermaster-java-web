package com.daliu.servlet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class InjectionServlet extends HttpServlet {
    @Resource(name = "name")
    private String name;  // 也可以：private @Resource(name="name") String name;

    private @Resource(name = "age") Integer age;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>Request servlet</title></head>");
        out.println("<body>");
        out.println("<p> name: " + this.name + "</p>");
        out.println("<p> age：" + this.age + "</p>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }
}
