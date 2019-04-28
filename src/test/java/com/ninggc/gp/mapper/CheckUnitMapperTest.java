package com.ninggc.gp.mapper;

import com.ninggc.gp.data.CheckUnit;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.CheckUnitService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CheckUnitMapperTest {

    SqlSessionFactory factory = null;

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
        CheckUnit pojo = new CheckUnit();

        List<CheckUnit> list = new CheckUnitService(session).select(pojo);

        for (CheckUnit unit :
                list) {
            System.out.println(unit.toJson());
        }
    }
}