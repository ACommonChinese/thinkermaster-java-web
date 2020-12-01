package com.daliu.mybatis.utils;

import com.daliu.mybatis.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 创建数据源
 */
public class DataSourceUtil {
    /**
     * 获取一个连接
     * @param cfg
     * @return
     */
    public static Connection getConnection(Configuration cfg) {
        try {
            Class.forName(cfg.getDriver());
            Connection conn = DriverManager.getConnection(cfg.getUrl(), cfg.getUsername(), cfg.getPassword());
            if (conn == null) {
                System.out.println("connection is null!!!!!");
            }
            return conn;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
