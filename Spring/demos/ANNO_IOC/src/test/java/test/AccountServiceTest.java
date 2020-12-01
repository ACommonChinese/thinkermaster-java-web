package test;

import com.daliu.domain.Account;
import com.daliu.service.IAccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 使用Junit单元测试测试
 */
public class AccountServiceTest {
    public IAccountService getAccountService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService accountService = context.getBean("accountService", IAccountService.class);
        return accountService;
    }

    @Test
    public void testFindAll() {
        IAccountService as = this.getAccountService();
        List<Account> accounts = as.findAllAccount();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindOne() {
        IAccountService as = this.getAccountService();
        Account account = as.findAccountById(1);
        System.out.println(account);
    }

    @Test
    public void testSave() {
        IAccountService as = this.getAccountService();
        Account account = new Account();
        account.setName("大刘");
        account.setMoney(100F);
        as.saveAccount(account);
    }

    @Test
    public void testUpdate() {
        IAccountService as = this.getAccountService();
        Account account = as.findAccountById(1);
        account.setMoney(23456F);
        as.updateAccount(account);
    }

    @Test
    public void testDelete() {
        IAccountService as = this.getAccountService();
        as.deleteAccount(4);
    }
}
