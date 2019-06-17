package com.ninggc.gp.mapper;

import com.ninggc.gp.data.CheckUnit;
import com.ninggc.gp.data.Process;
import com.ninggc.gp.data.Stage;
import com.ninggc.gp.mybatis.Factory;
import com.ninggc.gp.service.CheckUnitService;
import com.ninggc.gp.service.ProcessService;
import com.ninggc.gp.service.StageService;
import com.ninggc.gp.util.Log;
import com.ninggc.gp.util.Printer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.ninggc.gp.mybatis.Factory.openSession;

public class ProcessMapperTest implements ITest {

    SqlSessionFactory factory = null;
    ProcessService processService = null;
    StageService stageService = null;
    CheckUnitService checkUnitService = null;

    @Before
    public void setUp() throws Exception {
        factory = Factory.getSqlSessionFactory();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insert() throws IOException {

//        try(SqlSession session = openSession()) {
//            initService(session);
//
//            Process pojo = new Process();
//            pojo.setName("msg files");
//            List<String> strings = Arrays.asList("qq", "ww", "ee");
//            pojo.setMsg(gson.toJson(strings));
//            pojo.setFiles(gson.toJson(strings));
//            int i = processService.insert(pojo);
//            session.commit();
//
//            System.out.println(gson.toJson(i));
//            System.out.println(gson.toJson(pojo));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    @Test
    public void select() throws IOException {
        try(SqlSession session = openSession()){

            initService(session);

            Process process = processService.selectOne(new Process().setId(1));
            List<Stage> stages = stageService.select(new Stage().setProcess_id(process.getId()));
            process.setStageList(stages);

            System.out.println(process.toJson());
            for (Stage s :
                    process.getStageList()) {
                System.out.println(s.toJson());
            }
        };
    }

    @Test
    public void selectAllByUser() throws IOException {
        try(SqlSession session = openSession()){


            initService(session);

            List<Process> processes = processService.selectAllByUser("1503130115");

            Log.info(gson.toJson(processes));
        };
    }

    @Test
    public void selectUnitId() throws IOException {
        try(SqlSession session = openSession()){

            initService(session);

            CheckUnit unit = new CheckUnit();
            unit.setProcess_id(1);
            List<CheckUnit> select = checkUnitService.select(unit);

            for (CheckUnit v :
                    select) {
                System.out.println(Printer.toJson(v));
            }
        };
    }

    @Override
    public void initService(SqlSession session) {
        processService = new ProcessService(session);
        stageService = new StageService(session);
        checkUnitService = new CheckUnitService(session);
    }
}