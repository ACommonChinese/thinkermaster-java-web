package com.daliu.test;

import com.daliu.dao.IRoleDao;
import com.daliu.dao.IUserDao;
import com.daliu.domain.Role;
import com.daliu.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private IRoleDao roleDao;

    @Before
    public void init() throws Exception {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession(true);
        userDao = sqlSession.getMapper(IUserDao.class);
        roleDao = sqlSession.getMapper(IRoleDao.class);
    }

    @After
    public void destroy() throws Exception {
        sqlSession.close();
        in.close();
    }

    @Test
    public void testFindAllUser() {
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
            System.out.println(user.getRoles());
        }
    }

    @Test
    public void testFindAllRole() {
        List<Role> roles = roleDao.findAll();
        for (Role role : roles) {
            System.out.println(role);
            System.out.println(role.getUsers());
        }
    }
}
