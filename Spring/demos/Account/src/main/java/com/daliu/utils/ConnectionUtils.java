package com.daliu.utils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 连接的工具类，用于从数据源中获取一个连接并且实现和线程的绑定
 */
public class ConnectionUtils {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    /// 由spring注入
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 获取当前线程上的连接
     * @return 数据库连接
     */
    public Connection getThreadConnection() {
        // 1. 先从ThreadLocal中获取
        Connection conn = threadLocal.get();
        // 2. 判断当前线程上是否有连接
        if (conn == null) {
            try {
                // 3. 从数据源中获取连接并且存入ThreadLocal中
                conn = dataSource.getConnection();
                threadLocal.set(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
        return conn;
    }

    /**
     * 把连接和线程解绑
     */
    public void removeConnection() {
        threadLocal.remove();
    }
}
