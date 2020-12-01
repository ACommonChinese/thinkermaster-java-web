package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

/**
 * @Configuration
 *      指定当前类是一个配置类
 *
 * @ComponentScan
 *      作用：指定Spring在创建容器时要扫描的包
 *      属性: 有value和basePackages, 同义
 *      代替：<context:component-scan base-package="com.daliu"></context:component-scan>
 *      标准写法应是：basePackages = {"com.daliu"}, 因数组中只有一个元素，可以省略{}
 *
 *  @Bean
 *      作用： 把当前方法返回值作为bean存入spring的ioc容器中
 *      属性：
 *          name: 指定bean的id, 当不写时默认为当前方法的名称，即："createQueryRunner"
 *      当使用注解配置方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象，
 *      查找的方式和Autowired注解的作用是一致的
 */
@Configuration
@ComponentScan("com.daliu")
public class SpringConfiguration {

    /**
     * 创建数据源对象
     */
    @Bean(name="dataSource")
    public DataSource createDataSource() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass("com.mysql.cj.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/daliu?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC");
            ds.setUser("root");
            ds.setPassword("daliu8807");
            return ds;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(" 💀💀💀💀💀💀💀");
        }
    }

    @Bean(name = "runner") // 使用注解使用方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象，查找的方式和@Autowired一样
    @Scope(value = "prototype")
    public QueryRunner createQueryRunner(DataSource dataSource) {
        return new QueryRunner(dataSource);
    }
}
