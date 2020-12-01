package com.daliu.ui;

import com.daliu.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 表现层，调用业务层
 */
public class Client {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService accountService2 = (IAccountService) context.getBean("accountService"); // IAccountService.class
        accountService2.saveAccount();
        // 手动销毁容器
        context.close();
    }
}
