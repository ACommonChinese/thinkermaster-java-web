package com.daliu.service;

import com.daliu.domain.Account;

import java.util.List;

/**
 * 帐户的业务层接口
 */
public interface IAccountService {
    /// 查询所有
    List<Account> findAllAccount();
    /// 查询一个
    Account findAccountById(Integer accountId);
    /// 保存
    void saveAccount(Account account);
    /// 更新
    void updateAccount(Account account);
    /// 删除
    void deleteAccount(Integer accountId);
}
