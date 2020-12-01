package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // super.doGet(req, resp);
        // 设置request编码方式
        request.setCharacterEncoding("UTF-8");
        // 设置response编码方式
        response.setCharacterEncoding("UTF-8");
        // 设置文档类型
        response.setContentType("text/html");
        // 获取用于响应的打印输入字符流
        PrintWriter out = response.getWriter();
        // 输入到客户端
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("   <title>DaLiu title</title>");
        out.println("</head>");
        out.println("<body>");

        // 获取URI路径
        /**
         * <form action=[requestURI] method=post>
         *     <input type='submit' />
         * </form>
         */
        String requestURI = request.getRequestURI(); // /web_war_exploded/servlet/HelloServlet
        out.println("<form action='" + requestURI + "' method='post'>");
        out.println("请输入你的名字：<input type='text' name='name' />");
        out.println("<input type='submit' />");
        out.println("</form>");

        String name = request.getParameter("name");
        if (name != null && name.trim().length() > 0) {
            out.println("您好，<b>" + name + "</b>. 欢迎来到Java Web世界.");
        }
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // super.doPost(req, resp);
        this.doGet(request, response);
    }
}
