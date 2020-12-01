package com.daliu.dao.imp;

import com.daliu.dao.IAccountDao;

/**
 * 帐户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao {
    public void saveAccount() {
        System.out.println("保存了帐户");
    }
}
