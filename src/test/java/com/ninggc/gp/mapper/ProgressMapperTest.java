package com.ninggc.gp.mapper;

import com.ninggc.gp.data.Progress;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.ProgressService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ProgressMapperTest implements ITest {

    SqlSessionFactory factory = null;
    ProgressService progressService = null;
    
    @Before
    public void setUp() throws Exception {
        factory = Factory.getSqlSessionFactory();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void select() throws IOException {
        SqlSession session = Factory.openSession();

        initService(session);

        Progress progress = progressService.selectOne(new Progress().setAccount("1503130115").setProcess_id(1));

        System.out.println(progress.toJson());
    }

    @Override
    public void initService(SqlSession session) {
        progressService = new ProgressService(session);
    }
}