# 基于注解的IOC案例

在基于XML的IOC案例的基础上，只需做简单改动：

代码示例：`./demos/ANNO_IOC`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!--告知spring在创建容器时要扫描的包-->
    <context:component-scan base-package="com.daliu"></context:component-scan>

    <!--这里配置成prototype-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
        <!--注入数据源-->
        <constructor-arg name="ds" ref="dataSource">
        </constructor-arg>
    </bean>

    <!--配置数据源-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/daliu?characterEncoding=utf-8&amp;useSSL=false&amp;serverTimezone=UTC"></property>
        <property name="user" value="root"></property>
        <property name="password" value="daliu8807"></property>
    </bean>
</beans>
```

```java
// com.daliu.dao.impl.AccountDaoImpl.java
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {
    @Autowired
    private QueryRunner runner;
...
```

```java
// com.daliu.service.impl.AccountServiceImpl.java
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    /// 业务层调用持久层
    @Autowired
    private IAccountDao accountDao;
    ...
```