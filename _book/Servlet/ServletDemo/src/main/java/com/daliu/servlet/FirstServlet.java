package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FirstServlet extends HttpServlet {
    @Override
    /**
     * 以GET方式访问页面时执行该函数
     * 执行doGet前会先执行getLastModified，如果浏览器发现getLastModified返回的数值与上次访问时返回的数值相同
     * 则认为该文档没有更新，浏览器采用缓存而不执行doGet
     * 如果getLastModified返回-1, 则认为是时刻更新的，总是执行该函数
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.log("执行doGet...方法"); // 调用Servlet自带的日志输出信息到控制台
        this.execute(req, resp);
    }

    @Override
    /**
     * 返回该Servlet生成的文档的更新时间。对GET方式访问有效
     * 返回的时间为相对于1970年1月1日08:00:00的毫秒数
     * 如果为-1则认为是实时更新
     * 默认为-1
     */
    protected long getLastModified(HttpServletRequest req) {
        this.log("执行getLastModified方法...");
        return -1;
        // return super.getLastModified(req);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.log("执行doPost方法...");
        this.execute(req, resp);
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8"); // 设置response编码方式
        request.setCharacterEncoding("UTF-8");  // 设置request编码方式
        // 访问该Servlet的URI:
        String requestURI = request.getRequestURI();
        // 请求方式，GET 或者 POST
        String method = request.getMethod();
        // 获取客户端提交的参数param值
        String param = request.getParameter("param");
        response.setContentType("text/html"); // 设置响应文档类型为HTML类型
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0.1 Transitional//EN\"");
        out.println("<html>");
        out.println(" <body>");
        out.println("以" + method + "方法访问该页面。取到的param参数为： " + param + "<br />");
        out.println("<form action=" + requestURI + " method='get'>");
        out.println("   <input type='text' name='param' value='param string' />");
        out.println("   <input type='submit' value='以GET方式查询页面'" + requestURI + "<br />");
        out.println("</form>");
        out.println("<form action='" + requestURI + "' method=' post'>");
        out.println("   <input type='text' name='param' value='param string' />");
        out.println("   <input type='submit' value='以POST方式提交到页面'" + requestURI + "' />");
        out.println("</form>");

        // 由客户端浏览器读取该文档的更新时间
        out.println("<script>document.write('本页面最后更新时间：'+ document.lastModified); </script>");
        out.println(" </body");
        out.println("</html>");
        out.flush();
        out.close();
    }
}
