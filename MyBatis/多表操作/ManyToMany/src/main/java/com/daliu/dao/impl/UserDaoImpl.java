package com.daliu.dao.impl;

import com.daliu.dao.IUserDao;
import com.daliu.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl implements IUserDao {
    private SqlSessionFactory factory;

    public UserDaoImpl(SqlSessionFactory factory) {
        this.factory = factory;
    }

    public List<User> findAll() {
        SqlSession session = factory.openSession();
        List<User> users = session.selectList("com.daliu.dao.IUserDao.findAll");
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

    public void deleteUser(Integer userId) {
        SqlSession session = factory.openSession();
        session.update("com.daliu.dao.IUserDao.deleteUser", userId);
        session.commit();
        session.close();
    }

    public User findById(Integer userId) {
        SqlSession session = factory.openSession();
        User user = session.selectOne("com.daliu.dao.IUserDao.findById", userId);
        session.close();
        return user;
    }

    public List<User> findByName(String name) {
        SqlSession session = factory.openSession();
        List<User> users = session.selectList("com.daliu.dao.IUserDao.findByName", name);
        session.close();
        return users;
    }

    public int findTotal() {
        SqlSession session = factory.openSession();
        Integer count = session.selectOne("com.daliu.dao.IUserDao.findTotal");
        session.close();
        return count;
    }
}
