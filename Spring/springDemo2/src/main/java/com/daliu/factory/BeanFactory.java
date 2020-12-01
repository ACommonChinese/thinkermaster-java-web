package com.daliu.factory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 创建bean对象的工厂
 * 创建service和dao对象
 *
 * 1. 需要配置文件配置service和dao
 * 2. 通过读取配置文件，反射创建对象
 */
public class BeanFactory {
    private static Properties props;

    // 定义一个Map，用于存放我们要创建的对象，称之为容器
    private static Map<String, Object> beans;

    static {
        try {
            props = new Properties();
            // InputStream in = new FileInputStream()
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            props.load(in);
            beans = new HashMap<String, Object>();
            // 取出配置文件中的所有key, 得到枚举对象
            Enumeration keys = props.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement().toString();
                String beanPath = props.getProperty(key);
                Object value = Class.forName(beanPath).newInstance();
                beans.put(key, value);
            }
        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError("初始化properties失败");
        }
    }

    /**
     * 根据bean的名称获取bean对象
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return beans.get(beanName);
    }
}
