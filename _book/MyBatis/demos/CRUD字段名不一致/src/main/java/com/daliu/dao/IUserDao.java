package com.daliu.dao;

import java.util.List;

import com.daliu.domain.QueryVo;
import com.daliu.domain.User;

public interface IUserDao {
    /// 查询全部
    List<User> findAll();
    /// 保存User
    void saveUser(User user);
    /// MySql自增主键的返回值
    int saveUser2(User user);
    /// 更新User
    void updateUser(User user);
    /// 删除User
    void deleteUser(Integer id);
    /// 根据id查询User
    User findById(Integer userId);
    /// 根据名称模糊查询
    List<User> findByName(String username);
    /// 根据名称模糊查询不常用的写法
    List<User> findByName2(String username);
    /// 聚合函数，查询总用户数
    int findTotal();
    /// 传递对象作为查询条件, 根据QueryVo中的条件查询用户
    List<User> findUserByVo(QueryVo vo);
    /// 传递对象作为查询条件, 根据QueryVo中的条件查询用户
    List<User> findUserByVo2(QueryVo vo);
}
