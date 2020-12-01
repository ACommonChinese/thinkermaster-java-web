package com.daliu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Locale;

public class RequestServlet extends HttpServlet {
    private void writeLine(PrintWriter out, String key, String value) {
        out.println("<p>" + key + ": " + value + "</p>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        String authType         = request.getAuthType();
        String localAddr        = request.getLocalAddr();
        int localPort           = request.getLocalPort();
        Locale locale           = request.getLocale();
        String contextPath      = request.getContextPath(); // /demo
        String method           = request.getMethod();
        String pathInfo         = request.getPathInfo();
        String pathTranslate    = request.getPathTranslated();
        String protocol         = request.getProtocol();
        String queryString      = request.getQueryString();
        String remoteAddr       = request.getRemoteAddr();
        int remotePort          = request.getRemotePort();
        String remoteUser       = request.getRemoteUser();
        String requestSessionId = request.getRequestedSessionId();
        String requestURI       = request.getRequestURI(); // /demo/ser vlet/RequestServlet
        StringBuffer requestURL = request.getRequestURL(); // http://localhost:8080/demo/servlet/RequestServlet
        String scheme           = request.getScheme();
        String serverName       = request.getServerName();
        int serverPort          = request.getServerPort();
        String servletPath      = request.getServletPath(); // /servlet/RequestServlet
        Principal userPrincipal = request.getUserPrincipal();
        String accept           = request.getHeader("accept");
        String referer          = request.getHeader("referer");
        String userAgent        = request.getHeader("user-agent");
        String serverInfo       = this.getServletContext().getServerInfo();

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>Request servlet</title></head>");
        out.println("<body>");
        writeLine(out, "authType", authType);
        writeLine(out, "localAddr", localAddr);
        writeLine(out, "localPort", ""+localPort);
        writeLine(out, "locale", locale.toString());
        writeLine(out, "contextPath", contextPath);
        writeLine(out, "method", method);
        writeLine(out, "pathInfo", pathInfo);
        writeLine(out, "pathTranslate", pathTranslate);
        writeLine(out, "protocol", protocol);
        writeLine(out, "queryString", queryString);
        writeLine(out, "remoteAddr", remoteAddr);
        writeLine(out, "remotePort", remotePort+"");
        writeLine(out, "requestSessionId", requestSessionId);
        writeLine(out, "requestURI", requestURI);
        writeLine(out, "requestURL", requestURL.toString());
        writeLine(out, "scheme", scheme);
        writeLine(out, "serverName", serverName);
        writeLine(out, "serverPort", ""+serverPort);
        writeLine(out, "servletPath", servletPath);
        writeLine(out, "accept", accept);
        writeLine(out, "refer", referer);
        writeLine(out, "userAgent", userAgent);
        writeLine(out, "serverInfo", serverInfo);
        if (userPrincipal != null) {
            writeLine(out, "userPrincipal", userPrincipal.toString());
        } else {
            writeLine(out, "userPrincipal", "null");
        }

        out.println("<br /><br /><a href=" + requestURI + ">" + "单击刷新本页面" + "</a>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }
}
