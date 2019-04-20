package com.ninggc.gp.mybatis;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class Factory {

    @Bean(name = "sqlSessionFactory")
    public static SqlSessionFactory getSqlSessionFactory() throws IOException {

        String resource = "mybatis-config.xml";
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
