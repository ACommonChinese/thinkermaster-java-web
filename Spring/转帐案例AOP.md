# 转帐案例AOP

上一文[转帐案例](./转帐案例.md)中数据库事务操作有些烦琐：

```java
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
```

上面真正的业务代码就是`accountDao.findAllAccount()`, 其他的就是围绕着这个代码做的事务操作，像这种在“做一件事的前和后有统一的额外操作的代码”可以使用AOP的机制在不影响源码的基础上做增强。

修改上一文转帐案例中的代码如下：

Demo参考： `./demos/Account_AOP`

**com.daliu.service.AccountServiceImpl.java**

下面这个类的执行方法前后没有了transaction的代码。

```java
package com.daliu.service.impl;

import com.daliu.dao.IAccountDao;
import com.daliu.domain.Account;
import com.daliu.service.IAccountService;

import java.util.List;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    /// spring注入
    private IAccountDao accountDao;

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Integer accountId) {
        return accountDao.findAccountById(accountId);
    }

    @Override
    public void saveAccount(Account account) {
        saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Integer acccountId) {
        accountDao.deleteAccount(acccountId);
    }

    @Override
    public void transfer(String sourceName, String targetName, Float money) {
        System.out.println("transfer...");
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
    }
}
```

**com.daliu.factory.BeanFactory.java**

```java
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
```

**resources/bean.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置代理的service对象-->
    <!--注：
    proxyAccountService和accountService都实现了IAccountService接口
    -->
    <bean id="proxyAccountService" factory-bean="beanFactory" factory-method="getAccountService">
    </bean>

    <!--配置BeanFactory-->
    <bean id="beanFactory" class="com.daliu.factory.BeanFactory">
        <!--注入service-->
        <property name="accountService" ref="accountService"></property>
        <!--注入事务管理器-->
        <property name="transactionManager" ref="transactionManager"></property>
    </bean>

    <!-- 配置Service -->
    <bean id="accountService" class="com.daliu.service.impl.AccountServiceImpl">
        <!-- 注入dao -->
        <property name="accountDao" ref="accountDao"></property>
    </bean>

    <!--配置Dao对象-->
    <bean id="accountDao" class="com.daliu.dao.impl.AccountDaoImpl">
        <!-- 注入QueryRunner -->
        <property name="runner" ref="runner"></property>
        <!--注入ConnectionUtils-->
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>

    <!--配置QueryRunner-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
        <!--注入数据源-->
        <!-- <constructor-arg name="ds" ref="dataSource"></constructor-arg>-->
    </bean>

    <!-- 配置数据源 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/daliu?characterEncoding=utf-8&amp;useSSL=false&amp;serverTimezone=UTC"></property>
        <property name="user" value="root"></property>
        <property name="password" value="daliu8807"></property>
    </bean>

    <!--配置ConnectionUtils-->
    <bean id="connectionUtils" class="com.daliu.utils.ConnectionUtils">
        <!--注入数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>

    <!--配置事务管理器-->
    <bean id="transactionManager" class="com.daliu.utils.TransactionManager">
        <!--注入ConnectionUtils-->
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>
</beans>
```

**test/java/com.daliu.test.AccountServiceTest.java**

```java
package com.daliu.test;

import com.daliu.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * 使用Junit单元测试：测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {
    // 也可以使用：
    // @Autowired
    // @Qualifier("proxyAccountService")
    @Resource(name = "proxyAccountService")
    private IAccountService as;

    @Test
    public void testTransfer() {
        as.transfer("bbb", "aaa", 100F);
    }
}
```