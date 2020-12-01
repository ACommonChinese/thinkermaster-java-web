package com.daliu.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ContextParamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>读取上下文参数</title></head>");
        out.println("<style>body, td, div {font-size:12px;}</style>");
        out.println("<body>");

        // 获取上下文
        ServletContext servletContext = this.getServletConfig().getServletContext();

        String contextPath = servletContext.getContextPath(); // /demo
        String uploadFolder = servletContext.getInitParameter("upload folder");
        String allowedFileType = servletContext.getInitParameter("allowed file type");

        out.println("<p> uploadFolder: " + uploadFolder + " </p>");
        out.println("<p> allowd file type: " + allowedFileType + " </p>");
        out.println("<p> context path: " + contextPath + " </p>");

        out.println("</body>");
        out.println("</html>");
    }
}
