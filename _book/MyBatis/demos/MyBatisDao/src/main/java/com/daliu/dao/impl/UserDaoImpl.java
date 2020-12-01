package com.daliu.dao.impl;

import com.daliu.dao.IUserDao;
import com.daliu.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Proxy;
import java.util.List;

public class UserDaoImpl implements IUserDao {
    private SqlSessionFactory factory;
    private SqlSession session;

    public UserDaoImpl(SqlSessionFactory factory) {
        this.factory = factory;
    }

    public List<User> findAll() {
        SqlSession session = factory.openSession();
        List<User> users = session.selectList("com.daliu.dao.IUserDao.findAll"); // 参数就是能获取配置信息的key

        Proxy.newProxyInstance()

        session.close();
        return users;
    }

    public void saveUser(User user) {
        SqlSession session = factory.openSession();
        session.insert("com.daliu.dao.IUserDao.saveUser", user);
        session.commit();
        session.close();
    }

    public void updateUser(User user) {
        SqlSession session = factory.openSession();
        session.update("com.daliu.dao.IUserDao.updateUser", user);
        session.commit();
        session.close();
    }

    public void deleteUser(Integer id) {
        SqlSession session = factory.openSession();
        session.delete("com.daliu.dao.IUserDao.deleteUser", id);
        // session.update("com.daliu.dao.IUserDao.deleteUser", id); // OK
        session.commit();
        session.close();
    }

    public User findById(Integer userId) {
        SqlSession session = factory.openSession();
        User user = session.selectOne("com.daliu.dao.IUserDao.findById", userId);
        session.commit();
        session.close();
        return user;
    }

    public List<User> findByName(String username) {
        SqlSession session = factory.openSession();
        List<User> users = session.selectList("com.daliu.dao.IUserDao.findByName", username);
        session.close();
        return users;
    }

    public List<User> findByName2(String username) {
        return null;
    }

    public int findTotal() {
         SqlSession session = factory.openSession();
         int count = session.selectOne("com.daliu.dao.IUserDao.findTotal");
         return count;
    }
}
