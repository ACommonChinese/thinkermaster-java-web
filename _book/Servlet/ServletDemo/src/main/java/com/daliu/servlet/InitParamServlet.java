package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/*
获取web.xml中配置的initParam参数
 */
public class InitParamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>获取web.xml参数</title></head>");
        out.println("<style>body, td, div {font-size:12px;}</style>");
        out.println("<body>");

        out.println("<form action='" + request.getRequestURI() + "' method='post'>");
        out.println("帐号：<input type='text' name='username' style='width:200px; '/> <br/><br/>");
        out.println("密码：<input type='password' name='password' style='width:200px; '/> <br/><br/>");
        out.println("<input type='submit' value=' 登 录 ' />");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 获取web.xml中配置的initParam配置信息
        Enumeration params = this.getInitParameterNames();
        // getInitParameter("firstName")
        /**
         *  <init-param>
         *      <param-name>firstName</param-name>
         *      <param-value>firstValue</param-value>
         *  </init-param>
         */
        while (params.hasMoreElements()) { // 获取所有names
            String usernameParam = (String) params.nextElement();
            String passwordParam = this.getInitParameter(usernameParam);
            if (usernameParam.equalsIgnoreCase(username) && passwordParam.equalsIgnoreCase(password)) {
                // 跳转到/WEB-INF/notice.html
                request.getRequestDispatcher("/WEB-INF/notice.html").forward(request, response);
                return;
            }
        }
        // 如果username, password不匹配，显示登录页面
        this.doGet(request, response);
     }
}
