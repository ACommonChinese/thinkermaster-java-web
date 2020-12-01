package com.daliu.dao;

import com.daliu.domain.User;
import java.util.List;

public interface IUserDao {
    /**
     * 查询所有用户, 同时查询用户下所有的帐户
     * @return
     */
    List<User> findAll();
    User findById(Integer userId);
}