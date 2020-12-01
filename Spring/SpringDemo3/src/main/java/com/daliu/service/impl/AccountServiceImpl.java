package com.daliu.service.impl;

import com.daliu.service.IAccountService;

/**
 * 帐户的业户层实现类
 */
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
        System.out.println("AccountServiceImpl::saveAccount()");
    }

    public void init() {
        System.out.println("初始化方法 AccountServiceImpl::init");
    }

    public void destroy() {
        System.out.println("销毁方法 AccountServiceImpl::destroy");
    }
}
