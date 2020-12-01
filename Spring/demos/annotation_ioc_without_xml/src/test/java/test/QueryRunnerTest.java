package test;

import config.SpringConfiguration;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试QueryRunner是否单例
 */
public class QueryRunnerTest {
    @Test
    public void testQueryRunner() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        QueryRunner runner1 = context.getBean("runner", QueryRunner.class);
        QueryRunner runner2 = context.getBean("runner", QueryRunner.class);
        if (runner1 == runner2) {
            System.out.println("Equal!");
        }
        else {
            System.out.println("Not equal！");
        }
    }
}
