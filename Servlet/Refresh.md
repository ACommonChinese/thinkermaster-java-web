# Refresh

自动刷新不仅可以实现一段时间之后自动跳转到另一个页面，还可以实现一段时间之后自动刷新本页面。 Servlet中通过HttpServletResponse对象设置Header属性实现自动刷新效果，例如：  

```java
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // response.setHeader("Refresh", "3; URL=/demo/servlet/forwardServlet"); // 3秒之后跳转到/demo/servlet/forwardServlet
    response.setHeader("Refresh", "3; URL=https://www.baidu.com"); // 3秒之后跳转到https://www.baidu.com
    // response.setHeader("Refresh", "0; URL=https://www.bing.com"); // 相当于重定向
}
```