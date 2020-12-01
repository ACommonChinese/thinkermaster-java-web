package com.daliu.dao;

import java.util.List;
import com.daliu.domain.User;

public interface IUserDao {
    /// 查询全部
    List<User> findAll();
    /// 保存User
    void saveUser(User user);
    /// 更新User
    void updateUser(User user);
    /// 删除User
    void deleteUser(Integer id);
    /// 根据id查询User
    User findById(Integer userId);
    /// 根据名称模糊查询
    List<User> findByName(String username);
    /// 聚合函数，查询总用户数
    int findTotal();
}
