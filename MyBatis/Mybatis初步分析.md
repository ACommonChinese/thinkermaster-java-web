# Mybatis初步分析

依照前面的示例, Mybatis在使用代理dao实现CRUD时主要两件事:  
1. 创建代理对象
2. 在代理对象中调用selectList方法

首先, 在SqlMapConfig.xml作了配置:

```xml
<dataSource type="POOLED">
    <!--配置连接数据库的4个基本信息-->
    <property name="driver" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/daliu"/>
    <property name="username" value="root"/>
    <property name="password" value="110"/>
</dataSource>

<!--有了上面这些信息Mybatis就可以创建Connection对象.-->

<!--有了下面信息, Mybatis就可以去哪里找映射配置信息-->
<mappers>
    <mapper resource="cn/com/dao/IUserDao.xml" />
</mappers>
```

再来看resources/cn/com/dao/IUserDao.xml:

```xml
<mapper>
    <select id="findAll" resultType="cn.com.daliu.User">
    </select>
</mapper>
```
这个是重点, 有了它,就有了要执行的SQL语句, 就可以创建PreparedStatement, 此配置中还有封装的实体类全限定类名. 当查询到结果时, 把每一行的数据填充到cn.com.daliu.User这个java bean中.

有了以上配置文件, Mybatis就可以读取配置文件, 解析XML(比如使用dom4j解析), 并且:

1. 根据`SqlMapConfig`配置文件创建`Connection`对象, 注册驱动, 获取连接
2. 根据`resources/cn/com/dao/IUserDao.xml`中的配置信息创建预处理对象`PreparedStatement`并关联这个配置信息中的sql语句
3. 执行查询 `ResultSet resultSet = preparedStatement.executeQuery();`
4. 遍历结果集resultSet:
```java
List<E> list = new ArrayList<>();
while (resultSet.next()) {
    <!--这里的E就是配置文件中的resultType-->
    <!--<select id="findAll" resultType="cn.com.daliu.User">-->
    E element = (E)Class.forName(配置的全限定类名).newInstance();
    查询内容并放入element中; 
    list.add(element);
} 
return list;
````





