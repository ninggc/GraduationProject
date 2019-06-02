package com.ninggc.gp.mapper;

import com.google.gson.reflect.TypeToken;
import com.ninggc.gp.data.Progress;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.ProgressService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

        Progress progress = progressService.selectOne(new Progress().setAccount("1503130115"));

        System.out.println(progress.toJson());

//        Map<Integer, Byte> map = gson.fromJson(progress.getData(), new TypeToken<Map<Integer, Byte>>(){}.getType());
//
//        System.out.println(gson.toJson(map.get(1)));

        session.close();
    }

    @Test
    public void selectByTeacher() throws IOException {
        SqlSession session = Factory.openSession();

        initService(session);

        List<Map<String, Object>> mapList = progressService.selectByTeacher("1503130101");

        for (Map m :
                mapList) {
            System.out.println(gson.toJson(m));
        }

        session.close();
    }

    @Override
    public void initService(SqlSession session) {
        progressService = new ProgressService(session);
    }
}