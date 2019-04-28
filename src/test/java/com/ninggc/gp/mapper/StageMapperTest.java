package com.ninggc.gp.mapper;

import com.ninggc.gp.data.Stage;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.StageService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class StageMapperTest {

    SqlSessionFactory factory = null;
    StageService service = null;

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
        Stage pojo = new Stage();

        List<Stage> list = new StageService(session).select(pojo);

        for (Stage s :
                list) {
            System.out.println(s.toJson());
        }
    }
}