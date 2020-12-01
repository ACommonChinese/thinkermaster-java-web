# 基本CRUD

**com.daliu.domain.User.java**

```java
package com.daliu.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer id;
    private String username;
    private String address;
    private String sex;
    private Date birthday;
    // setters and getters
```

**com.daliu.dao.IUserDao.java** 

```java
package com.daliu.dao;

import com.daliu.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有用户
     * @return
     */
    @Select(value = "select * from user") // value = 可以省略
    List<User> findAll();

    /**
     * 保存用户
     * @param user
     */
    @Insert("insert into user(username, address, sex, birthday) values(#{username}, #{address}, #{sex}, #{birthday})")
    void saveUser(User user);

    /**
     * 更新用户
     * @param user
     */
    @Update("update user set username=#{username}, sex=#{sex}, birthday=#{birthday}, address=#{address} where id = #{id}")
    void updateUser(User user);

    /**
     * 删除用户
     * @param userId
     */
    @Delete("delete from user where id=#{id}")
    void deleteUser(Integer userId);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @Select("select * from user where id=#{id}")
    User findById(Integer userId);

    /**
     * 根据用户名称模糊查询
     *
     * OK:
     * @Select("select * from user where username like #{xxx}")
     * 因为只有一个参数, 这个xxx可以随意, 但习惯上和变量名写成一致: username
     * 注: 这样写的话, 外面传入应该带%, 比如: List<User> users = userDao.findUserByName("%王%");
     * 这种是推荐的方式
     */
    // 而采用下面这种写法, 格式是固定的, {value}不可替换成其他字符串
    // 此处仅为演示, 推荐使用上面方式: @Select("select * from user where username like #{xxx}")
    @Select("select * from user where username like '%${value}%'")
    List<User> findUserByName(String username);

    /**
     * 查询总用户数量
     * @return
     */
    @Select("select count(*) from user")
    int findTotalUser();
}
```

**测试**  

```java
package com.daliu.test;

import com.daliu.dao.IUserDao;
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

public class AnnotationCRUDTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IUserDao userDao;

    @Before
    public void init() throws Exception {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        userDao =  session.getMapper(IUserDao.class);
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("mybatis annotation");
        user.setAddress("北京昌平区");
        userDao.saveUser(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(49);
        user.setUsername("update user name");
        user.setAddress("北京海淀区");
        user.setSex("女");
        user.setBirthday(new Date());
        userDao.updateUser(user);
    }

    @Test
    public void testDelete() {
        userDao.deleteUser(49);
        userDao.deleteUser(50);
    }

    @Test
    public void testFindOne() {
        User user = userDao.findById(48);
        System.out.println(user);
    }

    @Test
    public void testFindByName() {
        // List<User> users = userDao.findUserByName("%王%");
        List<User> users = userDao.findUserByName("王");
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindTotal() {
        int total = userDao.findTotalUser();
        System.out.println(total);
    }

    @After
    public void destroy() throws Exception {
        session.commit();
        session.close();
        in.close();
    }
}
```
