# 注解HelloWorld

```xml
<!--pom.xml-->
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.daliu</groupId>
    <artifactId>springioc</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
    </dependencies>

</project>
```

```xml
<!--resources/bean.xml-->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <!--告知spring在创建容器时要扫描的包，配置所需要的标签是在一个名称为context名称空间和约束中-->
    <!--扫描com.daliu包下及其子包下所有使用注解的spring bean-->
    <context:component-scan base-package="com.daliu"></context:component-scan>
</beans>
```

```java
package com.daliu.service.impl;

import com.daliu.service.IAccountService;
import org.springframework.stereotype.Component;

/**
 ==== 创建对象的注解 ====
    同XML中：<bean id="" class=""></bean>
    @Component
        作用：把当前类对象存入spring容器
        属性：
            value: 指定bean的id, 如果省略不写，默认值是当前类型，首字母小写，比如Person，则@Component(value = "person")
            如果@Component中只有一个value属性，则value可以不写，比如写成：@Component("person")
    @Controller：一般用在表现层
    @Service：一般用在业务层
    @Repository：一般用在持久层
        以上三个注解的作用和属性与@Component相同。他们三个是spring框架为我们提供明确的三层使用的注解，使用我们的三层对象更加清晰
 */
@Component(value = "accountService") // 如果不写value, 则value的默认值就是：accountServiceImpl
public class AccountServiceImpl implements IAccountService  {
    public void saveAccount() {
        System.out.println("account service implement saveAccount");
    }
}
```

```java
package com.daliu.ui;

import com.daliu.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService accountService = (IAccountService) context.getBean("accountService");
        System.out.println(accountService);
        accountService.saveAccount();
    }
}
```