# 创建bean对象方式

### 构建对象的策略 

**ApplicationContext**
ApplicationContext在构建核心容器时, 创建对象采取的策略是立即加载的方式. 即只要一读取配置文件马上就创建配置文件中配置的对象.  `ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");`这句代码只要一执行, bean.xml中配置的所有对象全都会被立即创建. 可以通过写配置文件中的对象的构造函数来进行测试得到验证.   

**BeanFactory**    
BeanFactory在构建核心容器时, 创建对象采取的策略是延迟加载的方式.  即第一次根据id获取对象时, 才真正创建对象. 测试如下:  

可如下测试:   

```java
package com.daliu;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Hello {
    public Hello() {
        System.out.println("hello构造函数执行");
    }

    public void sayHello() {
        System.out.println("Hello world!");
    }

    public static void main(String[] args) {
        Resource resource = new ClassPathResource("bean.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
        System.out.println("11111");
        Hello hello = factory.getBean("hello", Hello.class);
        System.out.println("22222");
        hello.sayHello();
    }
}
```

运行结果:   

```java
11111
hello构造函数执行
22222
Hello world!
```

这种通过BeanFactory在什么时候需要时什么时候创建, 可用于多例对象, 注：这种使用XmlBeanFactory的方式已被Spring废弃.      

**注: XmlBeanFactory已被Spring废弃**
```java
Resource resource = new ClassPathResource("bean.xml")
BeanFactory factory = new XmlBeanFactory(resource);
IAccountService service = factory.getBean("accountService");
service.saveAccount();
```

ApplicationContext是经常使用的方式, 它可以根据对象的单例和多例决定采用立即加载还是什么时候需要什么时候加载.  

### 创建bean的三种方式

```xml
<!--resource/bean.xml-->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--把对象的创建交给spring管理-->
    <!--sprint对bean的管理细节
        1. 创建bean的三种方式
        2. bean对象的作用范围
        3. bean对象的生命周期
    -->

    <!--创建bean的三种方式-->
    <!--第一种方式：使用默认构造函数创建
    在spring的配置文件中使用bean标签，配以id和class属性之后，且没有其他属性和标签时
    采用的就是默认构造函数创建bean对象
    如果类中没有默认构造函数，对象无法创建-->
    <bean id="accountService" class="com.daliu.service.impl.AccountServiceImpl"></bean>

    <!--第二种方式：使用工厂中的实例方法创建对象(使用某个类中的实例方法创建对象并存入spring容器)-->
    <bean id="instanceFactory" class="com.daliu.factory.InstanceFactory"></bean>
    <bean id="accountService_2" factory-bean="instanceFactory" factory-method="getAccountService"></bean>

    <!--第三种方式：使用静态工厂中的静态方法创建对象(使用某个类中的静态方法创建对象并存入spring容器)-->
    <bean id="accountService_3" class="com.daliu.factory.StaticFactory" factory-method="getAccountService"></bean>
</beans>
```

```java
// -- java/com.daliu.factory.InstanceFactory.java --
package com.daliu.factory;

import com.daliu.service.IAccountService;
import com.daliu.service.impl.AccountServiceImpl;

/**
 * 模拟一个工厂类（该类可能存在于jar包中，无法通过修改源码的方式提供默认构造函数）
 */
public class InstanceFactory {
    public IAccountService getAccountService() {
        return new AccountServiceImpl();
    }
}
```
```java
// -- java/com.daliu.factory.StaticFactory.java --
package com.daliu.factory;

import com.daliu.service.IAccountService;
import com.daliu.service.impl.AccountServiceImpl;

public class StaticFactory {
    public static IAccountService getAccountService() {
        return new AccountServiceImpl();
    }
}
```