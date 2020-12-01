package com.daliu.utils;

/**
 * 和事务管理相关的工具类
 * 开启事务、提交事务、回滚事务、释放连接
 */
public class TransactionManager {
    /// 由spring注入
    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    /**
     * 开启连接
     */
    public void beginTransaction() {
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public void commit() {
        try {
            connectionUtils.getThreadConnection().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        try {
            connectionUtils.getThreadConnection().rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 释放连接
     */
    public void release() {
        try {
            connectionUtils.getThreadConnection().close(); // 还回到连接池
            connectionUtils.removeConnection(); // 由于connection被close掉了，下次再从连接池中获取关闭的connection不可用，这里直接删除掉
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
