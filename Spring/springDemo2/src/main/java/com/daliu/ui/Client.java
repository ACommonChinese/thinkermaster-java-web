package com.daliu.ui;

import com.daliu.factory.BeanFactory;
import com.daliu.service.IAccountService;
import com.daliu.service.impl.AccountServiceImpl;

/**
 * 表现层，调用业务层
 */
public class Client {
    public static void main(String[] args) {
        // IAccountService service = new AccountServiceImpl();
        IAccountService service = (IAccountService) BeanFactory.getBean("accountService");
        service.saveAccount();
    }
}
