package com.daliu.ui;

import com.daliu.dao.IAccountDao;
import com.daliu.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 表现层，调用业务层
 */
public class Client {
    public static void main(String[] args) {
        // 1. 获取核心容器对象
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        // 2. 根据id获取bean对象
        IAccountService accountService = (IAccountService) context.getBean("accountService");
        IAccountDao accountDao = context.getBean("accountDao", IAccountDao.class);
        System.out.println(accountService);
        System.out.println(accountDao);
    }
}
