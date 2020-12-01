package com.daliu.service.impl;

import com.daliu.dao.IAccountDao;
import com.daliu.dao.imp.AccountDaoImpl;
import com.daliu.service.IAccountService;

/**
 * 帐户的业户层实现类
 */
public class AccountServiceImpl implements IAccountService {

    // 持久层
    private IAccountDao accountDao = new AccountDaoImpl();

    // 业务层，业务层调用持久层
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
