package com.daliu.test;

import com.daliu.dao.IUserDao;
import com.daliu.domain.User;
import com.daliu.mybatis.io.Resources;
import com.daliu.mybatis.sqlsession.SqlSession;
import com.daliu.mybatis.sqlsession.SqlSessionFactoryBuilder;
import com.daliu.mybatis.sqlsession.SqlSessionFactory;

import java.io.InputStream;
import java.util.List;

public class MyBatisTest {

    /**
     * 入门案例
     * @param args
     */
    public static void main(String[] args)throws Exception {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println(user);
        }
        //6.释放资源
        session.close();
        in.close();
    }
}