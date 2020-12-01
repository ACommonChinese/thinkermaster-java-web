# Filter

Servlet, Filter, Listener都是JavaWeb的核心组件   
当访问服务器时, 过滤器Filter可将请求拦截下来, 完成一些通用的校验工作
过滤器一般用于完成通用的操作, 比如登录验证, 统一编码处理, 敏感字符过滤等...  

步骤:
1. 定义一个类, 实现Filter接口
2. 复写Filter接口中的方法
3. 配置拦截路径
    - web.xml配置
    - 注解配置

