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
