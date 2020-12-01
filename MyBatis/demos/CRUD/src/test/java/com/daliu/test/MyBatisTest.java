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
