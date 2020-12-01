package com.daliu.mybatis.sqlsession;

import com.daliu.mybatis.cfg.Configuration;
import com.daliu.mybatis.sqlsession.defaults.DefaultSqlSessionFactory;
import com.daliu.mybatis.utils.XMLConfigBuilder;

import java.io.InputStream;

/**
 * 用于创建SqlSessionFactory对象
 */
public class SqlSessionFactoryBuilder {
    /**
     * 根据配置文件(SqlMapConfig.xml)创建SqlSessionFactory工厂
     * @param config 配置文件流
     * @return
     */
    public SqlSessionFactory build(InputStream config) {
        Configuration cfg = XMLConfigBuilder.loadConfiguration(config);
        return new DefaultSqlSessionFactory(cfg);
    }
}
