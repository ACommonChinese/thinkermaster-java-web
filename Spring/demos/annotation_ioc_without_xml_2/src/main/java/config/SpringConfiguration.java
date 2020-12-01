package config;

import org.springframework.context.annotation.*;

/**
 * @Configuration
 *      作用：指定当前类是一个配置类
 *      细节：当配置类作为AnnotationConfigApplicationContext对象创建的参数时，该注解可以省略不写，比如：
 *      ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
 *      由于这个参数是惟一的，即，SpringConfiguration.class, 因此，这里的@Configuration可以省略
 *      但是，如果有多个config，要么在类前加@CompoenentScan, 要么指定多个config的class, 即如下两种做法都可以
 *      1.  @Configuration
 *          @ComponentScan({"com.daliu", "config"})
 *          public class SpringConfiguration { ... }
 *
 *          @Configuration
 *          public class JdbcConfig { ... }
 *
 *          ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
 *
 *       2. JdbcConfig前无@Configuration, 但是要指定多个config类作为参数：
 *          ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class, JdbcConfig.class);
 *
 *      实际开发中，不用上面两种方式，一般使用@Import在主配置类中添加其他配置类
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
 *
 * @Import
 *      作用：用于导入其他的配置类
 *      属性：
 *          value: 用于指定其他配置类的字节码，当使用@Import注解后，有@Import注解的类就是主配置类
 *
 * @PropertySource
 *      作用：用于指定properties文件的位置
 *      属性：
 *          value: 指定文件的路径和名称
 *                  关键字：calsspath:表示类路径下
 */
@Configuration
@Import(JdbcConfig.class)
@ComponentScan({"com.daliu", "config"})
@PropertySource("classpath:jdbcConfig.properties") // 如果有包需要写全，比如：classpath:config/daliu/jdbcConfig.properties
public class SpringConfiguration {
}
