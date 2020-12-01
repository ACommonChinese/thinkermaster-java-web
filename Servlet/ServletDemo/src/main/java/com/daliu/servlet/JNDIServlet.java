// Java Web王者归来 P59

package com.daliu.servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JNDIServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>JNDI Demo</title></head>");
        out.println("<body>");
        try {
            Context ctx = new InitialContext();
            String name = (String)ctx.lookup("java:comp/env/name");
            Integer age = (Integer)ctx.lookup("java:comp/env/age");
            out.println("<p> " + name + " </p>");
            out.println("<p> " + age + " </p>");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }
}
