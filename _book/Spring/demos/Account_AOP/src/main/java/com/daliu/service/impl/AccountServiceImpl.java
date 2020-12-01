package com.daliu.service.impl;

import com.daliu.dao.IAccountDao;
import com.daliu.domain.Account;
import com.daliu.service.IAccountService;

import java.util.List;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    /// spring注入
    private IAccountDao accountDao;

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Integer accountId) {
        return accountDao.findAccountById(accountId);
    }

    @Override
    public void saveAccount(Account account) {
        saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Integer acccountId) {
        accountDao.deleteAccount(acccountId);
    }

    @Override
    public void transfer(String sourceName, String targetName, Float money) {
        System.out.println("transfer...");
        // 执行操作
        // 1. 根据名称查询转出帐户
        Account source = accountDao.findAccountByName(sourceName);
        // 2. 根据名称查询转入帐户
        Account target = accountDao.findAccountByName(targetName);
        // 3. 转出帐户减钱
        source.setMoney(source.getMoney() - money);
        // 4. 转入帐户加钱
        target.setMoney(target.getMoney() + money);
        // 5. 更新转出帐户
        accountDao.updateAccount(source);
        // 6. 更新转入帐户
        accountDao.updateAccount(target);
    }
}
