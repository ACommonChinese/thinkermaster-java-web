# 基于XML的SpringAop

模拟帐户类IAccountService, 当调用帐户类对象每一个方法之前，通过AOP进行日志输出。

**pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.daliu</groupId>
    <artifactId>day03_03springAOP</artifactId>
    <version>1.0-SNAPSHOT</version>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>6</source>
                    <target>6</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.7</version>
        </dependency>
    </dependencies>
</project>
```

**com.daliu.service.IAccountService.java**

```java
package com.daliu.service;

/**
 * 帐户的业务层接口
 */
public interface IAccountService {
    /**
     * 模拟保存帐户
     */
    void saveAccount();

    /**
     * 模拟更新帐户
     * @param i
     */
    void updateAccount(int i);

    /**
     * 删除帐户
     */
    int deleteAccount();
}
```

**com.daliu.service.impl.AccountServiceImpl.java**

```java
package com.daliu.service.impl;

import com.daliu.service.IAccountService;

/**
 * 帐户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {
    @Override
    public void saveAccount() {
        System.out.println("保存帐户成功");
    }

    @Override
    public void updateAccount(int i) {
        System.out.println("更新帐户成功" + i);
    }

    @Override
    public int deleteAccount() {
        System.out.println("删除帐户成功");
        return 0;
    }
}
```

**com.daliu.utils.Logger.java**

```java
package com.daliu.utils;

/**
 * 用于记录日志的工具类
 */
public class Logger {
    /**
     * 打印日志，计划让其在切入点执行之前执行(切入点方法就是业务层方法)
     */
    public void printLog() {
        System.out.println("Logger类中的printLog方法开始记录日志了...");
    }
}
```

**resources/bean.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--配置spring的IOC, 把service对象配置进来-->
    <!--要达到的目的：在执行accountService的任何方法之前都要加上日志-->
    <bean id="accountService" class="com.daliu.service.impl.AccountServiceImpl">
    </bean>

    <!--spring中基于XML的AOP配置步骤
    1. 把通知Bean也交给spring来管理
    2. 使用aop:config标签表明开始AOP的配置
    3. 使用aop:aspect标签表明配置切面
        id属性：给切面提供一个唯一标识
        ref属性：指定通知类bean的id
    4. 在aop:aspect标签的内部使用对应的标签来配置通知的类型, 此示例让printLog在切入点方法执行前执行，是前置通知，用aop:before表示
        aop:before: 表示前置通知
        method：指定通知类中的方法
        pointcut: 表示切入点表达式，含义是对业务层中哪些方法增强
        切入点表达式写法：
            关键字：execution(表达式)
            表达式：
                    访问修饰符 返回值 包名.名名...类名.方法名(参数列表)
            标准写法：
                public void com.daliu.service.impl.AccountServiceImpl.saveAccount()
            访问修饰符可以省略：
                void com.daliu.service.impl.AccountServiceImpl.saveAccount()
            返回值可以使用通配符表示任意返回值：
                * com.daliu.service.impl.AccountServiceImpl.saveAccount()
            包名可以使用通配符表示任意包，但是有几级包就需要写几个*：
                * *.*.*.*.AccountServiceImpl.saveAccount()
            包名可以使用..表示当前包及其子包：
                * *..AccountServiceImpl.saveAccount()
            类名可以使用*实现通配：
                * *..*.saveAccount()
            方法名可以使用*实现通配：
                * *..*.*()  注：这种方式匹配任意包下任意没有参数的方法
            参数列表可以直接写数据类型：
                基本类型直接写名称: int
                引用类型写名名.类名: java.lang.String
                * *..*.*(int)
                * *..*.*(java.lang.String)
                可以使用通配符*表示任意类型，但是必须有参数：
                * *..*.*(*)
                可以通过..表示任意参数, 可以无参数：
                * *..*.*(..) 即全通配的写法：
            全通配写法：
                * *..*.*(..)
    实际开发中切入点表达式的通常写法：
        切到业务层实现类下的所有方法，比如：
            com.daliu.service.impl.*.*(..)
    -->
    <!--配置Logger类-->
    <bean id="logger" class="com.daliu.utils.Logger">
    </bean>

    <!--配置aop-->
    <aop:config>
        <!--配置切面，引用通知类对象logger-->
        <aop:aspect id="logAdvice" ref="logger">
            <!--method指定Logger类中哪个方法是前置通知-->
            <!--当执行com.daliu.service.impl.AccountServiceImpl.saveAccount()之前会执行logger对象的printLog方法-->
            <!-- <aop:before method="printLog" pointcut="execution(public void com.daliu.service.impl.AccountServiceImpl.saveAccount())"></aop:before> -->
            <aop:before method="printLog" pointcut="execution(* *..*.*(..))"></aop:before>
        </aop:aspect>
    </aop:config>
</beans>
```

### 4种通知类型

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <bean id="accountService" class="com.daliu.service.impl.AccountServiceImpl">
    </bean>


    <!--配置Logger类-->
    <bean id="logger" class="com.daliu.utils.Logger">
    </bean>

    <!--配置aop-->
    <aop:config>
        <!--配置切面，引用通知类对象logger-->
        <aop:aspect id="logAdvice" ref="logger">
            <!--前置通知: 在切入点方法执行之前执行-->
            <aop:before method="beforePrintLog" pointcut="execution(* com.daliu.service.impl.*.*(..))"></aop:before>

            <!--后置通知：在切入点方法正常执行之后执行-->
            <aop:after-returning method="afterReturningPrintLog" pointcut="execution(* com.daliu.service.impl.*.*(..))"></aop:after-returning>

            <!--异常通知：在切入点方法执行产生异常之后执行，它和后置通知永远只能执行一个-->
            <aop:after-throwing method="afterThrowingPrintLog" pointcut="execution(* com.daliu.service.impl.*.*(..))"></aop:after-throwing>

            <!--最终通知：无论切入点是否正常执行它都会在其后面执行-->
            <aop:after method="afterPrintLog" pointcut="execution(* com.daliu.service.impl.*.*(..))"></aop:after>
        </aop:aspect>
    </aop:config>
</beans>
```

### 切入点表达式引用 pointcut-ref

```xml
<aop:config>
    <aop:pointcut id="pt1" expression="execution(* com.daliu.service.impl.*.*(..))"/>

    <!--配置切面，引用通知类对象logger-->
    <aop:aspect id="logAdvice" ref="logger">
        <aop:before method="beforePrintLog" pointcut-ref="pt1"></aop:before>
        <aop:after-returning method="afterReturningPrintLog" pointcut-ref="pt1"></aop:after-returning>
        <aop:after-throwing method="afterThrowingPrintLog" pointcut-ref="pt1"></aop:after-throwing>
        <aop:after method="afterPrintLog" pointcut-ref="pt1"></aop:after>
        <!--配置切入点表达式
        id: 指定表达式唯一标识
        expression: 指定表达式内容
        注：此标签如果写在aop:aspect标签内部，只能当前切面使用。
        它还可以写在aop:aspect外面，此时就变成了所有切面可用
        -->
        <!-- <aop:pointcut id="pt1" expression="execution(* com.daliu.service.impl.*.*(..))"/>-->
    </aop:aspect>
</aop:config>
```

### 环绕通知  
