package com.daliu.dao;

import java.util.List;

import com.daliu.domain.QueryVo;
import com.daliu.domain.User;

public interface IUserDao {
    /// 查询全部
    List<User> findAll();
    /// 根据id查询User
    User findById(Integer userId);
    /// 根据名称模糊查询
    List<User> findByName(String username);
    /// 根据queryVo中的条件查询用户
    List<User> findUserByVo(QueryVo vo);
    /// 根据传入的参数条件查询, if
    List<User> findUserByConditionIf(User user);
    /// 根据传入的参数条件查询, where
    List<User> findUserByConditionWhere(User user);
    /// 根据传入的参数条件查询, foreach
    List<User> findUserInIds(QueryVo queryVo);
}
