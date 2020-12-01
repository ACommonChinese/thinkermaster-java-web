# @PostConstruct_@PreDestroy

参见：Java Web整合开发王者归来p85

从Java EE 5开始，Servlet中增加了两个影响Servlet生命周期的注解 `@PostConstruct、@PreDestroy`。这两个注解用来修饰非静态的void方法，这个方法不能有抛出异常声明 。  

示例：  

```java
@PostConstruct // 标识在构造方法之后执行（注：在init方法之前）
public void someMethod() {
	...
}

// 标识在服务器卸载Servlet时运行，并且只会被服务器调用一次
// 虽然pre destroy的字面意思是在destroy之前执行，但事实是被@PreDestroy修饰的方法
// 会在destroy()之后，服务器卸载Servlet之前执行
public @PreDestroy void anotherMethod() {
	...
}
```

流程：

1. 服务器加载servlet
2. servlet构造函数
3. @PostConstruct修饰的方法
4. service(HttpServletRequest req, HpptServletResponse resp)
5. destroy()
6. @PreDestroy
7. 服务器卸载Servlet

注：注解多多少少会影响到服务器的启动速度，服务器在启动的时候会遍历Web应用的WEB-INF/classes下的所有class文件与WEB-INF/lib下的所有jar文件。  
如果应用程序没有使用任何注解，可以在Web.xml中设置metadata-complete以优化启动速度：

```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee" version="2.5" metadata-complete="true"
</web-app>
```

