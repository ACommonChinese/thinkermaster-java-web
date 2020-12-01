package com.daliu.service.impl;

import com.daliu.dao.IAccountDao;
import com.daliu.domain.Account;
import com.daliu.service.IAccountService;
import com.daliu.utils.TransactionManager;

import java.util.List;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    /// spring注入
    private IAccountDao accountDao;

    /// spring注入 事务管理
    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAllAccount() {
        try {
            // 开启事务
            transactionManager.beginTransaction();;
            // 执行操作
            List<Account> accounts = accountDao.findAllAccount();
            // 操作事务
            transactionManager.commit();
            // 返回结果
            return accounts;
        } catch (Exception ex) {
            // 回滚操作
            transactionManager.rollback();
            throw new RuntimeException(ex);
        } finally {
            // 释放连接
            transactionManager.release();
        }
    }

    @Override
    public Account findAccountById(Integer accountId) {
        try {
            // 开启事务
            transactionManager.beginTransaction();;
            // 执行操作
            Account account = accountDao.findAccountById(accountId);
            // 操作事务
            transactionManager.commit();
            // 返回结果
            return account;
        } catch (Exception ex) {
            // 回滚操作
            transactionManager.rollback();
            throw new RuntimeException(ex);
        } finally {
            // 释放连接
            transactionManager.release();
        }
    }

    @Override
    public void saveAccount(Account account) {
        try {
            // 开启事务
            transactionManager.beginTransaction();;
            // 执行操作
            saveAccount(account);
            // 操作事务
            transactionManager.commit();
        } catch (Exception ex) {
            // 回滚操作
            transactionManager.rollback();
        } finally {
            // 释放连接
            transactionManager.release();
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            // 开启事务
            transactionManager.beginTransaction();;
            // 执行操作
            accountDao.updateAccount(account);
            // 操作事务
            transactionManager.commit();
        } catch (Exception ex) {
            // 回滚操作
            transactionManager.rollback();
        } finally {
            // 释放连接
            transactionManager.release();
        }
    }

    @Override
    public void deleteAccount(Integer acccountId) {
        try {
            // 开启事务
            transactionManager.beginTransaction();;
            // 执行操作
            accountDao.deleteAccount(acccountId);
            // 操作事务
            transactionManager.commit();
        } catch (Exception ex) {
            // 回滚操作
            transactionManager.rollback();
        } finally {
            // 释放连接
            transactionManager.release();
        }
    }

    @Override
    public void transfer(String sourceName, String targetName, Float money) {
        try {
            // 开启事务
            transactionManager.beginTransaction();
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
            // 操作事务
            transactionManager.commit();
        } catch (Exception ex) {
            // 回滚操作
            transactionManager.rollback();
            ex.printStackTrace();
        } finally {
            // 释放连接
            transactionManager.release();
        }
    }
}
