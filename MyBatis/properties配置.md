# properties配置

在resources下的SqlMapConfig.xml文件可配置properties标签:  

```xml
<!--resources/SqlMapConfig.xml-->

<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE configuration
        PUBLIC "-//mybatis.org/DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties>
        <!--配置连接数据库的4个基本信息-->
        <property name="driver" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/daliu"/>
        <property name="username" value="root"/>
        <property name="password" value="110"/>
    </properties>

    <environments default="mysql">
        <!--主配置环境，全局配置环境-->
        <environment id="mysql">
            <!--配置事务类型-->
            <transactionManager type="JDBC"></transactionManager>
            <!--配置数据源，即连接池-->
            <dataSource type="POOLED">
                <!--&lt;!&ndash;配置连接数据库的4个基本信息&ndash;&gt;-->
                <!--<property name="driver" value="com.mysql.jdbc.Driver"/>-->
                <!--<property name="url" value="jdbc:mysql://localhost:3306/daliu"/>-->
                <!--<property name="username" value="root"/>-->
                <!--<property name="password" value="110"/>-->
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

    <!--配置映射文件的位置-->
    <mappers>
        <mapper resource="com/daliu/dao/IUserDao.xml"></mapper>
    </mappers>
</configuration>
```

上面我们把配置连接数据库的4个基本信息放在properties标签中, 这里面properties标签中的内容也完全可以放在一个单独的文件中, 比如:  

```
// resources/jdbcConfig.properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/daliu
jdbc.username=root
jdbc.password=110
```

引入这个外部配置文件  

```xml
<!--resources/SqlMapConfig.xml-->
<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE configuration
        PUBLIC "-//mybatis.org/DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--resources属性用于配置文件的位置，按照类路径写法来写，并且必须存在于类路径下-->
    <properties resource="jdbcConfig.properties">
    </properties>

    <environments default="mysql">
        <!--主配置环境，全局配置环境-->
        <environment id="mysql">
            <!--配置事务类型-->
            <transactionManager type="JDBC"></transactionManager>
            <!--配置数据源，即连接池-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!--配置映射文件的位置-->
    <mappers>
        <mapper resource="com/daliu/dao/IUserDao.xml"></mapper>
    </mappers>
</configuration>
```

上面使用了resource标识文件: 

```xml
<!--resources属性：用于配置文件的位置，按照类路径写法来写，并且必须存在于类路径下-->
    <!--url属性：要求按照URL的写法来写地址，Uniform Resource Locator 统一资源定位符，可以唯一标识一个资源的位置
    http://localhost:8080/myserver/demo
    协议    主机     端口    URI(Uniform Resource Identifier: 统一资源标识符，它是在应用中可以唯一定位一个资源)
-->
<properties resource="jdbcConfig.properties" >
</properties>
```

也可以使用url来标识:  

```xml
<properties url="file:///Users/liuweizhen/Documents/mygit/gitbook-daliu-javaweb/MyBatis/demos/MyBatisDao/src/main/resources/jdbcConfig.properties">
</properties>
```

注: resource和url不可共存.  

