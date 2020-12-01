package com.daliu.test;

import com.daliu.dao.IUserDao;
import com.daliu.dao.impl.UserDaoImpl;
import com.daliu.domain.User;
import org.apache.ibatis.io.Resources;
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
    private IUserDao userDao;

    @Before
    public void init() throws Exception {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        userDao = new UserDaoImpl(factory);
    }

    @After
    public void destory() throws Exception {
        in.close();
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testSave() {
        User user = new User("钟山燕", "女", "上海市青浦区", new Date());
        userDao.saveUser(user);
        System.out.println(user);
    }

    @Test
    public void testUpdate() {
        User user = new User("钟山燕", "女", "海南省三亚市", new Date());
        user.setId(12);
        userDao.updateUser(user);
    }

    @Test
    public void testDelete() {
        userDao.deleteUser(10);
    }

    @Test
    public void testFindOne() {
        User user = userDao.findById(2);
        System.out.println(user);
    }

    @Test
    public void testFindByName() {
        List<User> users = userDao.findByName("%三%");
        for (User user :
                users) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindTotal() {
        int count = userDao.findTotal();
        System.out.println(count);
    }
}
