# bean的生命周期

首先看一下bean的作用范围, bean标签有一个scope属性：  

- singleton: 单例的（默认值）Spring默认创建的bean对象只创建一次, 即是单例的`singleton`
- prototype: 多例的
- request: 作用于web应用的请求范围
- session: 作用于web应用的会话范围
- global-session: 作用于集群环境的会话范围（全局会话范围），当不是集群环境时，它等同于session  

单例对象的生命周期是： 

```
出生：当容器创建对象出生
活着：只要容器还在，对象一直活着
死亡：容器销毁，对象消亡
总结：单例对象的生命周期和容器相同
```

单例对象的生命周期测试：  

```xml
<!--resources/bean.xml-->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--把对象的创建交给spring管理-->
    <bean id="accountService"
          class="com.daliu.service.impl.AccountServiceImpl"
          scope="singleton"
          init-method="init"
          destroy-method="destroy"></bean>
</beans>
```

```java
// -- com.daliu.service.IAccountService.impl.AccountServiceImpl

package com.daliu.service.impl;
import com.daliu.service.IAccountService;

/**
 * 帐户的业户层实现类
 */
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
        System.out.println("AccountServiceImpl::saveAccount()");
    }

    public void init() {
        System.out.println("初始化方法 AccountServiceImpl::init");
    }

    public void destroy() {
        System.out.println("销毁方法 AccountServiceImpl::destroy");
    }
}

```

```java
package com.daliu.ui;

import com.daliu.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 表现层，调用业务层
 */
public class Client {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService accountService2 = (IAccountService) context.getBean("accountService"); // IAccountService.class
        accountService2.saveAccount();
        // 手动销毁容器
        context.close();
    }
}
```

打印结果： 

```
初始化方法 AccountServiceImpl::init
AccountServiceImpl::saveAccount()
销毁方法 AccountServiceImpl::destroy
```

多例对象的生命周期是： 

```
出生：使用对象时由spring框架创建
活着：对象在使用过程中就一直活着
死亡：当对象没有被其他对象引用时，由垃圾回收器回收
```

如果把上面的代码scope的值改成prototype： 

```xml
<bean id="accountService"
      class="com.daliu.service.impl.AccountServiceImpl"
      scope="prototype"
      init-method="init"
      destroy-method="destroy">
</bean>
```

程序打印结果：  

```
初始化方法 AccountServiceImpl::init
AccountServiceImpl::saveAccount()
// 并没有打印destroy，因为GC还没来得及回收，程序结束了
```

----------------------

单例对象: 容器加载时立即创建  
多例对象: 使用时延迟创建