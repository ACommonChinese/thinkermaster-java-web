package com.daliu.dao;

import com.daliu.domain.User;
import com.daliu.mybatis.annotations.Select;

import java.util.List;

public interface IUserDao {
//    @Select("select * from user")
    @Select("select * from user")
    List<User> findAll();
}
