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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBatisTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private SqlSessionFactory factory;

    @Before
    public void init() throws Exception {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    public void destory() throws Exception {
        sqlSession.commit();
        sqlSession.close();
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
    public void testFindByConditionIf() {
        User user = new User();
        user.setUsername("老王");
        // user.setSex("女");
        List<User> users = userDao.findUserByConditionIf(user);
        for (User u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void testFindByConditionWhere() {
        User user = new User();
        user.setUsername("老王");
        // user.setSex("女");
        List<User> users = userDao.findUserByConditionWhere(user);
        for (User u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void findUserInIds() {
        QueryVo vo = new QueryVo();
        vo.setIds(new ArrayList<Integer>(Arrays.asList(1, 2, 3)));
        List<User> users = userDao.findUserInIds(vo);
        for (User user : users) {
            System.out.println(user);
        }
    }
}