# 编写dao实现类

Mybatis支持自己编写dao实现类, 这是一种作死的做法, 不推荐使用.

resources/com/daliu/dao/IUserDao.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.daliu.dao.IUserDao">
    <select id="findAll" resultType="com.daliu.domain.User">
        select * from user #查找到一条条记录封装成User对象, 然后返回User数组
    </select>
</mapper>
```



在cn.com.dao下添加UserDaoImpl类:

```java
// -- cn.com.dao.UserDaoImpl.java --
package cn.com.dao;

import cn.com.daliu.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl implements IUserDao {
    private SqlSessionFactory factory;
    public UserDaoImpl(SqlSessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<User> findAll() {
        SqlSession session = this.factory.openSession();
        // 实际上这里取的是命名空间+类名+方法
        // resources/cn/com/dao/IUserDao.xml下配置有: 
        /**
        <mapper namespace="cn.com.dao.IUserDao">
            <select id="findAll" resultType="cn.com.daliu.User">
                select * from user;
            </select>
        </mapper>
        */
        List<User> users = session.selectList("cn.com.dao.IUserDao.findAll");
        session.close();
        return users;
    }
}
```

2. 修改用于测试的主类文件

```java
package cn.com.test;

import cn.com.daliu.User;
import cn.com.dao.IUserDao;
import cn.com.dao.UserDaoImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        // 1. 读取配置文件SqlMapConfig.xml
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2. 创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);

        // 3. 使用工厂创建对象
        IUserDao userDao = new UserDaoImpl(factory);

        // 5. 使用代理对象执行方法
        List<User> users = userDao.findAll();
        System.out.println("====== print all users: =========");
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("=================================");
        in.close();
    }
}
```
