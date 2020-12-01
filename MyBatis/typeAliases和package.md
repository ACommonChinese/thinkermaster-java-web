# typeAliases和package

### 指定别名  

在dao的xml配置文件中,比如com.daliu.dao.IUserDao.xml: 

```xml
<!--resources/com.daliu.dao.IUserDao.xml-->
<delete id="deleteUser" parameterType="java.lang.Integer"> <!--Integer, Int, int都可以-->
    delete from user where id = #{uid}
</delete>
```

这里面的parameterType可以是java.lang.Integer或Int, int, Integer都可以. 而对于parameterType是类的情况默认必须使用全限定类名: 

```xml
<!--resources/com.daliu.dao.IUserDao.xml-->
<insert id="saveUser" parameterType="com.daliu.domain.User">
    ...
</insert>
```

如果不想使用全限定类名, 可以通过配置别名的方式处理:  

```xml
<!--resources/SqlMapConfig.xml-->
<!--使用typeAliases配置别名，只能配置domain中类的别名-->
<typeAliases>
    <!-- typeAlias用于配置别名，type指定实体类全限定类名，alias指定别名 -->
    <!--配置了别名, 就不再区分大小写, 使用时User, user, USER都可以-->
    <typeAlias type="com.daliu.domain.User" alias="user"></typeAlias>

    <!--用于指定要配置别名的包，当指定之后，该包下的实体类都会注册别名
    并且类名就是别名，不区分大小写-->
    <!--下面这样配置后，该包下的类名就是别名，而且不区分大小写-->
    <package name="com.daliu.domain"></package>
</typeAliases>
```

由于上面使用了typeAlias指定了com.daliu.domain.User的别名是user, 因此, 在dao的xml配置文件中可以使用user代替com.daliu.domain.User:   

```xml
<!--resources/com.daliu.dao.IUserDao.xml-->
<!--parameterType="com.daliu.domain.User"-->
<!--由于配置了别名, 不区分大小写,此处user, User, uSEr都可以-->
<insert id="saveUser" parameterType="user"> 
    ...
</insert>
```

### 使用package指定某一处下的别名

```xml
<!--resources/SqlMapConfig.xml-->
<typeAliases>
    <!-- typeAlias用于配置别名，type指定实体类全限定类名，alias指定别名 -->
    <!--typeAlias type="com.daliu.domain.User" alias="user"></typeAlias>-->
    <!--package用于指定要配置别名的包，当指定之后，该包下的实体类都会注册别名
    并且类名就是别名，不区分大小写-->
    <!--下面这样配置后，该包下的类名就是别名，而且不区分大小写-->
    <package name="com.daliu.domain"></package>
</typeAliases>
```

由于上面的配置, 则com.daliu.domain.User -> 别名就是 User, com.daliu.domain.Xxx -> 别名就是Xxx 且别名不区分分大小写.

### 在mapper中使用package

```xml
<!--resources/SqlMapConfig.xml-->
<!--配置映射配置文件-->
<mappers>
    <!--<mapper resource="com/daliu/dao/IUserDao.xml"></mapper>-->
    <!--用于指定dao接口所在的包
    当指定完成之后，就不需要写mapper以及resource或class-->
    <package name="com.daliu.dao"></package>
</mappers>
```

由于上面配置了package为com.daliu.dao, 则: 
com.daliu.dao.IUserDao -> 就会对应 com/daliu/dao/IUserDao.xml 或 class=com.daliu.dao.IUserDao.java 因此不再需要写mapper的resource或class

注意和typeAlises中的package区分, typeAlises下的package指定的是实体类的别名, 而mapper中的package用于指定dao接口所在的包