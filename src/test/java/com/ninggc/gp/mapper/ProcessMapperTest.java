package com.ninggc.gp.mapper;

import com.ninggc.gp.data.Process;
import com.ninggc.gp.data.Stage;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.ProcessService;
import com.ninggc.gp.service.StageService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ProcessMapperTest implements ITest {

    SqlSessionFactory factory = null;
    ProcessService processService = null;
    StageService stageService = null;

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

        init(session);

        Process process = processService.selectOne(new Process().setId(1));
        List<Stage> stages = stageService.select(new Stage().setProcess_id(process.getId()));
        process.setStageList(stages);

        System.out.println(process.toJson());
        for (Stage s :
                process.getStageList()) {
            System.out.println(s.toJson());
        }
    }

    @Override
    public void init(SqlSession session) {
        processService = new ProcessService(session);
        stageService = new StageService(session);
    }
}