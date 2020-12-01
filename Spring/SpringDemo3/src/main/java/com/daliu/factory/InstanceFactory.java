package com.daliu.factory;

import com.daliu.service.IAccountService;
import com.daliu.service.impl.AccountServiceImpl;

/**
 * 模拟一个工厂类（该类可能存在于jar包中，无法通过修改源码的方式提供默认构造函数）
 */
public class InstanceFactory {
    public IAccountService getAccountService() {
        return new AccountServiceImpl();
    }
}
