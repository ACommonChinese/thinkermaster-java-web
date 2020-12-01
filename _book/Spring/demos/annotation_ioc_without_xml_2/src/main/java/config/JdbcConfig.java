package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

/**
 * 和Spring数据库连接相关的配置类
 */
public class JdbcConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /**
     * 创建数据源对象
     */
    @Bean(name="dataSource")
    public DataSource createDataSource() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();

            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(" 💀💀💀💀💀💀💀");
        }
    }

    @Bean(name = "ds1")
    public DataSource createDataSource1() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();

            ds.setDriverClass(driver);
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/daliu_2?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC");
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(" 💀💀💀💀💀💀💀");
        }
    }

    @Bean(name = "runner") // 使用注解使用方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象，查找的方式和@Autowired一样
    @Scope(value = "prototype")
    public QueryRunner createQueryRunner(@Qualifier("dataSource") DataSource dataSource) {
        /**
         * 这个方法中有一个参数是：DataSource dataSource
         * 如果没有@Qualifier, spring默认查找和@Autowired相同，即先会从容器中找类型为DataSource的bean
         * 如果找到多个，那么再会找id为dataSource的bean(因为形参就是dataSource)
         * 如果把此处形参改为(DataSource ds), 那么spring如果从容器中找到了多个类型为DataSource的bean, 则就再去找id为ds的bean
         * 但这种方式并不太好，所以最好指定@Qualifier来指定
         */
        return new QueryRunner(dataSource);
    }
}
