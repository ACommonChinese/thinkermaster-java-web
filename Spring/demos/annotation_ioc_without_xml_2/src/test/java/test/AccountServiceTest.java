package test;

import com.daliu.domain.Account;
import com.daliu.service.IAccountService;
import config.SpringConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * 使用Junit单元测试测试
 */
public class AccountServiceTest {
    private IAccountService as;
    private ApplicationContext context;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        as = context.getBean("accountService", IAccountService.class);
    }

    @Test
    public void testFindAll() {
        List<Account> accounts = as.findAllAccount();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindOne() {
        Account account = as.findAccountById(1);
        System.out.println(account);
    }

    @Test
    public void testSave() {
        Account account = new Account();
        account.setName("大刘");
        account.setMoney(100F);
        as.saveAccount(account);
    }

    @Test
    public void testUpdate() {
        Account account = as.findAccountById(1);
        account.setMoney(23456F);
        as.updateAccount(account);
    }

    @Test
    public void testDelete() {
        as.deleteAccount(4);
    }
}
