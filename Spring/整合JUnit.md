# 整合JUnit

junit框架中集成了一个main方法，它找到带有@Test注解的方法并执行。执行测试方法时，junit根本不知道是否使用了spring框架，也就不会读取配置文件创建spring容器。

解决方法：

1. 导入spring整合junit的jar包 
```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-test</artifactId>
  <version>5.0.2.RELEASE</version>
</denpendency>
```

2. 使用junit提供的一个注解把原有的main方法替换掉，替换成spring提供的
@Runwith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {
    ...
}

3. 告知spring的运行器，spring的ioc创建是基于xml还是基于注解，并说明位置
@ContextConfiguration  
- locations: 指定xml文件的位置，加上classpath关键字，表示在类路径下
- classes: 指定注解类所在的位置

```java
@Runwith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
// @ContextConfiguration(locations = "classpath:bean.xml")

public class AccountServiceTest {
      ...
}
```

注：当使用spring 5.x版本时，要求junit jar包必须是4.12及以上版本
