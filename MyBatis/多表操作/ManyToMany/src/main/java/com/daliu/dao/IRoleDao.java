package com.daliu.dao;

import com.daliu.domain.Role;

import java.util.List;

public interface IRoleDao {
    //查询所有角色
    List<Role> findAll();
}
