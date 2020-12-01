package cn.daliu.service;

import cn.daliu.domain.PageBean;
import cn.daliu.domain.User;
import java.util.List;
import java.util.Map;

/**
 * - com.daliu.servlet 网络请求 --> 调用业务层:
 * - com.daliu.service 业务 --> 调用数据库Dao层:
 * - com.daliu.dao     数据库操作
 * - com.daliu.util    小工具, 比如JDBCUtils
 */
/**
 * 用户管理的业务接口
 */
public interface UserService {

    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();

    /**
     * 登录方法
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 保存User
     * @param user
     */
    void addUser(User user);

    /**
     * 根据id删除User
     * @param id
     */
    void deleteUser(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * 修改用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 批量删除用户
     * @param ids
     */
    void delSelectedUser(String[] ids);

    /**
     * 分页条件查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}

