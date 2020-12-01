package com.daliu.mybatis.sqlsession.defaults;

import com.daliu.mybatis.cfg.Configuration;
import com.daliu.mybatis.sqlsession.SqlSession;
import com.daliu.mybatis.sqlsession.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration cfg;
    public DefaultSqlSessionFactory(Configuration cfg) {
        this.cfg = cfg;
    }

    /**
     * 创建一个新的操作数据库对象()
     * @return
     */
    public SqlSession openSession() {
        return new DefaultSqlSession(cfg);
    }
}
