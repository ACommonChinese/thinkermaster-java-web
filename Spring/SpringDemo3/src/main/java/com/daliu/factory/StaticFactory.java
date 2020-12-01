package com.daliu.factory;

import com.daliu.service.IAccountService;
import com.daliu.service.impl.AccountServiceImpl;

public class StaticFactory {
    public static IAccountService getAccountService() {
        return new AccountServiceImpl();
    }
}
