package com.daliu.dao;

import com.daliu.domain.User;

import java.util.List;

public interface IUserDao {
    List<User> findAll();
}
