package com.daliu.dao;

import com.daliu.domain.Account;

import java.util.List;

/**
 * 账户的持久层接口
 */
public interface IAccountDao {

    /**
     * 查询所有
     * @return
     */
    List<Account> findAllAccount();

    /**
     * 查询一个
     * @return
     */
    Account findAccountById(Integer accountId);

    /**
     * 保存
     * @param account
     */
    void saveAccount(Account account);

    /**
     * 更新
     * @param account
     */
    void updateAccount(Account account);

    /**
     * 删除
     * @param acccountId
     */
    void deleteAccount(Integer acccountId);

    /**
     * 根据名称查询帐户
     * @param accoutName 帐户名称
     * @return 如果有惟一结果直接返回，如果没有返回null, 如果找到多个抛异常
     */
    Account findAccountByName(String accoutName);
}
