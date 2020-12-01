package com.daliu.mybatis.sqlsession.defaults;

import com.daliu.mybatis.cfg.Configuration;
import com.daliu.mybatis.sqlsession.SqlSession;
import com.daliu.mybatis.sqlsession.proxy.MapperProxy;
import com.daliu.mybatis.utils.DataSourceUtil;

import java.lang.reflect.Proxy;
import java.sql.Connection;

public class DefaultSqlSession implements SqlSession {
    private Configuration cfg;
    private Connection connection;

    public DefaultSqlSession(Configuration cfg) {
        this.cfg = cfg;
        connection = DataSourceUtil.getConnection(cfg);
    }

    /**
     * 创建代理对象
     * @param daoInterfaceClass dao的接口字节码class
     * @param <T>
     * @return
     */
    @Override
    public <T> T getMapper(Class<T> daoInterfaceClass) {
        return (T) Proxy.newProxyInstance(daoInterfaceClass.getClassLoader(),
                new Class[]{daoInterfaceClass},new MapperProxy(cfg.getMappers(),connection));
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
