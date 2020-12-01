package com.daliu.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@FunctionalInterface
interface CommonHtml {
    public abstract void doIt(PrintWriter out);
}

public class LifeCycleServlet extends HttpServlet {
    /// 个税起征点，从配置文件中读取
    private static double startPoint = 0;

    @Override
    public void init() throws ServletException {
        this.log("执行init()方法 ... ");
        ServletConfig config = this.getServletConfig();
        startPoint = Double.parseDouble(config.getInitParameter("startPoint"));
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        this.log("执行service()方法 ... ");
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html;charset=utf-8");
        super.service(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.log("执行doGet()方法 ...");
        this.setCommonHtml(req, resp, out -> {
            out.println("<form method='post'>");
            out.println("您的工资为：" + "<input type='text' name='income' />" + " 单位：元");
            out.println(" <br /> ");
            out.println("<input type='submit' value='计算个税' />");
            out.println("</form>");
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.log("执行doPost()方法 ... ");
        this.setCommonHtml(req, resp, (out) -> {
            try {
                double income = new Double(req.getParameter("income"));
                double charge = income - startPoint; // 超出起征点的部分
                double tax = 0; // 缴纳的税
                // 0, 500, 2000, 5000, 20000, 40000, 60000, 80000, 100000 ...
                //  5%   10%   15%   20%    25%    30%    35%    40%    45%
                // 以3000为例，收的税应为：
                // (3000-2000)*0.15 + (2000-500)*0.1 + 500*0.05 = 150 + 150 + 25 = 325
                // 或者这样计算：
                // 3000*0.15 - (2000-500)*(15%-10%) - 500*(15%-5%) = 450 - 125 = 325
                if (charge <= 0) {
                    tax = 0;
                } else if (charge <= 500) {
                    tax = charge * 0.05;
                } else if (charge > 500 && charge <= 2000) {
                    tax = charge*0.1 - 25;
                } else if (charge > 2000 && charge <= 5000) {
                    tax = charge * 0.15 - 125;
                } else if (charge > 5000 && charge <= 20000) {
                    tax = charge * 0.2 - 375;
                } else if (charge > 20000 && charge <= 40000) {
                    tax = charge * 0.25 - 1375;
                } else if (charge > 40000 && charge <= 60000) {
                    tax = charge * 0.3 - 3375;
                } else if (charge > 60000 && charge <= 80000) {
                    tax = charge * 0.35 - 6375;
                } else if (charge > 80000 && charge <= 100000) {
                    tax = charge * 0.4 - 10375;
                } else {
                    tax = charge * 0.45 - 15375;
                }

                out.println("<p>您的工资为：" + income + "元</p>");
                out.println("<p>应缴税为：" + tax + "元</p>");
                out.println("<hr />");
                out.println("<input type='button' onclick='history.go(-1);' value='返回' />");
            }
            catch (Exception ex) {
                out.println("请输入数值类型 <input type='button' onclick='history.go(-1);' value='返回' />");
            }
        });
    }

    void setCommonHtml(HttpServletRequest req, HttpServletResponse resp, CommonHtml doIt) throws ServletException, IOException {
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
