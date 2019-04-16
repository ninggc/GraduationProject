package com.ninggc.gp.mybatis;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Factory {

    public static SqlSessionFactory getSqlSessionFactory() throws IOException {

        String resource = "com/ninggc/gp/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//
//        Configuration configuration = new Configuration(environment);
//        configuration.addMapper(UserMapper.class);
//        return new SqlSessionFactoryBuilder().build(configuration);
    }

    public static SqlSession openSession() throws IOException {
        return getSqlSessionFactory().openSession();
    }

}
