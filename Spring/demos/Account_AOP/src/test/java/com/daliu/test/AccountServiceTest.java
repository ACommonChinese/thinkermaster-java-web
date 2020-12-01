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

