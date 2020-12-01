package com.daliu;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Hello {
    public Hello() {
        System.out.println("hello构造函数执行");
    }

    public void sayHello() {
        System.out.println("Hello world!");
    }

    public static void main(String[] args) {
//        // ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("file:///Users/liuweizhen/Documents/mygit/gitbook-daliu-javaweb/Spring/springDemo/src/main/resources/bean.xml");
//        // Hello hello = (Hello) context.getBean("hello");
//        Hello hello = context.getBean("hello", Hello.class);
//        hello.sayHello();
        Resource resource = new ClassPathResource("bean.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
        System.out.println("11111");
        Hello hello = factory.getBean("hello", Hello.class);
        System.out.println("22222");
        hello.sayHello();
    }
}
