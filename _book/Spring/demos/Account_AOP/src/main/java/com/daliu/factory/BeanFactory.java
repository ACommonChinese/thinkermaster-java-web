package com.daliu.factory;

import com.daliu.service.IAccountService;
import com.daliu.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用于创建Service的代理对象的工厂
 */
public class BeanFactory {
    // 由spring注入
    private IAccountService accountService;

    // 由spring注入
    private TransactionManager transactionManager;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * 获取Service代理对象
     * @return
     */
    public IAccountService getAccountService() {
        IAccountService as = (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /**
                 * 添加事务的支持
                 */
                Object returnValue = null;
                try {
                    transactionManager.beginTransaction();
                    returnValue = method.invoke(accountService, args);
                    transactionManager.commit();
                    return returnValue;
                } catch (Exception ex) {
                    transactionManager.rollback();
                    throw new RuntimeException(ex);
                } finally {
                    transactionManager.release();
                }
            }
        });
        return as;
    }
}
