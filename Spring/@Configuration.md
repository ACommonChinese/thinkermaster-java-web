# @Configuration

上一文[基于注解的IOC案例](./基于注解的IOC案例.md)中，并不是纯注解方式，而是结合使用了XML混合方式，现在使用@Configuration注解完全替换上一节中的bean.xml, Demo地址：`./demos/annotation_ioc_without_xml`:  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="com.daliu"></context:component-scan>

    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
        <constructor-arg name="ds" ref="dataSource">
        </constructor-arg>
    </bean>

    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/daliu?characterEncoding=utf-8&amp;useSSL=false&amp;serverTimezone=UTC"></property>
        <property name="user" value="root"></property>
        <property name="password" value="daliu8807"></property>
    </bean>
</beans>
```

使用的注解如下：  

```java
/**
 * @Configuration
 *      指定当前类是一个配置类
 *
 * @ComponentScan
 *      作用：指定Spring在创建容器时要扫描的包
 *      属性: 有value和basePackages, 同义
 *      代替：<context:component-scan base-package="com.daliu"></context:component-scan>
 *      标准写法应是：basePackages = {"com.daliu"}, 因数组中只有一个元素，可以省略{}
 *
 *  @Bean
 *      作用： 把当前方法返回值作为bean存入spring的ioc容器中
 *      属性：
 *          name: 指定bean的id, 当不写时默认为当前方法的名称，即："createQueryRunner"
 *      当使用注解配置方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象，
 *      查找的方式和Autowired注解的作用是一致的
 */
```

**pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.daliu</groupId>
    <artifactId>annotation_ioc_without_xml</artifactId>
    <version>1.0-SNAPSHOT</version>

    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>commons-dbutils</groupId>
            <artifactId>commons-dbutils</artifactId>
            <version>1.4</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.13</version>
        </dependency>
        <dependency>
            <groupId>c3p0</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.1.2</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>

    </dependencies>

</project>
```

**config.SpringConfiguration.java** 

```java
package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.daliu")
public class SpringConfiguration {

    /**
     * 创建数据源对象
     */
    @Bean(name="dataSource")
    public DataSource createDataSource() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass("com.mysql.cj.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/daliu?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC");
            ds.setUser("root");
            ds.setPassword("daliu8807");
            return ds;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(" 💀💀💀💀💀💀💀");
        }
    }

    @Bean(name = "runner") // 使用注解使用方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象，查找的方式和@Autowired一样
    @Scope(value = "prototype")
    public QueryRunner createQueryRunner(DataSource dataSource) {
        return new QueryRunner(dataSource);
    }
}
```

**......** 

**java/test.AccountServiceTest.java**

```java
package test;

import com.daliu.domain.Account;
import com.daliu.service.IAccountService;
import config.SpringConfiguration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * 使用Junit单元测试测试
 */
public class AccountServiceTest {
    public IAccountService getAccountService() {
        // ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        IAccountService accountService = context.getBean("accountService", IAccountService.class);
        return accountService;
    }

    @Test
    public void testFindAll() {
        IAccountService as = this.getAccountService();
        List<Account> accounts = as.findAllAccount();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
}
```

### 继续优化@Configuration

实际开发中，往往有多个配置文件，这时候可以在主配置文件中通过注解@Import引入其他配置文件，比如： 

**config.SpringConfiguration.java**

```java
package config;

import org.springframework.context.annotation.*;

/**
 * @Configuration
 *      作用：指定当前类是一个配置类
 *      细节：当配置类作为AnnotationConfigApplicationContext对象创建的参数时，该注解可以省略不写，比如：
 *      ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
 *      由于这个参数是惟一的，即，SpringConfiguration.class, 因此，这里的@Configuration可以省略
 *      但是，如果有多个config，要么在类前加@CompoenentScan, 要么指定多个config的class, 即如下两种做法都可以
 *      1.  @Configuration
 *          @ComponentScan({"com.daliu", "config"})
 *          public class SpringConfiguration { ... }
 *
 *          @Configuration
 *          public class JdbcConfig { ... }
 *
 *          ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
 *
 *       2. JdbcConfig前无@Configuration, 但是要指定多个config类作为参数：
 *          ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class, JdbcConfig.class);
 *
 *      实际开发中，不用上面两种方式，一般使用@Import在主配置类中添加其他配置类
 *
 * @ComponentScan
 *      作用：指定Spring在创建容器时要扫描的包
 *      属性: 有value和basePackages, 同义
 *      代替：<context:component-scan base-package="com.daliu"></context:component-scan>
 *      标准写法应是：basePackages = {"com.daliu"}, 因数组中只有一个元素，可以省略{}
 *
 *  @Bean
 *      作用： 把当前方法返回值作为bean存入spring的ioc容器中
 *      属性：
 *          name: 指定bean的id, 当不写时默认为当前方法的名称，即："createQueryRunner"
 *      当使用注解配置方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象，
 *      查找的方式和Autowired注解的作用是一致的
 *
 * @Import
 *      作用：用于导入其他的配置类
 *      属性：
 *          value: 用于指定其他配置类的字节码，当使用@Import注解后，有@Import注解的类就是主配置类
 *
 * @PropertySource
 *      作用：用于指定properties文件的位置
 *      属性：
 *          value: 指定文件的路径和名称
 *                  关键字：calsspath:表示类路径下
 */
@Configuration
@Import(JdbcConfig.class)
@ComponentScan({"com.daliu", "config"})
@PropertySource("classpath:jdbcConfig.properties") // 如果有包需要写全，比如：classpath:config/daliu/jdbcConfig.properties
public class SpringConfiguration {
}
```

**config.JdbcConfig.java**

```java
package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

/**
 * 和Spring数据库连接相关的配置类
 */
public class JdbcConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /**
     * 创建数据源对象
     */
    @Bean(name="dataSource")
    public DataSource createDataSource() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();

            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(" 💀💀💀💀💀💀💀");
        }
    }

    @Bean(name = "runner") // 使用注解使用方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象，查找的方式和@Autowired一样
    @Scope(value = "prototype")
    public QueryRunner createQueryRunner(DataSource dataSource) {
        return new QueryRunner(dataSource);
    }
}
```

**resources/jdbcConfig.properties**

```
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/daliu?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
jdbc.username=root
jdbc.password=daliu8807
```

**测试**

```java
ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
...
```

### @Qualifier在方法中

```java
package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

/**
 * 和Spring数据库连接相关的配置类
 */
public class JdbcConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /**
     * 创建数据源对象
     */
    @Bean(name="dataSource")
    public DataSource createDataSource() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();

            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(" 💀💀💀💀💀💀💀");
        }
    }

    @Bean(name = "ds1")
    public DataSource createDataSource1() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();

            ds.setDriverClass(driver);
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/daliu_2?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC");
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(" 💀💀💀💀💀💀💀");
        }
    }

    @Bean(name = "runner") // 使用注解使用方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象，查找的方式和@Autowired一样
    @Scope(value = "prototype")
    public QueryRunner createQueryRunner(@Qualifier("dataSource") DataSource dataSource) {
        /**
         * 这个方法中有一个参数是：DataSource dataSource
         * 如果没有@Qualifier, spring默认查找和@Autowired相同，即先会从容器中找类型为DataSource的bean
         * 如果找到多个，那么再会找id为dataSource的bean(因为形参就是dataSource)
         * 如果把此处形参改为(DataSource ds), 那么spring如果从容器中找到了多个类型为DataSource的bean, 则就再去找id为ds的bean
         * 但这种方式并不太好，所以最好指定@Qualifier来指定
         */
        return new QueryRunner(dataSource);
    }
}
```

Demo参见：`./demos/annotation_without_xml_2`