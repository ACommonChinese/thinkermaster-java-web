# Listener

Listener也是java web的三大组件之一 (Servlet, Filter, Listener)  

事件监听器包括4个元素:

- 事件: 一件事情, 比如点击事件
- 事件源: 事件发生的地方, 比如按钮
- 监听器: 一个对象或一段代码
- 注册监听: 将事件, 事件源, 监听器绑定在一起, 当在事件源上发生某个事件后执行监听器代码 

Listener监听器有很多种, 此处只了解一种, 即ServletContextListener:  

ServletContextListener 主要用于监听ServletContext对象的创建和销毁, 它是一个接口, 有两个常用方法:  

- void contextDestroyed(ServletContext context): ServletContext对象被销毁之前会调用该方法
- void contextInitialized(ServletContext context): ServletContext对象创建后会调用该方法  

步骤:  
1. 定义一个实现ServletContextListener接口的类并实现方法
2. 配置(web.xml或注解)