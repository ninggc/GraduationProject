package com.ninggc.gp.mapper;

import com.ninggc.gp.data.CheckUnit;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.CheckUnitService;
import com.ninggc.gp.util.Printer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CheckUnitMapperTest implements ITest {

    CheckUnitService checkUnitService = null;
    SqlSessionFactory factory = null;

    @Override
    public void initService(SqlSession session) {
        checkUnitService= new CheckUnitService(session);
    }

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
//        pojo.setProcess_id(1);
        pojo.setStage_id(41);
        List<CheckUnit> list = new CheckUnitService(session).select(pojo);

        for (CheckUnit unit :
                list) {
            System.out.println(Printer.toJson(unit));
        }
    }

    @Test
    public void selectByAccount() throws IOException {
        try(SqlSession session = Factory.openSession()) {
            initService(session);
            List<CheckUnit> units = checkUnitService.selectByTeacherAccount("1503130101");
            System.out.println(gson.toJson(units));
        }
    }
}