# CRUD

Create New Project > Maven > GoupId(com.daliu) ArtifaceId(CRUD) > OK

项目: `./demos/CRUD`

```xml
<!--pom.xml-->
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.daliu</groupId>
    <artifactId>CRUD</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.5</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.6</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.12</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.10</version>
        </dependency>
    </dependencies>
</project>
```

```xml
<!--resources/SqlMapConfig.xml-->
<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE configuration
        PUBLIC "-//mybatis.org/DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="mysql">
        <!--主配置环境，全局配置环境-->
        <environment id="mysql">
            <!--配置事务类型-->
            <transactionManager type="JDBC"></transactionManager>
            <!--配置数据源，即连接池-->
            <dataSource type="POOLED">
                <!--配置连接数据库的4个基本信息-->
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/daliu"/>
                <property name="username" value="root"/>
                <property name="password" value="110"/>
            </dataSource>
        </environment>
    </environments>

    <!--配置映射文件的位置-->
    <mappers>
        <mapper resource="com/daliu/dao/IUserDao.xml"></mapper>
    </mappers>
</configuration>
```

```xml
<!--com.daliu.dao.IUserDao.xml-->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org/DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.daliu.dao.IUserDao">
    <!--查询所有-->
    <select id="findAll" resultType="com.daliu.domain.User">
        select * from user;
    </select>
    <!--保存-->
    <insert id="saveUser" parameterType="com.daliu.domain.User">
      insert into user(username, address, sex, birthday) values(#{username}, #{address}, #{sex}, #{birthday})
      <!--注：#{address}默认执行的是getAddress()方法-->
    </insert>
    <!--新增用户返回自增长主键值-->
    <insert id="saveUser2" parameterType="com.daliu.domain.User">
        <!--keyProperty代表要返回的列的名称 order:取值为AFTER-->
        <selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer">
          select last_insert_id();
        </selectKey>
        insert into user(username, birthday, sex, address) values(#{username}, #{birthday}, #{sex}, #{address})
        <!--mysql中insert一条记录后可以通过select last_insert_id查询到自增长的主键id
insert into user(username, birthday, sex, address) values('韩三', '2020-01-23', '男', '北京大兴');
select last_insert_id();-->
    </insert>
    <!--更新-->
    <update id="updateUser" parameterType="com.daliu.domain.User">
      update user set username = #{username}, address = #{address}, sex = #{sex}, birthday = #{birthday} where id = #{id}
    </update>
    <!--删除用户-->
    <delete id="deleteUser" parameterType="java.lang.Integer"> <!--Integer, Int, int都可以-->
      delete from user where id = #{uid}
        <!--这里的{}中的uid只是起到占位符的意义，写什么都可以
        注：dao方法中deleteuser(Integer userId)中只有一个参数，而且是基本类型或基本类型包装类型，这种情况下占位符可以随意写-->
    </delete>
    <!--根据id查询用户-->
    <select id="findById" parameterType="int" resultType="com.daliu.domain.User">
      select * from user where id = #{uid}
    </select>
    <!--根据名称模糊查询-->
    <select id="findByName" parameterType="string" resultType="com.daliu.domain.User">
        select * from user where username like #{name}
        <!--mybatis翻译为: select * from user where username like ?-->
    </select>
    <!--不常用方法：根据名称模糊查询-->
    <select id="findByName2" parameterType="string" resultType="com.daliu.domain.User">
        <!-- select * from user where username like #{name} -->
        <!-- 这种方法使用时传findByName2("龙") -->
        select * from user where username like '%${value}%'
        <!--mybatis翻译为: select * from user where username like '%龙'-->
    </select>
    <!--聚合函数，查询总用户数-->
    <select id="findTotal" resultType="int">
        select count(id) from user
    </select>
    <!--根据queryVo中的条件查询用户-->
    <select id="findUserByVo" parameterType="com.daliu.domain.QueryVo" resultType="com.daliu.domain.User">
      select * from user where username like #{user.username}
    </select>
    <!--根据queryVo中的条件查询用户2-->
    <select id="findUserByVo2" parameterType="com.daliu.domain.QueryVo" resultType="com.daliu.domain.User">
        select * from user where username like #{nameContainsDragon} <!--根据OGNL规范，这里相当于调用方法: voObj.getNameContainsDragon()-->
    </select>
</mapper>
```

```java
// com.daliu.dao.IUserDao.java
package com.daliu.dao;

import java.util.List;

import com.daliu.domain.QueryVo;
import com.daliu.domain.User;

public interface IUserDao {
    /// 查询全部
    List<User> findAll();
    /// 保存User
    void saveUser(User user);
    /// MySql自增主键的返回值
    int saveUser2(User user);
    /// 更新User
    void updateUser(User user);
    /// 删除User
    void deleteUser(Integer id);
    /// 根据id查询User
    User findById(Integer userId);
    /// 根据名称模糊查询
    List<User> findByName(String username);
    /// 根据名称模糊查询不常用的写法
    List<User> findByName2(String username);
    /// 聚合函数，查询总用户数
    int findTotal();
    /// 传递对象作为查询条件, 根据QueryVo中的条件查询用户
    List<User> findUserByVo(QueryVo vo);
    /// 传递对象作为查询条件, 根据QueryVo中的条件查询用户
    List<User> findUserByVo2(QueryVo vo);
}
```

```java
// com.daliu.domain.User
package com.daliu.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer id;
    private String username;
    private String sex;
    private String address;
    private Date birthday;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", username=" + username
                + ", address=" + address
                + ", sex=" + sex
                + ", birthday=" + birthday
                + "}";
    }
}
```

```java
// com.daliu.domain.QueryVo.java
package com.daliu.domain;

public class QueryVo {
    private User user;

    public String getNameContainsDragon() {
        return "%龙%";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
```

```java
// com.daliu.test.MyBatisTest.java
package com.daliu.test;

import com.daliu.dao.IUserDao;
import com.daliu.domain.QueryVo;
import com.daliu.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MyBatisTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private SqlSessionFactory factory;

    @Before
    /**
     * 在测试方法执行前执行
     * 初始化操作
     */
    public void init() throws Exception {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    /**
     * 在测试方法执行后执行
     * 销毁资源
     */
    public void destory() throws Exception {
        // JDBC事务默认是开启的，但MyBatis框架是关闭的，需要显式提交事务
        // Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBCConnection...]
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void testFindAll() throws Exception {
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
        /**
         User{id=1, username=刘威振, address=河南郑州, sex=1, birthday=Mon Feb 01 08:23:12 CST 1988}
         User{id=2, username=李小龙, address=香港, sex=1, birthday=Thu Feb 01 07:23:12 CST 1968}
         User{id=3, username=李连杰, address=山东烟台, sex=1, birthday=Wed Feb 01 07:23:22 CST 1967}
         User{id=4, username=成龙, address=四川成都, sex=1, birthday=Wed Feb 01 02:23:22 CST 1967}
         User{id=5, username=刘德华, address=湖北武汉, sex=1, birthday=Mon Feb 01 01:23:22 CST 1937}
         */
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("王小二");
        user.setAddress("河南省郑州市");
        user.setSex("男");
        user.setBirthday(new Date());
        userDao.saveUser(user);
    }

    @Test
    public void testSaveUser2() {
        User user = new User();
        user.setUsername("老张");
        user.setSex("男");
        user.setBirthday(new Date());
        user.setAddress("上海");
        userDao.saveUser2(user);
        System.out.println(user); // User{id=10, username=老张, address=上海, sex=男, birthday=Sun Nov 17 17:15:46 CST 2019}
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(7);
        user.setUsername("张三丰");
        user.setAddress("河南省商丘市");
        user.setSex("女");
        user.setBirthday(new Date());
        userDao.updateUser(user);

    }

    @Test
    public void testDelete() {
        userDao.deleteUser(5);
    }

    @Test
    public void testFindOne() {
        User user = userDao.findById(2);
        System.out.println(user);
    }

    @Test
    public void testFindByName() {
        /// 查询名字中包含"龙"的user
        List<User> users = userDao.findByName("%龙%");
        for (User user : users) {
            System.out.println(user);
        }
        /**
         User{id=2, username=李小龙, address=香港, sex=1, birthday=Thu Feb 01 07:23:12 CST 1968}
         User{id=4, username=成龙, address=四川成都, sex=1, birthday=Wed Feb 01 02:23:22 CST 1967}
         */
    }

    @Test
    public void testFindByName2() {
        List<User> users = userDao.findByName2("龙"); // %龙%
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindTotal() {
        int count = userDao.findTotal();
        System.out.println("一共有" + count + "条记录");
    }

    @Test
    public void testFindByQueryVo() {
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("%龙%");
        vo.setUser(user);
        List<User> users = userDao.findUserByVo(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void testFindByQueryVo2() {
        QueryVo vo = new QueryVo();
        List<User> users = userDao.findUserByVo2(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }
}
```

模糊查询的一个细节:  
![](./images/7.png)