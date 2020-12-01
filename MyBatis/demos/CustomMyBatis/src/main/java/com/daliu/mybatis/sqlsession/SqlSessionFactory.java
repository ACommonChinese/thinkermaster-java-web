package com.daliu.mybatis.sqlsession;

public interface SqlSessionFactory {
    /// 打开一个新的SqlSession对象
    SqlSession openSession();
}
