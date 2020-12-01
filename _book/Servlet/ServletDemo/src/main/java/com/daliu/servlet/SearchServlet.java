package com.daliu.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        // http://localhost:8080/servlet/SearchServlet?type=news&allowedAdult=true&word=hello
    }
}
