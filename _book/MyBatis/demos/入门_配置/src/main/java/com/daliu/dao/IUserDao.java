package com.daliu.dao;

import com.daliu.domain.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有
     * @return
     */
    List<User> findAll();
}
